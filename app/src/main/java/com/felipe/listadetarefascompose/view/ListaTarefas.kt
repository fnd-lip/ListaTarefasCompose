package com.felipe.listadetarefascompose.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felipe.listadetarefascompose.ui.theme.BLACK
import com.felipe.listadetarefascompose.ui.theme.Purple700
import com.felipe.listadetarefascompose.ui.theme.WHITE
import com.google.firebase.auth.FirebaseAuth
import com.felipe.listadetarefascompose.R
import com.felipe.listadetarefascompose.itemlista.TarefaItem
import com.felipe.listadetarefascompose.viewmodel.TarefasViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListaTarefas(
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){

    val context = LocalContext.current
    val nomeUsuario = viewModel.perfilUsuario().collectAsState(initial = "").value

    Scaffold(
        topBar = {
            TopAppBar(
               backgroundColor = Purple700,
                title = {
                    Text(
                        text = "Lista de Tarefas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WHITE
                    )
                },
                actions = {

                    Text(text = nomeUsuario, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = WHITE)

                    TextButton(
                        onClick = {

                            val alertDialog = AlertDialog.Builder(context)
                            alertDialog.setTitle("Deslogar")
                            alertDialog.setMessage("Deseja sair do aplicativo?")
                            alertDialog.setPositiveButton("Sim"){_,_ ->
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate("login")
                            }
                            alertDialog.setNegativeButton("Não"){_,_ ->

                            }
                           .show()
                        }
                    ) {
                        Text(text = "Sair", fontSize = 16.sp, color = WHITE)
                    }
                }
            )
        },
        backgroundColor = BLACK,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                   navController.navigate("salvarTarefa")
                },
                backgroundColor = Purple700
            ) {
              Image(
                  imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                  contentDescription = "Ícone de salvar tarefa!"
              )
            }
        }
    ) {

       val listaTarefas = viewModel.recuperarTarefas().collectAsState(mutableListOf()).value
        
        LazyColumn{
            itemsIndexed(listaTarefas){position, _, ->
                TarefaItem(position = position, listaTarefas = listaTarefas, context = context , navController = navController,viewModel)
            }
        }
    }
}