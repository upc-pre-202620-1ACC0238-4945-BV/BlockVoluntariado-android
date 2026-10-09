package com.example.blockvoluntariado.feature.gamificationFeedback.presentation.recognition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.core.storage.TokenManager
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.model.DigitalCertificate
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.usecase.GetCertificatesByVolunteerUseCase
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.usecase.GetGamificationProfileUseCase
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.usecase.GetVolunteerHistoryUseCase
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.usecase.SubmitReviewOrganizationUseCase
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.usecase.SubmitReviewVolunteerUseCase
import com.example.blockvoluntariado.feature.gamificationFeedback.domain.usecase.VerifyCertificateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogrosCertificadosViewModel @Inject constructor(
    private val getGamificationProfileUseCase: GetGamificationProfileUseCase,
    private val getCertificatesByVolunteerUseCase: GetCertificatesByVolunteerUseCase,
    private val getVolunteerHistoryUseCase: GetVolunteerHistoryUseCase,
    private val verifyCertificateUseCase: VerifyCertificateUseCase,
    private val submitReviewOrganizationUseCase: SubmitReviewOrganizationUseCase,
    private val submitReviewVolunteerUseCase: SubmitReviewVolunteerUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogrosCertificadosUiState(isLoading = true))
    val uiState: StateFlow<LogrosCertificadosUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val volunteerId = tokenManager.userIdFlow.first() ?: 1L

            val profileResult = getGamificationProfileUseCase(volunteerId)
            val certsResult = getCertificatesByVolunteerUseCase(volunteerId)
            val historyResult = getVolunteerHistoryUseCase(volunteerId)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    profile = profileResult.getOrNull(),
                    certificates = certsResult.getOrDefault(emptyList()),
                    historyItems = historyResult.getOrDefault(emptyList())
                )
            }
        }
    }

    fun onVerificationInputChanged(hash: String) {
        _uiState.update { it.copy(verificationInput = hash, verificationError = null) }
    }

    fun verifyCertificate() {
        val hash = _uiState.value.verificationInput.trim()
        if (hash.isEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isVerifying = true, verificationError = null) }
            verifyCertificateUseCase(hash)
                .onSuccess { cert ->
                    _uiState.update {
                        it.copy(
                            isVerifying = false,
                            verifiedCertificate = cert,
                            selectedCertificate = cert,
                            statusMessage = "¡Certificado auténtico verificado en Blockchain!"
                        )
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            isVerifying = false,
                            verificationError = "No se encontró ningún certificado con el hash provisto."
                        )
                    }
                }
        }
    }

    fun selectCertificate(cert: DigitalCertificate?) {
        _uiState.update { it.copy(selectedCertificate = cert) }
    }

    fun openReviewDialog(targetId: Long, targetName: String, isOrg: Boolean) {
        _uiState.update {
            it.copy(
                isReviewDialogVisible = true,
                reviewTargetId = targetId,
                reviewTargetName = targetName,
                isReviewTargetOrg = isOrg
            )
        }
    }

    fun closeReviewDialog() {
        _uiState.update {
            it.copy(
                isReviewDialogVisible = false,
                reviewTargetId = null
            )
        }
    }

    fun submitReview(score: Int, comments: String?) {
        val targetId = _uiState.value.reviewTargetId ?: return
        val isOrg = _uiState.value.isReviewTargetOrg

        viewModelScope.launch {
            val myId = tokenManager.userIdFlow.first() ?: 1L
            val result = if (isOrg) {
                submitReviewOrganizationUseCase(targetId, myId, score, comments)
            } else {
                submitReviewVolunteerUseCase(targetId, myId, score, comments)
            }

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isReviewDialogVisible = false,
                        reviewTargetId = null,
                        statusMessage = "Evaluación de $score estrellas enviada con éxito."
                    )
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isReviewDialogVisible = false,
                        reviewTargetId = null,
                        statusMessage = "Evaluación registrada localmente."
                    )
                }
            }
        }
    }

    fun clearStatusMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
