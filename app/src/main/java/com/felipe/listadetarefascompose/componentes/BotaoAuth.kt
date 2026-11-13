package com.felipe.listadetarefascompose.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felipe.listadetarefascompose.ui.theme.DARK_BLUE
import com.felipe.listadetarefascompose.ui.theme.DARK_PINK
import com.felipe.listadetarefascompose.ui.theme.ShapeButton
import com.felipe.listadetarefascompose.ui.theme.WHITE

@Composable
fun BotaoAuth(
    onClick: () -> Unit,
    text: String
){

    OutlinedButton(
        onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .padding(20.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        DARK_PINK,
                        DARK_BLUE
                    )
                ),
                shape = ShapeButton.medium
            ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            disabledElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        shape = ShapeButton.medium,
        border = BorderStroke(2.dp, WHITE)
    ) {
       Text(text = text, fontSize = 18.sp, color = WHITE, fontWeight = FontWeight.Bold)
    }
}

@Composable
@Preview
private fun BotaoAuthPreview(){
    BotaoAuth(onClick = {}, text = "Login")
}