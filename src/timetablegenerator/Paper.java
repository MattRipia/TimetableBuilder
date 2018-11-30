package timetablegenerator;

import java.util.ArrayList;

/**
 * The Paper class contains details such as paper name,
 * paper code, and an array of streams
 * 
 * @author 1385931, 16959932
 */

public class Paper {
    
    private String paperName;
    private String paperCode;
    private ArrayList<Stream> stream;
    
    public Paper(String paperName, String paperCode, ArrayList<Stream> stream)
    {
        this.setPaperName(paperName);
        this.setPaperCode(paperCode);
        this.setStream(stream);
    }
    
    public Paper(Stream aStream, String name, String code){
        this.paperName = name;
        this.paperCode = code;
        this.stream = new ArrayList<>();
        stream.add(aStream);
    }
    public Paper() {
        
        this.stream = new ArrayList<>();
        Stream newStream = new Stream();
        this.stream.add(newStream);
        this.paperName = "unknown";
        this.paperCode = "unknown";
    }
    
    public Paper(String name, String code)
    {
        this.paperName = name;
        this.paperCode = code;
        this.stream = new ArrayList();
    }

    public Paper(Paper p) {
        this.paperName = p.paperName;
        this.paperCode = p.paperCode;
        this.stream = p.stream;
    }
    
    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public void setStream(ArrayList<Stream> stream)
    {
        this.stream = stream;
    }
    
    public ArrayList<Stream> getStreams() {
        return stream;
    }
    
    public void removeAllStreams(){
        this.stream.clear();
    }
    
    public Stream getStreamAtIndex(int i){
        return this.stream.get(i);
    }

    public void addStream(Stream aStream) {
        stream.add(aStream);
    }
    
     @Override
    public String toString(){
        
        String temp = "";
        
        for(int i = 0; i < getStreams().size(); i++)
        {
            temp += getStreams().get(i).toString() + "\n";
        }

        return "Paper Name: " +getPaperName() + "\n" + "Paper Code: " + getPaperCode() + "\n" + temp;
    }
}
