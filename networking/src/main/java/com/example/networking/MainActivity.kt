package com.example.networking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.example.networking.data.remote.FREEPIK_HEADER
import com.example.networking.data.remote.FREEPIK_TOKEN
import com.example.networking.data.remote.NetworkClient
import com.example.networking.data.remote.URL
import com.example.networking.model.User
import com.example.networking.ui.theme.GalleryTheme
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import java.io.FileOutputStream
import java.io.InputStream
import java.io.ObjectOutputStream
import java.io.Serializable


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryTheme {

                var photoState = remember { mutableStateOf<ApiResponse>(ApiResponse.Loading) }
                var image by remember { mutableStateOf<ImageBitmap?>(null) }
                Column(Modifier.statusBarsPadding()) {
                    Text("hello")
                    LaunchedEffect(Unit) {
                        launch(Dispatchers.IO) {
                            photoState.value = downloadFromFreePik(this@MainActivity)
                        }
                    }

                    when(photoState.value){
                        is ApiResponse.DownloadInfo -> {
                           val info = photoState.value as ApiResponse.DownloadInfo
                            Image(bitmap = info.bitmap!!.asImageBitmap(), "")
                            Text(info.fileName)
                        }
                        ApiResponse.Error -> Text("Something went wrong")
                        ApiResponse.Loading -> CircularProgressIndicator()
                    }


                }
            }
        }
    }
}

fun loadScaledBitmap(inputStream: InputStream, reqWidth: Int, reqHeight: Int): Bitmap? {
    // Step 1: Decode with inJustDecodeBounds=true to get image dimensions
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    BitmapFactory.decodeStream(inputStream, null,  options)

    // Step 2: Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

    // Step 3: Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false
    return BitmapFactory.decodeStream(inputStream, null, options)
}

// Helper function to calculate a power-of-2 sample size
fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.outHeight to options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        // Increase the inSampleSize to the largest value that keeps both
        // height and width larger than requested dimensions
        while ((halfHeight / inSampleSize) >= reqHeight &&
            (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

suspend fun getDataFromSomewhere(): User? {

    val response = NetworkClient.client.get("https://jsonplaceholder.typicode.com/todos/1")
    if (response.status == HttpStatusCode.OK) {
        return response.body<User>()

    }
    return null

//    val url = URL("https://jsonplaceholder.typicode.com/todos/1")
//    val connection = url.openConnection() as HttpURLConnection
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        println(String(connection.inputStream.readAllBytes()))
//    }
}

sealed class ApiResponse {
    data object Loading : ApiResponse()
    data class DownloadInfo(val fileName: String, val bitmap: Bitmap? = null) : ApiResponse()
    data object Error : ApiResponse()

}

@RequiresApi(Build.VERSION_CODES.P)
suspend fun downloadFromFreePik(context: Context): ApiResponse {
    val response = NetworkClient.client.get(URL, {
        header(FREEPIK_HEADER, FREEPIK_TOKEN)
    })
    if (response.status == HttpStatusCode.OK) {
        val apiData = response.body<ApiData>()
        val resp = NetworkClient.client.get(apiData.data.url) {
            header(FREEPIK_HEADER, FREEPIK_TOKEN)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return try {
                val source = ImageDecoder.createSource(resp.bodyAsBytes())
                ApiResponse.DownloadInfo(
                        fileName = apiData.data.fileName,
                bitmap = ImageDecoder.decodeBitmap(source)
                )
            } catch (e: Exception) {
                ApiResponse.Error
            }
        }else {
            val byteArray = resp.bodyAsBytes()

            ApiResponse.DownloadInfo(
                fileName = apiData.data.fileName,
                bitmap =   BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            )
        }
    }
    return ApiResponse.Error
}



class Person(val name: String, val age: Int) : Serializable


fun serializeObj(person: Person) {

    ObjectOutputStream(FileOutputStream("person.ser")).writeObject(person)
}


@kotlinx.serialization.Serializable
data class ApiData(
    val data: PhotoData
)

@kotlinx.serialization.Serializable
data class PhotoData(
    @SerialName("filename")
    val fileName: String,
    val url: String
)
