package com.edu.memorygame.model;

public class Time {
    private int hour;
    private int minute;
    private int second;
    private int totalSeconds;

    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.totalSeconds = 0;
    }

    public Time(String currentTime) {
        String[] time = currentTime.split(":");
        hour = Integer.parseInt(time[0]);
        minute = Integer.parseInt(time[1]);
        second = Integer.parseInt(time[2]);
        totalSeconds = 0;
    }

    public String getCurrentTime(){
        return hour + ":" + minute + ":" + second;
    }

    public void oneSecondPassed(){
        totalSeconds++;
        second++;
        if(second == 60){
            minute++;
            second = 0;
            if(minute == 60){
                hour++;
                minute = 0;
            }
        }
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }
}
