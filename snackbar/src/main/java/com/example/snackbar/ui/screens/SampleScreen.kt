package com.example.snackbar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InfoScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
//                modifier = Modifier.padding(bottom = 20.dp),
            title = { Text("Info Screen", textAlign = TextAlign.Center) },
            navigationIcon = {
                Icon(Icons.Default.Menu, contentDescription = "")
            },
            actions = {
                Text("Logout")
            }
        )

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

               FlowRow(Modifier.fillMaxWidth(),
                   verticalArrangement = Arrangement.spacedBy(16.dp),
                   horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                   infoList.forEach {
                       InfoCard(icon = it.icon, text = it.text)
                   }
               }


//            FlowRow(Modifier.fillMaxWidth() ,
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)) {
//                InfoCard()
//                InfoCard()
//                InfoCard()
//                InfoCard()
//            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun InfoScreenPreview() {
    InfoScreen()

}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Info,
//    content: @Composable () -> Unit = {}
    text: String = "This text"
) {
    Card(
//        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Icon(icon, "", modifier=
        Modifier.align(Alignment.CenterHorizontally).padding(vertical = 16.dp, horizontal = 50.dp).size(64.dp))
        Text(text, textAlign = TextAlign.Center, modifier =  Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp).width(120.dp),
            overflow = TextOverflow.Clip)
    }
}

data class InfoState(val icon : ImageVector, val text: String)

val infoList = listOf(
    InfoState(Icons.Default.Person, "Users"),
    InfoState(Icons.Default.AccountBox, "Contacts"),
    InfoState(Icons.Default.AccountBox, "Groups and Associations"),
    InfoState(Icons.Filled.Build, "Documents"),
    InfoState(Icons.Filled.DateRange, "Incident types and response guidelines")

)