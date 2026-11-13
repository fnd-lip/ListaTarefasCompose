package com.felipe.listadetarefascompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipe.listadetarefascompose.model.Tarefa
import com.felipe.listadetarefascompose.repositorio.TarefasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TarefasViewModel @Inject constructor(private val tarefasRepositorio: TarefasRepositorio): ViewModel() {

    private val _todastarefas = MutableStateFlow<MutableList<Tarefa>>(mutableListOf())
    private val todastarefas: StateFlow<MutableList<Tarefa>> = _todastarefas

    private val _nome = MutableStateFlow("")
    private val nome: StateFlow<String> = _nome

    fun salvarTarefa(tarefa: String, descricao: String, prioridade: Int, checkTarefa: Boolean){
        viewModelScope.launch {
            tarefasRepositorio.salvarTarefa(tarefa,descricao,prioridade,checkTarefa)
        }
    }

    fun recuperarTarefas(): Flow<MutableList<Tarefa>>{
       viewModelScope.launch {
          tarefasRepositorio.recuperarTarefas().collect{
              _todastarefas.value = it
          }
       }
        return todastarefas
    }

    fun deletarTarefa(tarefa: String){
        viewModelScope.launch {
            tarefasRepositorio.deletarTarefa(tarefa)
        }
    }

    fun atualizarEstadoTarefa(tarefa: String, checkTarefa: Boolean){
        viewModelScope.launch {
            tarefasRepositorio.atualizarEstadoTarefa(tarefa,checkTarefa)
        }
    }

    fun perfilUsuario(): Flow<String>{
        viewModelScope.launch {
            tarefasRepositorio.perfilUsuario().collect{
                _nome.value = it
            }
        }
        return nome
    }

}