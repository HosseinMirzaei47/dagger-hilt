package com.example.daggerhilt.util

interface EntityMapper<Entity, View> {

    fun toView(entity: Entity): View

    fun toEntity(view: View): Entity

}