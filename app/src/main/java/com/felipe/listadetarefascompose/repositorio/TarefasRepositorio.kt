package com.felipe.listadetarefascompose.repositorio

import com.felipe.listadetarefascompose.datasource.DataSource
import com.felipe.listadetarefascompose.model.Tarefa
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class TarefasRepositorio @Inject constructor(private val dataSource: DataSource){


    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int, checkTarefa: Boolean){
        dataSource.salvarTarefa(tarefa,descricao,prioridade,checkTarefa)
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>>{
        return dataSource.recuperarTarefas()
    }

    fun deletarTarefa(tarefa: String){
        dataSource.deletarTarefa(tarefa)
    }

    fun atualizarEstadoTarefa(tarefa: String, checkTarefa: Boolean){
        dataSource.atualizarEstadoTarefa(tarefa,checkTarefa)
    }

    fun perfilUsuario(): Flow<String>{
        return dataSource.perfilUsuario()
    }

}