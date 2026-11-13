package com.felipe.listadetarefascompose.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.felipe.listadetarefascompose.R
import com.felipe.listadetarefascompose.componentes.BotaoAuth
import com.felipe.listadetarefascompose.listener.ListenerAuth
import com.felipe.listadetarefascompose.ui.theme.*
import com.felipe.listadetarefascompose.viewmodel.AuthViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Login(
    navController: NavController,
    viewModel: AuthViewModel
){

    val context = LocalContext.current
    var usuarioLogado = viewModel.verificarUsuarioLogado().collectAsState(initial = false).value


    LaunchedEffect(usuarioLogado){
        if (usuarioLogado){
            navController.navigate("listaTarefas")
        }else{
            usuarioLogado = false
        }
    }

    Scaffold(
      modifier = Modifier.background(
          brush = Brush.linearGradient(
              colors = listOf(
                  DARK_PINK,
                  DARK_BLUE
              )
          )
      ),
       backgroundColor = Color.Transparent
    ) {
         
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            var email by remember {
                mutableStateOf("")
            }

            var senha by remember {
                mutableStateOf("")
            }

            var visibilidadeSenha by remember {
                mutableStateOf(true)
            }

            var mensagem by remember {
                mutableStateOf("")
            }

            var icon = if (visibilidadeSenha)
              painterResource(id = R.drawable.baseline_visibility)
            else{
             painterResource(id = R.drawable.baseline_visibility_off)
            }

            Icon(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.baseline_task),
                contentDescription = "Ícone de tarefas",
                tint = WHITE
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                   email = it
                },
                label = {
                    Text(text = "E-mail")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 0.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = WHITE,
                    textColor = Color.Black,
                    cursorColor = Purple700,
                    focusedBorderColor = Purple700,
                    focusedLabelColor = Purple700
                ),
                shape = ShapeEditText.small,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_email),
                        contentDescription = "Ícone de email"
                    )
                },
                maxLines = 1
            )

            OutlinedTextField(
                value = senha,
                onValueChange = {
                    senha = it
                },
                label = {
                    Text(text = "Senha")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp, 20.dp, 0.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = WHITE,
                    textColor = Color.Black,
                    cursorColor = Purple700,
                    focusedBorderColor = Purple700,
                    focusedLabelColor = Purple700
                ),
                shape = ShapeEditText.small,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                trailingIcon = {

                    IconButton(
                        onClick = {
                           visibilidadeSenha = !visibilidadeSenha
                        }
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = "Ícone de email"
                        )
                    }
                },
                maxLines = 1,
                visualTransformation = if (visibilidadeSenha) VisualTransformation.None
                else PasswordVisualTransformation()
            )
            
            Text(text = mensagem, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = WHITE)

            BotaoAuth(
                onClick = {
                   viewModel.login(email,senha, object : ListenerAuth{
                       override fun onSucess(mensagem: String, tela: String) {
                           Toast.makeText(context,mensagem,Toast.LENGTH_SHORT).show()
                           navController.navigate(tela)
                       }

                       override fun onFailure(erro: String) {
                           mensagem = erro
                       }

                   })
                },
                text = "Entrar"
            )

            Spacer(modifier = Modifier.padding(20.dp))

            TextButton(
                onClick = {
                   navController.navigate("cadastro")
                }
            ) {
                Text(
                    text = "Não tem conta? cadastre-se agora!",
                    color = WHITE,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.padding(0.dp,0.dp,0.dp,20.dp))

        }
    }
}