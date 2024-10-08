
import java.util.ArrayList;

public class course{
    private int course_code;
    private String course_title;
    private String proffessor;
    private int credits;
    private ArrayList<Integer>prerequisites;
    private String timing;
    public course(int course_code, String course_title, String proffessor, int credits, ArrayList<Integer>prerequisites,String timing)
    {
        this.course_code=course_code;
        this.course_title=course_title;
        this.proffessor=proffessor;
        this.credits=credits;
        this.timing=timing;
        this.prerequisites=prerequisites;
    }
    public course()
    {
        
    }
    public String toString()
    {
        return "course code : "+course_code+" couse title : "+course_title+" proffessor : "+proffessor+" credits : "+credits+" timing : "+timing;
    }
}