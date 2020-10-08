package com.example.daggerhilt.retrofit

import com.example.daggerhilt.model.BlogView
import com.example.daggerhilt.util.EntityMapper
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<BlogNetworkEntity, BlogView> {

    override fun toView(entity: BlogNetworkEntity): BlogView {
        return BlogView(
            id = entity.id,
            title = entity.title,
            body = entity.body,
            image = entity.image,
            category = entity.category
        )
    }

    override fun toEntity(view: BlogView): BlogNetworkEntity {
        return BlogNetworkEntity(
            id = view.id,
            title = view.title,
            body = view.body,
            image = view.image,
            category = view.category
        )
    }

    fun toViewList(entityList: List<BlogNetworkEntity>): List<BlogView> {
        return entityList.map { entity -> toView(entity) }
    }

}