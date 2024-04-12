package app.krishna.stopwatch.utils

class Time(
    var hours: Int,
    var minutes: Int,
    var seconds: Int
){
    fun runStopWatch(playing: Boolean){
        while (playing){
            if (seconds < 60){
                seconds++
            }
            else if (seconds == 60){
                minutes++
            }
            else if (minutes == 60){
                hours++
            }
        }
    }
    fun reset(){
        hours = 0
        minutes = 0
        seconds = 0
    }
}
