package com.example.iec.ui.model

import kotlinx.serialization.Serializable


@Serializable
data class UserInfo(
    val imageProfile: String = "Unknown",
    val userName: String = "Unknown",
    val name: String = "Unknown",
    val birth: String = "Unknown",
    val currentJob: String = "Unknown",
    val quotes: String = "Unknown",
    val workingPlace: String = "Unknown",
    val aboutMe: String = "Unknown",
    val gender: String = "Male",
    val marriage: String = "Single",
    val city: String = "Ha Noi"
){

}