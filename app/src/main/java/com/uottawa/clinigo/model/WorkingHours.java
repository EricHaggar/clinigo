package com.uottawa.clinigo.model;

import java.util.ArrayList;

public class WorkingHours {

    private ArrayList<String> startTimes;
    private ArrayList<String> endTimes;

    public WorkingHours() {
        startTimes = new ArrayList<>();
        endTimes = new ArrayList<>();
    }

    public WorkingHours(ArrayList<String> startTimes, ArrayList<String> endTimes) {
        this.startTimes = startTimes;
        this.endTimes = endTimes;
    }

    /**
     * Getter for startTimes attribute
     *
     * @return ArrayList<String> startTimes
     */
    public ArrayList<String> getStartTimes() {
        return this.startTimes;
    }

    /**
     * Setter for role startTimes
     *
     * @param startTimes ArrayList<String> to be assigned
     */
    public void setStartTimes(ArrayList<String> startTimes) {
        this.startTimes = startTimes;
    }

    /**
     * Getter for endTimes attribute
     *
     * @return ArrayList<String> endTimes
     */
    public ArrayList<String> getEndTimes() {
        return this.endTimes;
    }

    /**
     * Setter for role endTimes
     *
     * @param endTimes ArrayList<String> to be assigned
     */
    public void setEndTimes(ArrayList<String> endTimes) {
        this.endTimes = endTimes;
    }
}
