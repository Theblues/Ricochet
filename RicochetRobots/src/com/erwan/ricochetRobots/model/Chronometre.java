package com.erwan.ricochetRobots.model;

public class Chronometre {

    private long startTime;
    private long timeSwap;
    private long finalTime;
    private int secondes;
    private int minutes;

    public Chronometre() {
	startTime = 0L;
	timeSwap = 0L;
	finalTime = 0L;
	secondes = 0;
	minutes = 0;
    }

    public long getStartTime() {
	return startTime;
    }

    public void setStartTime(long startTime) {
	this.startTime = startTime;
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
	this.minutes = (int) (this.finalTime / 60000);
	this.secondes = (int) ((this.finalTime / 1000) % 60);
    }

    public int getSecondes() {
        return secondes;
    }

    public int getMinutes() {
        return minutes;
    }
}
