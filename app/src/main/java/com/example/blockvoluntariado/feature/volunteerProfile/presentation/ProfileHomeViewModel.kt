package com.example.blockvoluntariado.feature.volunteerProfile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.feature.volunteerProfile.application.GetVoluntarioProfileByIdUseCase
import com.example.blockvoluntariado.feature.volunteerProfile.application.GetVolunteerPreferencesByIdUseCase
import com.example.blockvoluntariado.feature.volunteerProfile.application.UpdateVolunteerPreferencesUseCase
import com.example.blockvoluntariado.feature.volunteerProfile.application.UpdateVolunteerProfileUseCase
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerPreferenceRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.Request.UpdateVolunteerProfileRequest
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.PersonalName
import com.example.blockvoluntariado.feature.volunteerProfile.domain.valueObjects.VolunteerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileHomeViewModel @Inject constructor(
    private val getVolunteerProfile: GetVoluntarioProfileByIdUseCase,
    private val getVolunteerPreferences: GetVolunteerPreferencesByIdUseCase,
    private val updateVolunteerProfile: UpdateVolunteerProfileUseCase,
    private val updateVolunteerPreferences: UpdateVolunteerPreferencesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileHomeUiState())
    val state = _state.asStateFlow()

    fun loadVolunteer(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val profileResult = getVolunteerProfile(id)
            val preferencesResult = getVolunteerPreferences(id)

            profileResult.fold(
                onSuccess = { volunteer ->
                    preferencesResult.fold(
                        onSuccess = { preferences ->
                            _state.update {
                                it.copy(
                                    volunteer = volunteer,
                                    preferences = preferences ?: volunteer?.preferences,
                                    isLoading = false,
                                    errorMessage = if (volunteer == null) "No se encontró el perfil del voluntario." else null
                                )
                            }
                        },
                        onFailure = { error ->
                            _state.update {
                                it.copy(
                                    volunteer = volunteer,
                                    preferences = volunteer?.preferences,
                                    isLoading = false,
                                    errorMessage = error.message ?: "No se pudieron cargar las preferencias."
                                )
                            }
                        }
                    )
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "No se pudo cargar el perfil.")
                    }
                }
            )
        }
    }

    fun saveProfile(id: Int, firstName: String, lastName: String) {
        if (firstName.isBlank() || lastName.isBlank()) {
            _state.update { it.copy(errorMessage = "Completa el nombre y los apellidos.", successMessage = null) }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }
            updateVolunteerProfile(id, UpdateVolunteerProfileRequest(PersonalName(firstName.trim(), lastName.trim())))
                .fold(
                    onSuccess = { updated ->
                        _state.update {
                            it.copy(
                                volunteer = updated ?: it.volunteer?.copy(name = PersonalName(firstName.trim(), lastName.trim())),
                                isSaving = false,
                                successMessage = "Perfil actualizado correctamente."
                            )
                        }
                    },
                    onFailure = { error ->
                        _state.update { it.copy(isSaving = false, errorMessage = error.message ?: "No se pudo actualizar el perfil.") }
                    }
                )
        }
    }

    fun savePreferences(id: Int, causesText: String, availability: String, modality: String) {
        if (availability.isBlank() || modality.isBlank()) {
            _state.update { it.copy(errorMessage = "Completa la disponibilidad y la modalidad.", successMessage = null) }
            return
        }

        val preferences = VolunteerPreferences(
            causes = causesText.split(",").map { it.trim() }.filter { it.isNotBlank() },
            availability = availability.trim(),
            preferredModality = modality.trim()
        )

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }
            updateVolunteerPreferences(id, UpdateVolunteerPreferenceRequest(preferences)).fold(
                onSuccess = { updated ->
                    _state.update {
                        it.copy(
                            preferences = updated ?: preferences,
                            volunteer = it.volunteer?.copy(preferences = updated ?: preferences),
                            isSaving = false,
                            successMessage = "Preferencias actualizadas correctamente."
                        )
                    }
                },
                onFailure = { error ->
                    _state.update { it.copy(isSaving = false, errorMessage = error.message ?: "No se pudieron actualizar las preferencias.") }
                }
            )
        }
    }

    fun clearMessages() {
        _state.update { it.copy(errorMessage = null, successMessage = null) }
    }
}
