package com.example.t_gamer.trabalhorecyclerview.ui

import com.example.t_gamer.trabalhorecyclerview.entidades.Tarefa

interface TarefaAdapterListener {
    fun tarefainserted(tarefa: Tarefa)
    fun tarefaRemoved(tarefa: Tarefa)
    fun tarefaClicked(tarefa: Tarefa)
    fun tarefaUpdated(tarefa: Tarefa)
    fun tarefaCompartilhar(titulo: String)
    fun tarafaFeita(tarefa: Tarefa)
    //fun verificarStatus(): List<Tarefa>
}