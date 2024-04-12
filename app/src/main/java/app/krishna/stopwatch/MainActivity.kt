package app.krishna.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.sharp.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.krishna.stopwatch.data.Stopwatches
import app.krishna.stopwatch.utils.Time
import app.krishna.stopwatch.data.stopwatches
import app.krishna.stopwatch.ui.theme.StopWatchTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopWatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    StopwatchApp()
                }
            }
        }
    }
}

@Composable
fun StopwatchApp(){
    StopwatchList(stopwatches = stopwatches)
}

@Composable
fun StopwatchList(stopwatches: List<Stopwatches>){
    Scaffold(
        topBar = {
            StopWatchAppBar(modifier = Modifier.background(MaterialTheme.colorScheme.background))
        }
    ) { LazyColumn(contentPadding = it) {
            items(stopwatches) { StopwatchCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                )
            }
        }
    }
}

@Composable
fun StopwatchCard(modifier: Modifier = Modifier){
    Card(
        modifier = modifier
            .padding(10.dp)

    ) {

        Row(modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
            Spacer(modifier = modifier.weight(1f))
            StopwatchText(modifier = modifier)
        }
    }
}

fun Int.twoDigits(): String{
    return  if (this < 10) "0$this" else this.toString()
}

@Composable
fun StopWatchAppBar(modifier: Modifier = Modifier){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
        ) {
        Image(painter = painterResource(R.drawable.stopwatch_icon), contentDescription = null, modifier = modifier)
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier
        )
        Spacer(modifier = modifier.weight(1f))
    }
}

@Composable
fun StopwatchText(modifier: Modifier = Modifier){
    val time by remember {
        mutableStateOf( Time(hours = 0, minutes = 0, seconds = 0))
    }
    var timeInString: String by remember {
      mutableStateOf("${time.hours.twoDigits()}:${time.minutes.twoDigits()}:${time.seconds.twoDigits()}")
    }
    val coroutineScope = rememberCoroutineScope()
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.weight(2f)
        ) {
            Icon(
                imageVector = Icons.Sharp.Timer,
                contentDescription = null,
                modifier = Modifier
                    .padding(14.dp)
                    .size(50.dp)
            )
            Text(
                text = timeInString,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        var playing:Boolean by remember {
            mutableStateOf(false)
        }
        var played by remember {
            mutableStateOf(false)
        }

        Column {
            IconButton(
                onClick = {
                    playing = !playing
                    played = true
                    if (playing) {
                        coroutineScope.launch {
                            while (playing) {
                                delay(1000) // Delay for 1 second
                                time.seconds++
                                if (time.seconds == 60) {
                                    time.seconds = 0
                                    time.minutes++
                                    if (time.minutes == 60) {
                                        time.minutes = 0
                                        time.hours++
                                    }
                                }
                                timeInString =
                                    "${time.hours.twoDigits()}:${time.minutes.twoDigits()}:${time.seconds.twoDigits()}"
                            }
                        }
                    }
                },
                Modifier
                    .weight(1f)
            ) {
                if (!playing) Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    Modifier
                        .weight(1f)
                        .size(60.dp)
                )
                else
                    Icon(
                        imageVector = Icons.Filled.Pause,
                        contentDescription = null,
                        Modifier
                            .weight(1f)
                            .size(60.dp)
                    )
            }
            if (played){
            IconButton(
                onClick = {
                    time.reset()
                    played = false
                timeInString =
                    "${time.hours.twoDigits()}:${time.minutes.twoDigits()}:${time.seconds.twoDigits()}"
                }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
            }

            }
        }
    }
}


@Composable
@Preview
fun StopwatchAppPreview(){
    StopWatchTheme {
     StopwatchApp()
    }
}