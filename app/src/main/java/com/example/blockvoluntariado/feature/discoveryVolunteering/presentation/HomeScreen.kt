package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), OnNavigateToDetail: (Int) -> Unit) {

   val state = viewModel.state.collectAsStateWithLifecycle().value

    Box(modifier = Modifier.fillMaxSize()) {

        when {

            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.errorMessage != null -> {
                Text(text = state.errorMessage)
            }

            else -> {
                ConvocatoriaList(state.convocatorias,
                    onConvocatoriaClick = { id->
                        OnNavigateToDetail(id)

                    })
            }


        }
    }
}