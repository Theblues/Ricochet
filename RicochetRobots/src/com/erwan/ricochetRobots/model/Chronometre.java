package com.erwan.ricochetRobots.model;

import android.os.Handler;

public class Chronometre {

    private long startTime;
    long timeInMillies;
    long timeSwap;
    long finalTime;
    
    public Chronometre()
    {
	startTime = 0L;
	timeInMillies = 0L;
	timeSwap = 0L;
	finalTime = 0L;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTimeInMillies() {
        return timeInMillies;
    }

    public void setTimeInMillies(long timeInMillies) {
        this.timeInMillies = timeInMillies;
    }

    public long getTimeSwap() {
        return timeSwap;
    }

    public void setTimeSwap(long timeSwap) {
        this.timeSwap = timeSwap;
    }

    public long getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(long finalTime) {
        this.finalTime = finalTime;
    }	
}
