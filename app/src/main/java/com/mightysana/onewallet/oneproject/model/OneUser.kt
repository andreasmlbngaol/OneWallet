package com.mightysana.onewallet.oneproject.model

data class OneUser(
    val uid: String = "",
    val name: String? = null,
    val email: String = "",
    val profilePhotoUrl: String? = "",
    val username: String? = "",
    val phoneNumber: String? = "",
    val bio: String? = null,
    val birthDate: String? = null,
    val gender: String? = "",
    val address: String? = "",
    val createdAt: String = "",
    val lastLoginAt: String = "",
    val verifiedAt: String? = null
)