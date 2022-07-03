package com.example.salonapp.domain.repositories

import com.example.salonapp.domain.models.Text


interface TextRepository {

    suspend fun getAllText(): List<Text>

    suspend fun getTextByKey(key:String): Text

    suspend fun createText(text:Text): List<Text>

    suspend fun updateText(text:Text): List<Text>

    suspend fun deleteText(key: String): List<Text>

}