package org.d3if0145.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parfum")
data class Parfum(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaParfum: String,
    val brandParfum: String,
    val genderParfum: String

)