package presentation.Models

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class OutputModel(
    val message: String,
)