package com.felipe.listadetarefascompose.itemlista

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felipe.listadetarefascompose.R
import com.felipe.listadetarefascompose.model.Tarefa
import com.felipe.listadetarefascompose.ui.theme.*
import com.felipe.listadetarefascompose.viewmodel.TarefasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TarefaItem(
    position: Int,
    listaTarefas: MutableList<Tarefa>,
    context: Context,
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){

    val tituloTarefa = listaTarefas[position].tarefa
    val descricaoTarefa = listaTarefas[position].descricao
    val prioridade = listaTarefas[position].prioridade
    val tarefaConcluida = listaTarefas[position].checkTarefa

    val scope = rememberCoroutineScope()

    var isChecked by remember {
        mutableStateOf(tarefaConcluida)
    }

    fun dialogDeletar(){

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Deletar Tarefa")
            .setMessage("Deseja excluir a tarefa?")
            .setPositiveButton("Sim"){ _, _ ->

                viewModel.deletarTarefa(tituloTarefa.toString())

                scope.launch(Dispatchers.Main){
                    listaTarefas.removeAt(position)
                    navController.navigate("listaTarefas")
                    Toast.makeText(context,"Sucesso ao deletar tarefa!",Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Não"){ _, _ ->

            }
        .show()
    }

    val nivelDePrioridade: String = when(prioridade){
        0 -> {
            "Sem prioridade"
        }
        1 -> {
            "Prioridade Baixa"
        }
        2 -> {
            "Prioridade Média"
        }
        else -> {
            "Prioridade Alta"
        }
    }

    val color = when(prioridade){
        0 -> {
            Color.Black
        }
        1 -> {
            RADIO_BUTTON_GREEN_SELECTED
        }
        2 -> {
            RADIO_BUTTON_YELLOW_SELECTED
        }
        else -> {
            RADIO_BUTTON_RED_SELECTED
        }
    }

    Card(
        backgroundColor = WHITE,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
       ConstraintLayout(
           modifier = Modifier.padding(20.dp)
       ) {

           val (txtTitulo,txtDescricao,cardPrioridade,txtPrioridade,btDeletar,checkTarefa) = createRefs()

           Text(
               text = tituloTarefa.toString(),
               modifier = Modifier.constrainAs(txtTitulo){
                   top.linkTo(parent.top, margin = 10.dp)
                   start.linkTo(parent.start, margin = 10.dp)
               }
           )

           Text(
               text = descricaoTarefa.toString(),
               modifier = Modifier.constrainAs(txtDescricao){
                   top.linkTo(txtTitulo.bottom, margin = 10.dp)
                   start.linkTo(parent.start, margin = 10.dp)
               }
           )

           Text(
               text = nivelDePrioridade,
               modifier = Modifier.constrainAs(txtPrioridade){
                   top.linkTo(txtDescricao.bottom, margin = 10.dp)
                   start.linkTo(parent.start, margin = 10.dp)
                   bottom.linkTo(parent.bottom, margin = 10.dp)
               }
           )

           Card(
               backgroundColor = color,
               modifier = Modifier
                   .size(30.dp)
                   .constrainAs(cardPrioridade) {
                       top.linkTo(txtDescricao.bottom, margin = 10.dp)
                       start.linkTo(txtPrioridade.end, margin = 10.dp)
                       bottom.linkTo(parent.bottom, margin = 10.dp)
                   },
               shape = ShapeCardPrioridade.large
           ) {
               
           }

           IconButton(
               onClick = {
                  dialogDeletar()
               },
               modifier = Modifier.constrainAs(btDeletar){
                   top.linkTo(txtDescricao.bottom, margin = 10.dp)
                   start.linkTo(cardPrioridade.end, margin = 30.dp)
                   end.linkTo(parent.end, margin = 10.dp)
                   bottom.linkTo(parent.bottom, margin = 10.dp)
               }
           ) {
               Image(imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete) , contentDescription = null)
           }

           Checkbox(
               checked = isChecked!! ,
               onCheckedChange = {
                   isChecked = it

                   scope.launch(Dispatchers.IO){
                       if (isChecked!!){
                          viewModel.atualizarEstadoTarefa(tituloTarefa!!,true)
                       }else{
                          viewModel.atualizarEstadoTarefa(tituloTarefa!!,false)
                       }
                   }

               },
               modifier = Modifier.constrainAs(checkTarefa){
                   start.linkTo(btDeletar.end, margin = 10.dp)
                   top.linkTo(txtDescricao.bottom, margin = 10.dp)
                   end.linkTo(parent.end, margin = 10.dp)
                   bottom.linkTo(parent.bottom, margin = 10.dp)
               },
               colors = CheckboxDefaults.colors(
                   checkedColor = RADIO_BUTTON_GREEN_SELECTED,
                   uncheckedColor = Color.Black
               )
           )
       }
    }
}
