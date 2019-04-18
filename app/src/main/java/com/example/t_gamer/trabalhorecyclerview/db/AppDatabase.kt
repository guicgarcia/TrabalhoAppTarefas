package com.example.t_gamer.trabalhorecyclerview.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.t_gamer.trabalhorecyclerview.db.dao.TarefaDao
import com.example.t_gamer.trabalhorecyclerview.entidades.Tarefa

@Database(entities = arrayOf(Tarefa::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao
}