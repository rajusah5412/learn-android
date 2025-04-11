package com.example.snackbar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard() {

    Column(Modifier.fillMaxSize()) {
//        TopAppBar(
//            navigationIcon = {Icon(Icons.Default.Menu, "")},
//            title = {Text("DASHBOARD")}
//
//        )
         Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
        ){
            Icon(Icons.Default.Menu, "")
            Text("DASHBOARD", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
//            Icon(Icons.Default.Menu, "")
        }
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Icon(Icons.Default.Person, "", modifier = Modifier.size(64.dp))
//            Column() {
//                Text("Direct")
//                Text("X")
//            }
//        }
        ChatItem(icon = Icons.Default.Person,
            text1 = "JHON DOE", text2 = "Moscow")
        Spacer(Modifier.size(24.dp))
        ChatItem(icon = Icons.Default.Person, text1 = "JACK HARRIS", text2 = "New York")
        Spacer(Modifier.size(24.dp))
        ChatItem(icon = Icons.Default.Person, text1 = "JOE", text2 = "New York")

    }

}

//@Preview(showBackground = true)
//@Composable
//private fun PreviewChatItem() {
//    ChatItem()
//
//}

@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    icon: ImageVector   ,
    text1: String,
    text2: String,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(icon, "", modifier = Modifier.size(64.dp))
        Column() {
            Text(text1)
            Text(text2)
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    DashBoard()
}

//fun add(
//    num1: Int,
//    num2 : Int,
//): Int{
//    return num2 + num1
//}