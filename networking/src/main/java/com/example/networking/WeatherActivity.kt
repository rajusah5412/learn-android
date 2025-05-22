package com.example.networking

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.networking.Result.Loading
import com.example.networking.data.remote.NetworkClient
import com.example.networking.data.remote.models.WeatherResponse
import com.example.networking.ui.theme.GalleryTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date


const val WEATHER_ICON = " https://openweathermap.org/img/wn/10d@2x.png"

class WeatherActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryTheme {
                Box(Modifier.statusBarsPadding()) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var cityName by remember { mutableStateOf("") }
        var weather by remember { mutableStateOf<Result<WeatherResponse>?>(null) }
        val scope = rememberCoroutineScope()
        OutlinedTextField(value = cityName, onValueChange = { cityName = it })
        Spacer(Modifier.size(height = 16.dp, width = 0.dp))
        OutlinedButton(onClick = {
            scope.launch {
                weather = Loading
                weather = getWeatherForCity(cityName)
            }
        }) {
            Text("Search")
        }

        weather?.let {
            when (it) {
                is Result.Error -> {

                    Text(it.throwable?.message ?: "Something went wrong....")
                }

                Loading -> {
                    CircularProgressIndicator()
                }

                is Result.Success<*> -> {
                    val res = weather as Result.Success
                    WeatherCard<WeatherResponse>(response = res.data!!) {
                        AsyncImage(
                            model = "https://openweathermap.org/img/wn/${it.weather[0].id}@2x.png",
                            contentDescription = null,
                        )
                        Text("Country Code: ${it.sys.country}")
                        Text("Country Name: ${it.name}")
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Text(("Sunrise : ${UnixTimestampConverter.convert(it.sys.sunrise)}"))
                        } else {
                            Text(("Sunrise : ${Date(it.sys.sunrise * 1000L)}"))

                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Text(("Sunset : ${UnixTimestampConverter.convert(it.sys.sunset)}"))
                        } else {
                            Text(("Sunset : ${Date(it.sys.sunset * 1000L)}"))

                        }



                        Text("Temp: ${it.main.temp}")
                        Text("Feels Like: ${it.main.feelsLike}")
                        Text("Max: ${it.main.tempMax}")
                        Text("Min: ${it.main.tempMin}")
                        Text("Description: ${it.weather[0].description}")
//                        Text(it.toString())
                    }
                }
            }
        }
    }
}


object UnixTimestampConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    fun convert(timestamp: Long): LocalDateTime? {

        // Convert to Instant
        val instant: Instant = Instant.ofEpochSecond(timestamp)

        // Convert to LocalDateTime using system default time zone
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        return dateTime
    }
}

@Composable
fun <T> WeatherCard(modifier: Modifier = Modifier, response: T, content: @Composable (T) -> Unit) {
    content(response)
}


suspend fun getWeatherForCity(city: String): Result<WeatherResponse> {

    return withContext(Dispatchers.IO) {
        try {
            val response =
                NetworkClient.client.get {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = "api.openweathermap.org"
                        path("data/2.5/weather")
                        parameters.append("q", city)
                        parameters.append("appid", "c1f7e8669f6dfdd4efd400c2d9959700")
                        parameters.append("units", "metric")
                    }


                }
//                NetworkClient.client.get("https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=c1f7e8669f6dfdd4efd400c2d9959700&units=metric")
            if (response.status == HttpStatusCode.OK) {
                return@withContext Result.Success<WeatherResponse>(data = response.body<WeatherResponse>())
            } else {
                throw Exception("something went wrong")
            }
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

}

sealed interface Result<out T> {
    data object Loading : Result<Nothing>
    data class Success<T>(val data: T? = null) : Result<T>
    data class Error(val throwable: Throwable? = null) : Result<Nothing>
}


//@Composable
//fun MyImage(modifier: Modifier = Modifier, model : String) {
//    val bitmap  by remember { mutableStateOf<Bitmap?>(null)}
//    LaunchedEffect(Unit) {
//        NetworkClient.client.get("")
//    }
//
//    Image(bitmap = bitmap.asImageBitmap(), "")
//}