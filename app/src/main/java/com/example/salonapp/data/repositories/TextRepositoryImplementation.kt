package com.example.salonapp.data.repositories

import com.example.salonapp.data.dtos.TextDTO
import com.example.salonapp.data.remote.TextAPI
import com.example.salonapp.domain.models.Text
import com.example.salonapp.domain.repositories.TextRepository
import javax.inject.Inject

class TextRepositoryImplementation @Inject constructor(
    private val api: TextAPI,
): TextRepository
{

    override suspend fun getAllText(): List<Text> {
        return api.getAllText().map { toModel(it) }
    }

    override suspend fun getTextByKey(key: String): Text {
        return toModel(api.getTextByKey(key))
    }

    override suspend fun createText(text: Text): List<Text> {
        return api.createText(toDTO(text)).map { toModel(it) }
    }

    override suspend fun updateText(text: Text): List<Text> {
        return api.updateText(toDTO(text)).map { toModel(it) }
    }

    override suspend fun deleteText(key: String): List<Text> {
        return api.deleteText(key).map { toModel(it) }
    }


    private  fun toDTO(text: Text): TextDTO{
        return TextDTO(
            id = text.id,
            key = text.key,
            danishValue = text.danishValue,
            englishValue = text.englishValue
        )
    }

    private  fun toModel(textDTO: TextDTO): Text{
        return Text(
            id = textDTO.id,
            key = textDTO.key,
            danishValue = textDTO.danishValue,
            englishValue = textDTO.englishValue
        )
    }




}