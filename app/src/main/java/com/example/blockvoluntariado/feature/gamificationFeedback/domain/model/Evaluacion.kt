package com.example.blockvoluntariado.feature.gamificationFeedback.domain.model

enum class TipoEvaluador {
    ONG,
    VOLUNTARIO
}

data class Evaluacion(
    val id: Long,
    val evaluadorId: Long,
    val evaluadoId: Long,
    val tipoEvaluador: TipoEvaluador,
    val score: Int, // 1 a 5
    val feedback: String?,
    val fecha: String
)
