package com.edsoft.allotawatch;

public class task {
    private String name;
    private long timeSpent; //mins
    private long timeAllocated; //mins
    private int listPosition;

    public task (String _name, int _timeAllocated, int _timeSpent) {
        this.name = _name;
        timeSpent = _timeSpent;
        timeAllocated = _timeAllocated;
    }
    public void decrementTime() {
        if (timeAllocated >= 0) {
            timeAllocated = timeAllocated - 1000;
            timeSpent = timeSpent + 1000;
        } else {
            //show notification
        }
    }

    public boolean setListPosition(int x) {
        if(x > 100 || x < 0) {
            return false;
        } else {
            listPosition = x;
            return true;
        }
    }
    public int getListPosition() {
        return listPosition;
    }
    public String getName() {
        return name;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public long getTimeAllocated() {
        return timeAllocated;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setTimeAllocated(long newTime) {
        if(newTime < timeAllocated) {
            this.timeSpent = timeSpent + (timeAllocated-newTime); //TODO: change reciever code and dont to this here
        }
        this.timeAllocated = newTime;
    }
}
