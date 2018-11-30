package timetablegenerator;

import java.text.DecimalFormat;

/**
 * Sessions consist of a room and a time object
 * @author 1385931, 16959932
 */

public class Session {
 
    private String room, day;
    private float startTime, endTime;
   
    public Session(String room, String day, Float start, Float end){
        this.room = room;
        this.startTime = start;
        this.endTime = end;
        this.day = day;
    }

    public Session() {
        
        this.room = "room unknown";
        this.day = "day unkown";
    }
    
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return the day
     */
    public String getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * @return the startTime
     */
    public float getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public float getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString(){
        
        DecimalFormat timeFormat = new DecimalFormat("##.##");
        return getDay() + " " + timeFormat.format(getStartTime()) + " to " + timeFormat.format(getEndTime() + " in room " + getRoom());
    }
}
