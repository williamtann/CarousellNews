package com.example.domain.mapper

import com.example.data.model.entity.NewsEntity
import com.example.data.model.response.NewsApiModel
import com.example.domain.model.NewsUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsMapperTest {

    @Test
    fun `when map news api model to entity should return expected entity`() {
        // Given
        val id = "id"
        val title = "title"
        val description = "description"
        val bannerUrl = "bannerUrl"
        val timeCreated = 321L
        val rank = 123

        val apiModel = NewsApiModel(
            id = id,
            title = title,
            description = description,
            bannerUrl = bannerUrl,
            timeCreated = timeCreated,
            rank = rank
        )
        val entity = NewsEntity(
            id = id,
            title = title,
            description = description,
            bannerUrl = bannerUrl,
            timeCreated = timeCreated,
            rank = rank
        )

        // When
        val result = apiModel.toEntity()

        // Then
        assertEquals(entity, result)
    }

    @Test
    fun `when map news entity to ui model should return expected ui model`() {
        // Given
        val id = "id"
        val title = "title"
        val description = "description"
        val bannerUrl = "bannerUrl"
        val timeCreated = 321L
        val rank = 123

        val entity = NewsEntity(
            id = id,
            title = title,
            description = description,
            bannerUrl = bannerUrl,
            timeCreated = timeCreated,
            rank = rank
        )
        val uiModel = NewsUiModel(
            id = id,
            title = title,
            description = description,
            bannerUrl = bannerUrl,
            timeCreated = timeCreated,
            rank = rank
        )

        // When
        val result = entity.toUiModel()

        // Then
        assertEquals(uiModel, result)
    }
}
