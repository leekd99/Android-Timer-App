package edu.temple.mytimer;

public class Timer {

    private int seconds;
    private int minutes;
    private boolean limitReached;

    //constructor method
    public Timer(){

        this.seconds = 0;
        this.minutes = 0;
        this.limitReached = false;

    }//end timer

    public void increment(){

        //check for roll over
        if(seconds == 59 ){

            if (minutes != 59){

                seconds = 0;

            }//end

            //if hours isn't reached yet
            if(minutes < 59){

                minutes++;

            } else {

                System.out.println("Max Time reached!");
                this.limitReached = true;
                return;

            }//end check for max time

        } else if(minutes <= 59) {

            seconds++;

        }//end check for roll over

    }//end increment

    public boolean timerLimitReached(){

        return limitReached;

    }//end

    public void reset(){
        minutes = 0;
        seconds = 0;
    }//end reset

    public String formatToString(){

        String secondsDisplay;
        String minuteDisplay;

        //format seconds
        if (seconds < 10){

            secondsDisplay = "0" + seconds;

        } else {

            secondsDisplay = Integer.toString(seconds);

        }//end second format

        //format minutes
        if (minutes < 10){

            minuteDisplay = "0" + minutes;

        } else {

            minuteDisplay = Integer.toString(minutes);

        }//end minute format

        return minuteDisplay + ":" + secondsDisplay;

    }//end formatToString

}//end Timer class

