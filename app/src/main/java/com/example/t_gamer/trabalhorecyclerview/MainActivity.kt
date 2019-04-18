package com.example.t_gamer.trabalhorecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.t_gamer.trabalhorecyclerview.db.AppDatabase
import com.example.t_gamer.trabalhorecyclerview.db.dao.TarefaDao
import com.example.t_gamer.trabalhorecyclerview.entidades.Tarefa
import com.example.t_gamer.trabalhorecyclerview.ui.TarefaAdapter
import com.example.t_gamer.trabalhorecyclerview.ui.TarefaAdapterListener
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), TarefaAdapterListener {
    lateinit var tarefaDao: TarefaDao
    lateinit var adapter: TarefaAdapter
    //var tarefaEditing: Tarefa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "tarefa.db"
            )
                .allowMainThreadQueries()
                .build()
        tarefaDao = db.tarefaDao()

        btSave.setOnClickListener { adicionarTarefa() }

        loadData()
    }

    private fun removeTarefa(tarefa: Tarefa) {
        tarefaDao.remove(tarefa)
        loadData()
    }

    private fun adicionarTarefa() {
        val tarefa = Tarefa("", "", 0)
        adapter.addTarefa(tarefa)
    }

    private fun loadData() {
        val tarefa = tarefaDao.getAll()
        adapter = TarefaAdapter(tarefa.toMutableList(), this)
        listTarefa.adapter = adapter

        listTarefa.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
    }

    override fun tarefainserted(tarefa: Tarefa) {
        tarefa.id = tarefaDao.insert(tarefa).toInt()

        val position = adapter.updateTarefa(tarefa)
        listTarefa.scrollToPosition(position)

        Toast.makeText(this, getString(R.string.tarefa_salva), Toast.LENGTH_SHORT).show()
    }

    override fun tarefaRemoved(tarefa: Tarefa) {
        tarefaDao.remove(tarefa)
    }

    override fun tarefaClicked(tarefa: Tarefa) {
        //editTarefa(tarefa)
    }

    override fun tarefaUpdated(tarefa: Tarefa) {
        tarefaDao.update(tarefa)

        val position = adapter.updateTarefa(tarefa)
        listTarefa.scrollToPosition(position)

        Toast.makeText(this, getString(R.string.tarefa_editada), Toast.LENGTH_SHORT).show()
    }

    override fun tarefaCompartilhar(titulo: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)

        with(shareIntent) {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Compartilhar")
            putExtra(Intent.EXTRA_TEXT, getString(R.string.tarefa_concluida) + "$titulo")
        }

        Toast.makeText(this, getString(R.string.compartilhando), Toast.LENGTH_SHORT).show()
        startActivity(shareIntent)
    }

    override fun tarafaFeita(tarefa: Tarefa) {
        tarefaDao.update(tarefa)
        Toast.makeText(this, getString(R.string.tarefa_realizada), Toast.LENGTH_SHORT).show()
    }

    /*
    override fun verificarStatus(): List<Tarefa> {
        var tarefa = tarefaDao.getStatus()
        return tarefa
    }
    */

}