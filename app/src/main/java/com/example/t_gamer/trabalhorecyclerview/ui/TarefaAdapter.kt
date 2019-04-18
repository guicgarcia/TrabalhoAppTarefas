package com.example.t_gamer.trabalhorecyclerview.ui

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.t_gamer.trabalhorecyclerview.R
import com.example.t_gamer.trabalhorecyclerview.entidades.Tarefa
import kotlinx.android.synthetic.main.item_tarefa.view.*

class TarefaAdapter(
    private var listaTarefa: MutableList<Tarefa>,
    private var listener: TarefaAdapterListener,
    private var tarefaEditing: Tarefa? = null

) :
    RecyclerView.Adapter<TarefaAdapter.ViewHolder>() {

    fun addTarefa(person: Tarefa): Int {
        listaTarefa.add(0, person)
        notifyItemInserted(0)
        return 0
    }

    fun updateTarefa(person: Tarefa): Int {
        val position = listaTarefa.indexOf(person)
        notifyItemChanged(position)
        return position
    }

    override fun getItemViewType(position: Int): Int {
        //Pega posição do card selecionado
        val tarefa = listaTarefa[position]

        if(tarefa != tarefaEditing) {
            return R.layout.item_tarefa_compact
        }else{
            return R.layout.item_tarefa
        }
    }

    private fun tarefaSelecionada() {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)
        )

    override fun getItemCount() = listaTarefa.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarefa = listaTarefa[position]
        holder.fillUI(tarefa)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillUI(tarefa: Tarefa) {

            //Verificar tarefas com status = 1
            //listener.verificarStatus()

            if (tarefa != tarefaEditing) {

                itemView.txtTitulo.setText(tarefa.titulo)
                itemView.txtTitulo.isEnabled = false

                itemView.setOnClickListener {
                    with(this@TarefaAdapter) {
                        listener.tarefaClicked(tarefa)

                        //Clicou no card
                        tarefaEditing = tarefa
                        val position = listaTarefa.indexOf(tarefa)
                        notifyItemChanged(position)
                    }
                }
            }
            else {
                var cardCompact = 0
                var tarefaFeita = 0

                itemView.txtTitulo.setText(tarefa.titulo)
                itemView.txtDescricao.setText(tarefa.descricao)

                itemView.btCompartilhar.visibility = View.INVISIBLE

                //deixarInvisivel()

                itemView.btRemove.setOnClickListener {
                    with(this@TarefaAdapter) {
                        val position = listaTarefa.indexOf(tarefa)
                        listaTarefa.removeAt(position)
                        notifyItemRemoved(position)
                        listener.tarefaRemoved(tarefa)
                    }
                }

                itemView.setOnClickListener {
                    with(this@TarefaAdapter) {
                        cardCompact = 1
                        listener.tarefaClicked(tarefa)

                        //deixarVisivel()

                        //Clicou no card
                        tarefaEditing = tarefa
                        val position = listaTarefa.indexOf(tarefa)
                        notifyItemChanged(position)

                    }
                }

                itemView.btSalvar.setOnClickListener {
                    with(this@TarefaAdapter) {

                        //deixarInvisivel()

                        tarefa.titulo = itemView.txtTitulo.text.toString()
                        tarefa.descricao = itemView.txtDescricao.text.toString()

                        if (tarefa.id != 0) {
                        //if (tarefa.titulo.toString() != "" && tarefa.descricao.toString() != "") {
                            listener.tarefaUpdated(tarefa)
                        } else {
                            listener.tarefainserted(tarefa)
                        }

                    }
                }

                itemView.setOnLongClickListener {
                    itemView.btCompartilhar.visibility = View.VISIBLE

                    if (cardCompact == 0 && tarefaFeita == 0) {
                        tarefaFeita = 1
                        itemView.card.setBackgroundColor((Color.GREEN))
                        itemView.btCompartilhar.visibility = View.VISIBLE

                        tarefa.status = 1
                        listener.tarafaFeita(tarefa)
                    } else if (cardCompact == 0 && tarefaFeita == 1) {
                        tarefaFeita = 0
                        itemView.card.setBackgroundColor((Color.WHITE))
                        itemView.btCompartilhar.visibility = View.INVISIBLE
                    }
                    true
                }

                itemView.btCompartilhar.setOnClickListener {
                    with(this@TarefaAdapter) {
                        var titulo = itemView.txtTitulo.text.toString()
                        listener.tarefaCompartilhar(titulo)
                    }
                }

            }

        }

        /*
        private fun deixarInvisivel() {
            itemView.txtTitulo.isEnabled = false
            itemView.txtDescricao.visibility = View.INVISIBLE
            itemView.btSalvar.visibility = View.INVISIBLE
            itemView.btRemove.visibility = View.INVISIBLE
            itemView.btCompartilhar.visibility = View.INVISIBLE
        }

        private fun deixarVisivel() {
            itemView.txtTitulo.isEnabled = true
            itemView.txtDescricao.visibility = View.VISIBLE
            itemView.btSalvar.visibility = View.VISIBLE
            itemView.btRemove.visibility = View.VISIBLE
        }
        */

    }

}
