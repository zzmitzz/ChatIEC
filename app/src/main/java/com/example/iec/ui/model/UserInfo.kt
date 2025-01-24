package com.example.iec.ui.model

import kotlinx.serialization.Serializable


@Serializable
data class UserInfo(
    val imageProfile: String? = null,
    val userName: String? = null,
    val name: String? = null,
    val birth: String? = null,
    val currentJob: String? = null,
    val quotes: String? = null,
    val email: String? = null,
    val sessionToken: String? = null
){

}