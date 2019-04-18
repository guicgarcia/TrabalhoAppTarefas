package com.example.t_gamer.trabalhorecyclerview.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefa")
data class Tarefa(
    @ColumnInfo(name = "titulo")
    var titulo: String,
    @ColumnInfo(name = "descricao")
    var descricao: String,
    @ColumnInfo(name = "status")
    var status: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val fullName get() = "${titulo} ${descricao}"
    //val fullNameWithTitle get() = "${titulo} ${descricao}"

    override fun toString()= fullName
}