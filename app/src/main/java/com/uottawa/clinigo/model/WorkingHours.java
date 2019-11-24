package com.uottawa.clinigo.model;

import java.util.ArrayList;

public class WorkingHours {

    private ArrayList<String> startTimes;
    private ArrayList<String> endTimes;

    public WorkingHours() {
        this.startTimes = new ArrayList<>();
        this.endTimes = new ArrayList<>();

        //Default Working hours
        for (int i = 0; i < 7; i++) {
            this.startTimes.add("--");
            this.endTimes.add("--");
        }
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
