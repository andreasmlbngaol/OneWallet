package com.mightysana.onewallet.oneproject.model

data class OneUser(
    val uid: String = "",
    val name: String = uid,
    val email: String = "",
    val profilePhotoUrl: String? = null,
    val phoneNumber: String? = null,
    val bio: String? = null,
    val birthDate: Long? = null,
    val gender: String? = null,
    val address: String? = null,
    val createdAt: Long = 0L,
    val lastLoginAt: Long = 0L,
    val verified: Boolean = false,
)