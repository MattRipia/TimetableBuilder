package timetablegenerator;

import java.util.ArrayList;

/**
 * Every paper has an array of Stream objects 
 * each stream has a stream number and a list of sessions
 * @author 1385931, 16959932
 */

class Stream {

    private ArrayList<Session> sessions;
    private String semester;
    private String year;
    private String streamNumber;
    
    public Stream(String stream, String year, String semester)
    {
        this.setSessions(new ArrayList());
        this.setYear(year);
        this.setStreamNumber(stream);
        this.setSemester(semester);
    }

    public Stream(ArrayList<Session> sessions, String streamNumber) {
        this.setSessions(sessions);
        this.setStreamNumber(streamNumber);
    }
    
    public Stream(Session session, String sNumber){
        
        this.setSessions(new ArrayList<>());
        getSessions().add(session);
        this.setStreamNumber(sNumber);
    }
    
    public Stream(String streamNo)
    {
        this.setStreamNumber(streamNo);
        setSessions(new ArrayList());
    }

    public Stream() {
        
        Session newSession = new Session();
        this.setSessions(new ArrayList<>());
        this.getSessions().add(newSession);
        this.setStreamNumber("unknown");
    }
    
    public void addSession(Session parsedSession){
        getSessions().add(parsedSession);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }
    
    public Session getSessionAtIndex(int i){
        return this.getSessions().get(i);
    }
    
    public int getSessionLength(){
        return this.getSessions().size();
    }

    public String getStreamNumber() {
        return streamNumber;
    }

    public void setStreamNumber(String streamNumber) {
        this.streamNumber = streamNumber;
    }

    public String getSemester() {
        return semester;
    }

    public String getYear() {
        return year;
    }
    
    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString(){

        String temp = "";

        for(int i = 0; i < getSessions().size(); i++)
        {
            temp += getSessions().get(i).toString() + "\n";
        }

        return  "StreamNo: " +getStreamNumber() + "\n" + "Session details:\n" + temp;
    }

}
