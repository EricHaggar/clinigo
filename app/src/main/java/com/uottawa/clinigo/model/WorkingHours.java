package com.uottawa.clinigo.model;

import java.util.ArrayList;

public class WorkingHours {

    private ArrayList<String> startTime;
    private ArrayList<String> endTime;

    public WorkingHours() {
        this.startTime = new ArrayList<>();
        this.endTime = new ArrayList<>();

        //Default Working hours
        for (int i = 0; i < 7; i++) {
            this.startTime.add("--");
            this.endTime.add("--");
        }
    }

    public WorkingHours(ArrayList<String> startTime, ArrayList<String> endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Getter for startTime attribute
     *
     * @return ArrayList<String> startTime
     */
    public ArrayList<String> getStartTime() {
        return this.startTime;
    }

    /**
     * Setter for role startTime
     *
     * @param startTime ArrayList<String> to be assigned
     */
    public void setStartTime(ArrayList<String> startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for endTime attribute
     *
     * @return ArrayList<String> endTime
     */
    public ArrayList<String> getEndTime() {
        return this.endTime;
    }

    /**
     * Setter for role endTime
     *
     * @param endTime ArrayList<String> to be assigned
     */
    public void setEndTime(ArrayList<String> endTime) {
        this.endTime = endTime;
    }
}
