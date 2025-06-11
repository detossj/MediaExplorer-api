package com.deto.mediaexplorer.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "elements",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,  // La tabla a la que se hace referencia
            parentColumns = ["id"],    // La columna de la tabla `Category` que es la clave primaria
            childColumns = ["categoryId"], // La columna en la tabla `Element` que hace referencia a `Category`
            onDelete = ForeignKey.CASCADE  // Si se elimina una `Category`, se eliminan sus `Element` asociados
        )
    ],
    indices = [Index("categoryId")]  // Crea un índice en `categoryId` para optimizar consultas
)
data class Element(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val classification: Int,
    val imagen: Int?,
    @SerializedName("category_id") val categoryId: Int
)