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
        this.sessions = new ArrayList();
        this.year = year;
        this.streamNumber = stream;
        this.semester = semester;
    }

    public Stream(ArrayList<Session> sessions, String streamNumber) {
        this.sessions = sessions;
        this.streamNumber = streamNumber;
    }
    
    public Stream(Session session, String sNumber){
        
        this.sessions = new ArrayList<>();
        sessions.add(session);
        this.streamNumber = sNumber;
    }
    
    public Stream(String streamNo)
    {
        this.streamNumber = streamNo;
        sessions = new ArrayList();
    }

    public Stream() {
        
        Session newSession = new Session();
        this.sessions = new ArrayList<>();
        
        this.sessions.add(newSession);
        this.streamNumber = "unknown";
    }
    
    public void addSession(Session parsedSession){
        sessions.add(parsedSession);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }
    
    public Session getSessionAtIndex(int i){
        return this.sessions.get(i);
    }
    
    public int getSessionLength(){
        return this.sessions.size();
    }

    public String getStreamNumber() {
        return streamNumber;
    }

    public void setStreamNumber(String streamNumber) {
        this.streamNumber = streamNumber;
    }

    /**
     * @return the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
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
