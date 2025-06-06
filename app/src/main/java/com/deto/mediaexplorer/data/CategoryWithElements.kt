package com.deto.mediaexplorer.data

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithElements(
    @Embedded val category: Category, // Esta es la categoría
    @Relation(
        parentColumn = "id",           // La columna en `Category` que se usa para hacer la relación
        entityColumn = "categoryId"    // La columna en `Element` que actúa como clave foránea
    )
    val elements: List<Element> // Todos los elementos asociados a esta categoría
)