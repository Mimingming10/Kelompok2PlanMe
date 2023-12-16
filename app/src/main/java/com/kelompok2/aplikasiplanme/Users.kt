package com.kelompok2.aplikasiplanme

import android.os.Parcelable
import java.util.UUID
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    val id : String = UUID.randomUUID().toString(),
    val userType : String? = null,
    val userId : String? = null,
    val userName : String? = null,
    val userEmail : String? = null,
    val userPassword : String? = null,
    val userImage : String? = null,
    val userToken : String? = null,
) : Parcelable

