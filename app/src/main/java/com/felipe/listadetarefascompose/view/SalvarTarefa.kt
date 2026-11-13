package com.felipe.listadetarefascompose.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.felipe.listadetarefascompose.ui.theme.Purple700
import com.felipe.listadetarefascompose.ui.theme.RADIO_BUTTON_GREEN_DISABLED
import com.felipe.listadetarefascompose.ui.theme.RADIO_BUTTON_GREEN_SELECTED
import com.felipe.listadetarefascompose.ui.theme.RADIO_BUTTON_RED_DISABLED
import com.felipe.listadetarefascompose.ui.theme.RADIO_BUTTON_RED_SELECTED
import com.felipe.listadetarefascompose.ui.theme.RADIO_BUTTON_YELLOW_DISABLED
import com.felipe.listadetarefascompose.ui.theme.RADIO_BUTTON_YELLOW_SELECTED
import com.felipe.listadetarefascompose.ui.theme.WHITE
import com.felipe.listadetarefascompose.componentes.Botao
import com.felipe.listadetarefascompose.componentes.CaixaDeTexto
import com.felipe.listadetarefascompose.constantes.Constantes
import com.felipe.listadetarefascompose.ui.theme.*
import com.felipe.listadetarefascompose.viewmodel.TarefasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SalvarTarefa(
    navController: NavController,
    viewModel: TarefasViewModel = hiltViewModel()
){

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Purple700,
                title = {
                    Text(
                        text = "Salvar Tarefa",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WHITE
                    )
                }
            )

        }
    ) {

        var tituloTarefa by remember {
            mutableStateOf("")
        }

        var descricaoTarefa by remember {
            mutableStateOf("")
        }

        var semPrioridadeTarefa by remember {
            mutableStateOf(false)
        }

        var prioridadeBaixaTarefa by remember {
            mutableStateOf(false)
        }

        var prioridadeMediaTarefa by remember {
            mutableStateOf(false)
        }

        var prioridadeAltaTarefa by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            CaixaDeTexto(
                value = tituloTarefa,
                onValueChange = {
                  tituloTarefa = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp),
                label = "Titulo Tarefa",
                maxLines = 1,
                keyboardType = KeyboardType.Text
            )

            CaixaDeTexto(
                value = descricaoTarefa,
                onValueChange = {
                    descricaoTarefa = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(20.dp, 10.dp, 20.dp, 0.dp),
                label = "Descrição",
                maxLines = 5,
                keyboardType = KeyboardType.Text
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(text = "Nível de prioridade")

                RadioButton(
                    selected = prioridadeBaixaTarefa ,
                    onClick = {
                         prioridadeBaixaTarefa = !prioridadeBaixaTarefa
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RADIO_BUTTON_GREEN_DISABLED,
                        selectedColor = RADIO_BUTTON_GREEN_SELECTED
                    )
                )

                RadioButton(
                    selected = prioridadeMediaTarefa,
                    onClick = {
                        prioridadeMediaTarefa = !prioridadeMediaTarefa
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RADIO_BUTTON_YELLOW_DISABLED,
                        selectedColor = RADIO_BUTTON_YELLOW_SELECTED
                    )
                )


                RadioButton(
                    selected = prioridadeAltaTarefa ,
                    onClick = {
                        prioridadeAltaTarefa = !prioridadeAltaTarefa
                    },
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = RADIO_BUTTON_RED_DISABLED,
                        selectedColor = RADIO_BUTTON_RED_SELECTED
                    )
                )
            }

           Botao(
               onClick = {

                    var mensagem = true

                    scope.launch(Dispatchers.IO){
                        if (tituloTarefa.isEmpty()){
                           mensagem = false
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && prioridadeBaixaTarefa){
                           viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.PRIORIDADE_BAIXA,false)
                           mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && prioridadeMediaTarefa){
                            viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.PRIORIDADE_MEDIA,false)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && prioridadeAltaTarefa){
                            viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.PRIORIDADE_ALTA,false)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && descricaoTarefa.isNotEmpty() && semPrioridadeTarefa){
                            viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.SEM_PRIORIDADE,false)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && prioridadeBaixaTarefa){
                            viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.PRIORIDADE_BAIXA,false)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && prioridadeMediaTarefa){
                            viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.PRIORIDADE_MEDIA,false)
                            mensagem = true
                        }else if (tituloTarefa.isNotEmpty() && prioridadeAltaTarefa){
                            viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.PRIORIDADE_ALTA,false)
                            mensagem = true
                        }else{
                            viewModel.salvarTarefa(tituloTarefa,descricaoTarefa,Constantes.SEM_PRIORIDADE,false)
                            mensagem = true
                        }
                    }

                   scope.launch(Dispatchers.Main){
                       if (mensagem){
                           Toast.makeText(context,"Sucesso ao salvar a tarefa!",Toast.LENGTH_SHORT).show()
                           navController.popBackStack()
                       }else{
                           Toast.makeText(context,"Título da tarefa é obrigatório",Toast.LENGTH_SHORT).show()
                       }
                   }
               },
               modifier = Modifier
                   .fillMaxWidth()
                   .height(80.dp)
                   .padding(20.dp),
               texto = "Salvar"
           )
            
        }
    }
}