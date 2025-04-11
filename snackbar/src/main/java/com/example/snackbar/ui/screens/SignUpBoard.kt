package com.example.snackbar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpBoard(modifier: Modifier = Modifier) {

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize()) {

//        TopAppBar(
//            navigationIcon = { Icon(Icons.Default.Menu, "") },
//            title = { Text("SIGNUP") }
//
//        )
        CustomAppBar(
            icon = Icons.Default.Menu,
            title = "Dashboard",
            align = TextAlign.Center
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)){
            Text("Sign Up", fontSize = 36.sp)
            Text("Material design UI Kit", fontSize = 20.sp)
        }
        UserDetails()

        Column( Modifier.fillMaxWidth(.8f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {}, shape = RectangleShape,
                modifier = Modifier.fillMaxWidth()) {
                Text("SIGN UP")
            }
            Text("ALREADY HAVE AN ACCOUNT? LOG IN")
        }

    }
}

@Preview
@Composable
private fun PreviewUserDetails() {
    UserDetails()
    
}

@Composable
fun UserDetails(modifier: Modifier = Modifier) {
    Column(Modifier.fillMaxWidth(.8f)) {
        TextField(
            value = "",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            placeholder = {
                Text("Username")
            })
        TextField(
            value = "",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            placeholder = {
                Text("Email")
            })
        TextField(
            value = "",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            placeholder = {
                Text("Password")
            })
        Row(Modifier.fillMaxWidth()) {
            TextField(
                value = "",
                modifier= Modifier.weight(1f),
                onValueChange = {},
                placeholder = {
                    Text("Country")
                })
            TextField(
                value = "",
                modifier= Modifier.weight(1f),
                onValueChange = {},
                placeholder = {
                    Text("City")
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SignUpBoard()
}

@Composable
fun CustomAppBar(modifier: Modifier = Modifier,
                 icon: ImageVector,
                 title: String ,
                 align: TextAlign) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(icon, "")
        Text("DASHBOARD", textAlign = align, modifier = Modifier.weight(1f))
//            Icon(Icons.Default.Menu, "")
    }
    add(45, "abc")
}

fun add(num: Int, name: String){
    
}