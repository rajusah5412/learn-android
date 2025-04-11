package com.example.snackbar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snackbar.ui.theme.GalleryTheme
import com.example.snackbar.ui.theme.Purple40

@Composable
fun OnBoardingScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Purple40),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier.size(100.dp),
                tint = Color.White
            )
        }

        Spacer(Modifier.size(50.dp))

        Text(
            "Material Design\nUI Kit Freebie", fontWeight = FontWeight.Bold,
            fontSize = 24.sp, textAlign = TextAlign.Center,
            color = Color.White
        )
//        UI Kit Freebie
        Spacer(Modifier.size(25.dp))
        Text("lorme ipsum dolumx",
            color = Color.White)
        Spacer(Modifier.size(60.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column {
                Button(onClick = {}, shape = RectangleShape, colors = ButtonDefaults.buttonColors().copy(
                    contentColor = Purple40,
                    containerColor = Color.White
                )) {
                    Text("SIGN UP")
                }
            }
            Column {
                Button(onClick = {}, shape = RectangleShape, colors = ButtonDefaults.buttonColors().copy(
                    contentColor = Purple40,
                    containerColor = Color.White
                )) {
                    Text("LOGIN")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOnBoardingScreen() {
    GalleryTheme {
        OnBoardingScreen()
    }


}