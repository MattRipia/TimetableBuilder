package timetablegenerator;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Each timetable object contains an array with
 * the four user selected papers and another array of papers
 * used to process the different stream combinations
 * 
 * @author 1385931, 16959932
 */

public class Timetable {
    
    private static ArrayList<Paper> originalPapers;
    private ArrayList<Paper> newPapers;
    private int timetableNumber = 0;
    
    public Timetable(){
        this.originalPapers = new ArrayList();
        this.newPapers = new ArrayList();
    }
    
    public Timetable(int i){
        this.timetableNumber = i;
        this.newPapers = new ArrayList();
    }
    
    /**
     * creates a timetable with 4 papers depending on what stream is parsed into the method
     * @param p1Stream
     * @param p2Stream
     * @param p3Stream
     * @param p4Stream 
     */
    
    public void createPerms(int p1Stream, int p2Stream, int p3Stream, int p4Stream){
        
        Stream tempStream;
        Paper tempPaper;
        int[] paperStreams = {p1Stream, p2Stream, p3Stream, p4Stream};
        
        for (int i = 0; i < 4; i++)
        {
            tempStream = this.originalPapers.get(i).getStreamAtIndex(paperStreams[i]);
            ArrayList<Stream> firstPaperStream = new ArrayList<>();
            firstPaperStream.add(tempStream);
            tempPaper = new Paper(this.originalPapers.get(i).getPaperName(), this.originalPapers.get(i).getPaperCode(), firstPaperStream);
            newPapers.add(tempPaper);
        }
    }
    
    // returns a paper at index i
    public Paper getPaperAtIndex(int i){
        return this.originalPapers.get(i);
    }

    public ArrayList<Paper> getOriginalPapers() {
        return originalPapers;
    }

    public ArrayList<Paper> getNewPapers() {
        return newPapers;
    }

    public int getTimetableNumber() {
        return timetableNumber;
    }

    // adds a paper to the list
    public void removePaper(int i){
        this.originalPapers.remove(i);
    }
    
    public Paper getNewPaperAtIndex(int i){
        return this.newPapers.get(i);
    }
    
    /**
     * @return a String representation of a timetable object including the streams used, use to print to console and to the text file
     */
//    @Override
//    public String toString(){
//        
//        DecimalFormat timeFormat = new DecimalFormat("00.00");
//        String tempString = "";
//        
//        tempString += "\r\n---------------------------------------\r\n";
//        tempString += "This is timetable number: " +String.valueOf(timetableNumber) + "\r\n";
//        
//        for (int i = 0 ; i < this.newPapers.size(); i ++)
//        {
//          tempString +=  "\r\n";
//          tempString += this.newPapers.get(i).getPaperCode() + "   - Stream: ";
//          tempString += this.newPapers.get(i).getStream().get(0).getStreamNumber();
//        }
//        
//        //These strings are used in the following loop
//        String[] dayCodes = {"mon", "tue", "wed", "thu", "fri"};
//        String[] days = {"Monday    - ", "Tuesday   - ", "Wednesday - ", "Thursday  - ", "Friday    - "};
//        
//        tempString += "\r\n";
//        
//        for(int k = 0; k < 5; k++)
//        {
//             tempString += "\r\n" + days[k];
//        
//            for(int i = 0 ; i < newPapers.size(); i++)
//            {
//                for(int j = 0; j < newPapers.get(i).getStreamAtIndex(0).getSessionLength(); j++)
//                {
//                    if(newPapers.get(i).getStreamAtIndex(0).getSessionAtIndex(j).getTime().getDay().toLowerCase().contains(dayCodes[k]))
//                    {
//                        tempString += newPapers.get(i).getPaperCode() + " ";
//                        tempString += newPapers.get(i).getStreamAtIndex(0).getSessionAtIndex(j).getRoom() + " ";
//                        tempString += timeFormat.format(newPapers.get(i).getStreamAtIndex(0).getSessionAtIndex(j).getTime().getStartTime())+ " - ";
//                        tempString += timeFormat.format(newPapers.get(i).getStreamAtIndex(0).getSessionAtIndex(j).getTime().getEndTime())+ "\r\n            ";  
//                    }
//                }
//            }
//        }
//        
//        return tempString;
//    }
}