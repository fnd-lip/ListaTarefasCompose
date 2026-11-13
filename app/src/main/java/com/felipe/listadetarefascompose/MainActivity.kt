package com.felipe.listadetarefascompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.felipe.listadetarefascompose.ui.theme.ListaDeTarefasComposeTheme
import com.felipe.listadetarefascompose.view.Cadastro
import com.felipe.listadetarefascompose.view.ListaTarefas
import com.felipe.listadetarefascompose.view.Login
import com.felipe.listadetarefascompose.view.SalvarTarefa
import com.felipe.listadetarefascompose.viewmodel.AuthViewModel
import com.felipe.listadetarefascompose.viewmodel.TarefasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListaDeTarefasComposeTheme {

                val navController = rememberNavController()
                val tarefasViewModel: TarefasViewModel = hiltViewModel()
                val authViewModel: AuthViewModel = hiltViewModel()


                NavHost(navController = navController, startDestination = "login"){

                    composable(
                        route = "login"
                    ){
                        Login(navController,authViewModel)
                    }

                    composable(
                        route = "cadastro"
                    ){
                        Cadastro(navController,authViewModel)
                    }

                    composable(
                        route = "listaTarefas"
                    ){
                        ListaTarefas(navController,tarefasViewModel)
                    }

                    composable(
                        route = "salvarTarefa"
                    ){
                        SalvarTarefa(navController,tarefasViewModel)
                    }
                }

            }
        }
    }
}


