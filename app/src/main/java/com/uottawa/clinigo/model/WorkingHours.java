package com.uottawa.clinigo.model;

import java.util.ArrayList;

public class WorkingHours {

    private ArrayList<String> startTime;
    private ArrayList<String> endTime;

    public WorkingHours() {
        this.startTime = new ArrayList<String>();
        this.endTime = new ArrayList<String>();

        //Default Working hours
        for (int i=0; i<7; i++) {
            this.startTime.add("9:00 AM");
            this.endTime.add("5:00 PM");
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
     * Getter for endTime attribute
     *
     * @return ArrayList<String> endTime
     */
    public ArrayList<String> getEndTime() {
        return this.endTime;
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
     * Setter for role endTime
     *
     * @param endTime ArrayList<String> to be assigned
     */
    public void setEndTime(ArrayList<String> endTime) {
        this.endTime = endTime;
    }
}
