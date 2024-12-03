package com.example.iec.feature.message

data class Message(
    val header: HEADER = HEADER.MESSAGE,
    val message: String,
    val isFromUser: Boolean,
    val timestamp: Long
)

enum class HEADER(val type: String){
    MESSAGE("message"), FILE("file"), IMAGE("image"), VIDEO("video"), AUDIO("audio")
}
