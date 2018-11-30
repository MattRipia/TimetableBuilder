package timetablegenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaperDatabase 
{
    private ArrayList<Paper> paperArrayList, semesterOnePapers, semesterTwoPapers, selectedPapers;
    public ArrayList<TimetableGenerator.Timetable> timetableArrayList = new ArrayList();
    private TimetableGenerator.Timetable aTimetable = new TimetableGenerator.Timetable();
    private Connection conn, conn2, jdbcConn;
    private Statement statement, statement2, statement3;
  
    public PaperDatabase()
    {
        this.paperArrayList = new ArrayList();
        this.semesterOnePapers = new ArrayList();
        this.semesterTwoPapers = new ArrayList();
        this.selectedPapers = new ArrayList();
        this.conn = null;
        this.statement = null;
        
        // connects to either the server or derby database depending on the string "derby" || "server"
        // returns true if a connection is made, then reads in the database into a paper arrayList
        // it then uses a method to figure out if the papers are in s1 || s2 and adds those to the semesterOnePapers || semseterTwoPapers
        if(this.connectToDB("derby"))
        {
            try {
                this.readInDB();
                populateSemesterLists();
                 
            } catch (Exception ex) {
                Logger.getLogger(PaperDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("Error in paperDatabase constructor | Not connected!");
        }
    }
    
    // this reads in the database, then pre-populates the semester one/two lists with the respective papers
    // so that when a user selects a different semester, the papers already exist in the list which correspond
    // to the selection
    private void populateSemesterLists(){
        
        // for each paper in the arrayList
        for(Paper aPaper : paperArrayList)
        {
            ArrayList<Stream> semesterOneStreams = new ArrayList();
            ArrayList<Stream> semesterTwoStreams = new ArrayList();
            
            // for each stream in a paper
            for(Stream aStream : aPaper.getStreams())
            {
                // a s1 stream, add this to the temp arrayList
                if(aStream.getSemester().toLowerCase().contains("s1"))
                {
                    semesterOneStreams.add(aStream);
                }
                // a s2 stream, add this to the temp arrayList 
                else if (aStream.getSemester().toLowerCase().contains("s2"))
                {
                    semesterTwoStreams.add(aStream);
                }
            }
            
            // removes all the streams from aPaper, allowing us to add to only add s1 streams or s2 streams
            aPaper.removeAllStreams();
              
            if(semesterOneStreams.size() > 0)
            {
                aPaper.setStream(semesterOneStreams);
                getSemesterOnePapers().add(aPaper);
            }
            
            // clearing the streams again, incase there are s1 and s2 streams for the current paper
            aPaper.removeAllStreams();
            
            if(semesterTwoStreams.size() > 0)
            {
                aPaper.setStream(semesterTwoStreams);
                getSemesterTwoPapers().add(aPaper);
            }
        }
    }
    
    // this method is used to create a local database using the java derby driver
    // it first connects to the mysql server at 192.168.1.2, gets all the paper details
    // and uses those paper details to insert into the local derby database 
    // so that I dont need to connect to the server again.
    private void copyServerToDerbyDB()
    {
        try{
            // connects to both the server located at 192.168.1.2 and the local derby database
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.2:3306/autpapers", "Matt", "password");
            statement = conn.createStatement();
            
            jdbcConn = DriverManager.getConnection("jdbc:derby:autpapers;create=true");
            statement3 = jdbcConn.createStatement();
            
            //creates the local db tables | only needs to be executed once
            statement3.executeUpdate("create table paper(paper_id integer, paper_name varchar(100), paper_code varchar(7))");
            System.out.println("paper table created!");
            statement3.executeUpdate("create table stream(paper_id integer, stream_no varchar(10), yearOffered integer, semester varchar(10))");
            System.out.println("stream table created!");
            statement3.executeUpdate("create table session(paper_id integer, stream_no varchar(10), room varchar(7), day varchar(4), start_time float, end_time float)");
            System.out.println("session table created!");
            
            // gets the results from the server database, inserts them into the derby database
            ResultSet paperResults = statement.executeQuery("select paper_id, paper_name, paper_code from paper");
            while(paperResults.next())
            {
                statement3.executeUpdate("insert into paper values("+paperResults.getInt(1)+",'" + paperResults.getString(2) + "','" + paperResults.getString(3) + "')");
            }
            // tests the local database to ensure the papers were added
            ResultSet paperTest = statement3.executeQuery("select * from paper");
            while(paperTest.next())
            {
                System.out.println(paperTest.getString(1) + " " + paperTest.getString(2) + " " + paperTest.getString(3));
            } 

            // gets the results from the server database, inserts them into the derby database
            ResultSet streamResults = statement.executeQuery("select paper_id, stream_no, year, semester from stream");
            while(streamResults.next())
            {
                statement3.executeUpdate("insert into stream values("+streamResults.getInt(1)+",'" + streamResults.getString(2)
                                        + "'," + streamResults.getInt(3) + ",'" + streamResults.getString(4) + "')");
            }
            // tests the local database to ensure the papers were added
            ResultSet StreamTest = statement3.executeQuery("select * from stream");
            while(StreamTest.next())
            {
                System.out.println(StreamTest.getString(1) + " " + StreamTest.getString(2) + " " + StreamTest.getString(3)+ " " + StreamTest.getString(4));
            }
            
            // gets the results from the server database, inserts them into the derby database
            ResultSet sessionResults = statement.executeQuery("select paper_id, stream_no, room, day, start_time, end_time from session");
            while(sessionResults.next())
            {
                statement3.executeUpdate("insert into session values("+sessionResults.getInt(1)+",'" + sessionResults.getString(2) 
                + "','" + sessionResults.getString(3) + "','" + sessionResults.getString(4)+ "'," + sessionResults.getFloat(5) + "," +sessionResults.getFloat(6) +")");
            }
            // tests the local database to ensure the papers were added
            ResultSet sessionTest = statement3.executeQuery("select * from session");
            while(sessionTest.next())
            {
                System.out.println(sessionTest.getString(1) + " " + sessionTest.getString(2) + " " + sessionTest.getString(3)
                + " " + sessionTest.getString(4) + " " + sessionTest.getString(5) + " " + sessionTest.getString(6));
            }
            
        }catch(Exception ex)
        {
            System.err.println("error creating derby db | " + ex.getLocalizedMessage());
        } 
    }
    
    private Boolean connectToDB(String serverType){
        
        // connecting to the server database or the local derby database
        try{
            if(serverType.contains("server"))
            {
                conn = DriverManager.getConnection("jdbc:mysql://192.168.1.2:3306/autpapers", "Matt", "password");
                statement = conn.createStatement();

                conn2 =  DriverManager.getConnection("jdbc:mysql://192.168.1.2:3306/autpapers", "Matt", "password");
                statement2 = conn2.createStatement();
                return true;
            }
            else if(serverType.contains("derby"))
            {
                System.out.println("Connecting to the derby DB");
                conn = DriverManager.getConnection("jdbc:derby:autpapers");
                statement = conn.createStatement();
                System.out.println("first connection successful");
                
                conn2 = DriverManager.getConnection("jdbc:derby:autpapers");
                statement2 = conn2.createStatement(); 
                System.out.println("second connection successful");
                return true;
            }
            
        }catch(Exception ex)
        {
            System.err.println("exception in connectToDB " + ex.getLocalizedMessage());
            return false;
        } 
        
        return true;
    }
    
    // reads in the papers, streams and sessions from the connected database
    // populates the paperArrayList with all the paper details
    private void readInDB() 
    {
        try {
            // variable initilization
            int totalPapers = 0;
            Paper aPaper = null;
            Stream aStream = null;
            Session aSession = null;
            Float start_time, end_time;
            ResultSet paperResults, sessionResults, streamResults, rowResults;
            String queryPaper, querySession, paper_code, paper_name, room = "", day = "";
      
            // finds how many papers there are in the database, uses this to build objects and loop thorugh each entry
            rowResults = statement.executeQuery("select count(*) from paper");
            while(rowResults.next())
            {
                totalPapers = Integer.parseInt(rowResults.getString(1));
            }

            // for each paper in the batabase
            for(int currentPaper = 1; currentPaper <= totalPapers; currentPaper++)
            {
                queryPaper = "select paper_name, paper_code from paper where paper_id = " + Integer.toString(currentPaper);
                
                // get the main paper details, executes one loop for a the single entry
                paperResults = statement.executeQuery(queryPaper);
                while(paperResults.next())
                {
                    paper_name = paperResults.getString(1);
                    paper_code = paperResults.getString(2);
                    aPaper = new Paper(paper_name, paper_code);
                }
                
                // this part uses two connections to the database, as the first connection is needed by the second connection
                streamResults = statement.executeQuery("select stream_no, yearOffered, semester from stream where paper_id = " + Integer.toString(currentPaper));
                while(streamResults.next())
                {
                   
                    String currentStreamNumber = streamResults.getString(1);
                    String currentYear = streamResults.getString(2);
                    String currentSemester = streamResults.getString(3);
                    
                    System.out.println("Stream details - " + currentPaper + " " + currentStreamNumber + " " + currentYear  + " " + currentSemester);
                    
                    aStream = new Stream(currentStreamNumber, currentYear, currentSemester);
        
                    querySession = "select room, day, start_time, end_time from session where paper_id = " + Integer.toString(currentPaper)
                                        + " and stream_no = '" + currentStreamNumber + "'";

                    // for each session in the current stream
                    sessionResults = statement2.executeQuery(querySession);
                    while(sessionResults.next())
                    {
                        room = sessionResults.getString(1);
                        day = sessionResults.getString(2); 
                        start_time = sessionResults.getFloat(3);
                        end_time = sessionResults.getFloat(4);
                        aSession = new Session(room, day, start_time, end_time);
                        aStream.addSession(aSession);
                    }
                            
                    aPaper.addStream(aStream);
                }
                
                paperArrayList.add(aPaper);
            }
            
            conn.close();
            statement.close();
            
        }catch (Exception ex) {
            
           System.err.println("error reading DB | " + ex);
        }
    }
    
    // this method checks that if a paper has already been added to the new list
    // by looking at the selection, then looking if that selection was already added
    // takes in the index of the selected item and the semester which the user is selecting from
    // this ensures that the list is being populated from the correct semesters papers
    // returns true if a clash is found
    public boolean checkPaperAdded(int selection, int semester)
    {
        boolean clashes = false;
        
        switch (semester) {
            case 1:
                for(Paper aPaper : semesterOnePapers)
                {
                    if(aPaper.getPaperCode().equals(paperArrayList.get(selection).getPaperCode()))
                    {
                        clashes = true;
                        break;
                    }
                }   
            break;
            case 2:
                for(Paper aPaper : semesterTwoPapers)
                {
                    if(aPaper.getPaperCode().equals(paperArrayList.get(selection).getPaperCode()))
                    {
                        clashes = true;
                        break;
                    }
                }   
            break;
            default:
                System.out.println("checkPaperAdded | unkown semester has been parsed in");
            break;
        }
        
        return clashes;
    }
    
   /**
     * Method will take a timetable object and check if there are any clashing sessions it does this by
     * looping through each combination of papers inside the timetable which work by checking paper combos:
     * 
     *  1 - 2 | 1 - 3 | 1 - 4 | 2 - 3 | 2 - 4 | 3 - 4
     * 
     * It then loops through each session of the paper combination, and if it finds a clash, it returns true 
     * as soon as a clash is found, or returns false if the sessions within a paper combo don't clash
     * @param aTimetable - 4 papers within the timetable which get checked to 
     * @return boolean -  true if there is a clash in the timetable
     * 
     *  Current limitation to this is there are only 4 paper combos which are allowed, this needs to be
     *  changed to suit 2, 3, or 5 papers also.
     */
    public static boolean isClash(TimetableGenerator.Timetable aTimetable)
    {
        boolean isClash = false;
        
            Paper paper1, paper2;
            
            for (int paper1Index = 0; paper1Index < 4; paper1Index++)
            {
                paper1 = aTimetable.getNewPaperAtIndex(paper1Index);

                for (int paper2Index = paper1Index + 1; paper2Index < 4; paper2Index++)
                {
                    paper2 = aTimetable.getNewPaperAtIndex(paper2Index);

                    // goes into each session combination to see if 
                    for(Session paper1Session : paper1.getStreamAtIndex(0).getSessions())
                    {
                        for(Session paper2Session : paper2.getStreamAtIndex(0).getSessions())
                        {
                            // checks the day of the sessions to see if they are the same
                            if(paper1Session.getDay().toLowerCase().contains(paper2Session.getDay().toLowerCase()))
                            {
                                // if the days are the same, then check to see if the times overlap at 
                                // any point within that day, if so set isClash to true
                                if(paper1Session.getStartTime() >= paper2Session.getStartTime() &&
                                   paper1Session.getStartTime() <  paper2Session.getEndTime())
                                {
                                    isClash = true;
                                }
                                else if(paper1Session.getEndTime() >  paper2Session.getStartTime() &&
                                        paper1Session.getEndTime() <= paper2Session.getEndTime())

                                {
                                    isClash = true;
                                }
                            }
                        }
                    }
                }
            }
        
        return isClash;
    }

    /**
     * this method creates all possible timetables, then adds it to the list if none of
     * the papers inside of it clash. This calls a method which creates all possible permentations
     * then checks each of these.
     */
    public void createAllTimetables(){
        
        int timetableCounter = 1;
        
        for(int a = 0; a < aTimetable.getPaperAtIndex(0).getStreams().size(); a++)
        {
            for(int b = 0; b < aTimetable.getPaperAtIndex(1).getStreams().size(); b++)
            {
                for(int c = 0; c < aTimetable.getPaperAtIndex(2).getStreams().size(); c++)
                {
                    for(int d = 0; d < aTimetable.getPaperAtIndex(3).getStreams().size(); d++)
                    {
                        // creates all possible timetables with all 4 papers based on each stream
                        TimetableGenerator.Timetable tempTimetable = new TimetableGenerator.Timetable(timetableCounter);
                        tempTimetable.createPerms(a, b, c, d);

                        // if there is no clash, parse in the timetable onject into an arraylist of timetables to be printed
                        boolean clash = isClash(tempTimetable);
                        if(clash == false)
                        {
                            timetableArrayList.add(tempTimetable);
                            timetableCounter++;
                        }
                    }
                }
            }
        }
    }

    // adds the paper that was selected from the list into the 
    // selected papers arrayList, this is then displayed
    public void addPaperAtIndex(int i, int semester)
    {
        if(semester == 1)
        {
            if(!selectedPapers.contains(semesterOnePapers.get(i)))
            {
                selectedPapers.add(this.semesterOnePapers.get(i));
            }
            else
            {
                System.out.println("AddPaperAtIndex | s1 | paper not added, its probably already in the list");
            }
        }
        else if(semester == 2 && !selectedPapers.contains(semesterTwoPapers.get(i)))
        {
            if(!selectedPapers.contains(semesterTwoPapers.get(i)))
            {
                selectedPapers.add(this.semesterTwoPapers.get(i));
            }
            else
            {
                System.out.println("AddPaperAtIndex | s2 | paper not added, its probably already in the list");
            }
        }
    }
    
    // getters and setters
    // gets all the paper titles which are in the paper arrayList which we parse in
    public String[] getPaperTitles(ArrayList<Paper> paperArrayList){
        
        String[] paperTitles = new String[paperArrayList.size()];
        
        for(int i = 0; i < paperArrayList.size(); i++)
        {
            paperTitles[i] = paperArrayList.get(i).getPaperName();
        }
        
        return paperTitles;
    }
    
    // takes in an arrayList of papers and gives back and arrayList of their titles
    public ArrayList<String> getPaperTitlesList(ArrayList<Paper> parsedPaperList){
        
        ArrayList<String> paperTitles = new ArrayList();
        
        for(Paper aPaper : parsedPaperList)
        {
            paperTitles.add(aPaper.getPaperName());
        }
        
        return paperTitles;
    }
    public TimetableGenerator.Timetable getaTimetable() {
        return aTimetable;
    }

    public void setaTimetable(TimetableGenerator.Timetable aTimetable) {
        this.aTimetable = aTimetable;
    }

    public ArrayList<Paper> getPapersArrayList(){
        return this.paperArrayList;
    }
    
    public ArrayList<Paper> getSelectedArrayList(){
        return this.selectedPapers;
    }
    
    public void removePaperAtIndex(int i)
    {
        selectedPapers.remove(i);
    }
      
    public ArrayList<TimetableGenerator.Timetable> getTimetableArrayList() {
        return timetableArrayList;
    }

    public void setTimetableArrayList(ArrayList<TimetableGenerator.Timetable> timetableArrayList) {
        this.timetableArrayList = timetableArrayList;
    }

    public ArrayList<Paper> getSemesterOnePapers() {
        return semesterOnePapers;
    }

    public void setSemesterOnePapers(ArrayList<Paper> semesterOnePapers) {
        this.semesterOnePapers = semesterOnePapers;
    }

    public ArrayList<Paper> getSemesterTwoPapers() {
        return semesterTwoPapers;
    }

    public void setSemesterTwoPapers(ArrayList<Paper> semesterTwoPapers) {
        this.semesterTwoPapers = semesterTwoPapers;
    }
}