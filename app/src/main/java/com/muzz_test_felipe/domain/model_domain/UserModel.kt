package com.muzz_test_felipe.domain.model_domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String,
    val name: String,
    val photoUrl: String
) : DomainModel, Parcelable