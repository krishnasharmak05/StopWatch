package app.krishna.stopwatch.data

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import app.krishna.stopwatch.R

data class Stopwatches(@StringRes var stopwatch: Int)


var stopwatches  = mutableStateListOf(
    Stopwatches(R.string.stopwatch1),
    Stopwatches(R.string.stopwatch2),
    Stopwatches(R.string.stopwatch3),
    Stopwatches(R.string.stopwatch4),
    Stopwatches(R.string.stopwatch1),
    Stopwatches(R.string.stopwatch1),
    Stopwatches(R.string.stopwatch1),
    Stopwatches(R.string.stopwatch1),
    Stopwatches(R.string.stopwatch1),
    Stopwatches(R.string.stopwatch1)
)