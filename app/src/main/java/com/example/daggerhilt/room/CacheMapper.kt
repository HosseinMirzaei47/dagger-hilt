package com.example.daggerhilt.room

import com.example.daggerhilt.model.BlogView
import com.example.daggerhilt.util.EntityMapper
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<BlogCacheEntity, BlogView> {

    override fun toView(entity: BlogCacheEntity): BlogView {
        return BlogView(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            image = entity.image,
            category = entity.category
        )
    }

    override fun toEntity(view: BlogView): BlogCacheEntity {
        return BlogCacheEntity(
            id = view.id,
            title = view.title,
            body = view.body,
            image = view.image,
            category = view.category
        )
    }

    fun toViewList(entityList: List<BlogCacheEntity>): List<BlogView> {
        return entityList.map { entity -> toView(entity) }
    }

}