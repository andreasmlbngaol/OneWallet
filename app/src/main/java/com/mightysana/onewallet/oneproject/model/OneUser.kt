package com.mightysana.onewallet.oneproject.model

data class OneUser(
    val uid: String = "",
    val name: String = uid,
    val email: String = "",
    val profilePhotoUrl: String? = null,
    val phoneNumber: String? = null,
    val bio: String? = null,
    val birthDate: String? = null,
    val gender: String? = null,
    val address: String? = null,
    val createdAt: String = "",
    val lastLoginAt: String = "",
    val verified: Boolean = false,
)