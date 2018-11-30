package timetablegenerator;
import javax.swing.JFrame;

// @author 1385931, 16959932

public class TimetableGenerator extends JFrame{

    private PaperDatabase model;
    private TimetableGeneratorPanel view;
   
    public static void main(String[] args){
        
        JFrame frame = new TimetableGenerator();
        frame.setVisible(true); 
    } 
    
    // Creates the timetable generator frame
    public TimetableGenerator()
    {
        // creates a new frame with a new model / view
        super("Timetable Generator");
        this.model = new PaperDatabase();
        this.view = new TimetableGeneratorPanel(this.model);
        
        System.out.println("" + this.model.getPapersArrayList().size() + this.model.getPapersArrayList().get(4).getStreams());
        System.out.println("s1 List contains - " + this.model.getSemesterOnePapers().size());
        System.out.println("s2 List contains - " + this.model.getSemesterTwoPapers().size());

        // sets the parameters of the frame
        this.getContentPane().add(this.view);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1100, 530); // manually computed sizes
        this.setResizable(false);      
        this.setLocationRelativeTo(null);
    } 
}