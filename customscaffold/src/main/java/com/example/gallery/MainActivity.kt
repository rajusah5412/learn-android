package com.example.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gallery.ui.theme.GalleryTheme


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GalleryTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {

                    CustomScaffold(
                        topBar = {
                            TopAppBar(title = {
                                Text("My app")
                            })
                        },
                        bottomBar = {
                            BottomAppBar { }
                        },
                        fabBarPosition = FabBarPosition.Center,
                        floatingActionButton = {
                            FloatingActionButton(onClick = {},
                                ) {
                                Icon(Icons.Default.Delete, "")
                            }
                        }
                    ) {
                        Text("hello world")
                        Button(onClick = {}) {
                            Text("Click here")
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    fabBarPosition: FabBarPosition = FabBarPosition.BottomRight ,
    content: @Composable ColumnScope.() -> Unit
) {
    val fabBarAlignment = when(fabBarPosition){
        FabBarPosition.Center -> Alignment.BottomCenter
        FabBarPosition.BottomLeft -> Alignment.BottomStart
        else -> Alignment.BottomEnd
    }

    Box(contentAlignment = fabBarAlignment){
        Column(Modifier.fillMaxSize()) {
            val topBarBackgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.Yellow
            val textColor = if (isSystemInDarkTheme()) Color.White else Color.Black

            topBar()
            Column(Modifier.weight(3f)) {
                content()
            }

            bottomBar()

        }
        Box(modifier = Modifier.padding(bottom = 85.dp, end = 8.dp)){
            floatingActionButton()
        }
    }
}


enum class FabBarPosition {
    Center,
    BottomLeft,
    BottomRight
}