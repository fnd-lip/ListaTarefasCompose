package com.felipe.listadetarefascompose.listener

interface ListenerAuth {
    fun onSucess(mensagem: String, tela: String)
    fun onFailure(erro: String)
}