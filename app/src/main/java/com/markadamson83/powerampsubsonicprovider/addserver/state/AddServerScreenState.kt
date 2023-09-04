package com.markadamson83.powerampsubsonicprovider.addserver.state

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AddServerScreenState(): Parcelable {
    var serverName by mutableStateOf("")
    var isBadServerName by mutableStateOf(false)
    var baseURL by mutableStateOf("")
    var isBadURL by mutableStateOf(false)
    var username by mutableStateOf("")
    var isBadUsername by mutableStateOf(false)
    var password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    var isBadPassword by mutableStateOf(false)
    var lastInfoMessage by mutableStateOf("")
    var isSaving by mutableStateOf(false)

    private var lastSubmittedServerName by mutableStateOf<String?>(null)
    private var lastSubmittedBaseURL by mutableStateOf<String?>(null)
    private var lastSubmittedUsername by mutableStateOf<String?>(null)
    private var lastSubmittedPassword by mutableStateOf<String?>(null)

    val showBadServerName : Boolean
        get() = isBadServerName && lastSubmittedServerName == serverName
    val showBadURL : Boolean
        get() = isBadURL && lastSubmittedBaseURL == baseURL
    val showBadUsername : Boolean
        get() = isBadUsername && lastSubmittedUsername == username
    val showBadPassword : Boolean
        get() = isBadPassword && lastSubmittedPassword == password

    constructor(parcel: Parcel) : this() {
        serverName = parcel.readString() ?: ""
        isBadServerName = parcel.readByte() != 0.toByte()
        baseURL = parcel.readString() ?: ""
        isBadURL = parcel.readByte() != 0.toByte()
        username = parcel.readString() ?: ""
        isBadUsername = parcel.readByte() != 0.toByte()
        password = parcel.readString() ?: ""
        isPasswordVisible = parcel.readByte() != 0.toByte()
        isBadPassword = parcel.readByte() != 0.toByte()
        lastInfoMessage = parcel.readString() ?: ""
        isSaving = parcel.readByte() != 0.toByte()

        lastSubmittedServerName = parcel.readString()
        lastSubmittedBaseURL = parcel.readString()
        lastSubmittedUsername = parcel.readString()
        lastSubmittedPassword = parcel.readString()
    }

    fun resetUIState() {
        lastSubmittedServerName = serverName
        lastSubmittedBaseURL = baseURL
        lastSubmittedUsername = username
        lastSubmittedPassword = password
        lastInfoMessage = ""
    }

    fun resetLastSubmittedServerName() {
        lastSubmittedServerName = null
    }

    fun resetLastSubmittedBaseURL() {
        lastSubmittedBaseURL = null
    }

    fun resetLastSubmittedUsername() {
        lastSubmittedUsername = null
    }

    fun resetLastSubmittedPassword() {
        lastSubmittedPassword = null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(serverName)
        parcel.writeByte(if (isBadServerName) 1 else 0)
        parcel.writeString(baseURL)
        parcel.writeByte(if (isBadURL) 1 else 0)
        parcel.writeString(username)
        parcel.writeByte(if (isBadUsername) 1 else 0)
        parcel.writeString(password)
        parcel.writeByte(if (isPasswordVisible) 1 else 0)
        parcel.writeByte(if (isBadPassword) 1 else 0)
        parcel.writeString(lastInfoMessage)
        parcel.writeByte(if (isSaving) 1 else 0)

        parcel.writeString(lastSubmittedServerName)
        parcel.writeString(lastSubmittedBaseURL)
        parcel.writeString(lastSubmittedUsername)
        parcel.writeString(lastSubmittedPassword)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddServerScreenState> {
        override fun createFromParcel(parcel: Parcel): AddServerScreenState {
            return AddServerScreenState(parcel)
        }

        override fun newArray(size: Int): Array<AddServerScreenState?> {
            return arrayOfNulls(size)
        }
    }
}