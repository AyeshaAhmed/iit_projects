//Ivan Temesvari
//CSC 156-050
//Spring '08
//This class describes a person with a grade, name, and array of lab scores.

public class People{
    private String name;
    private char grade;
    private int[] labs = new int[10];
    public static final int NUMBERofLABS = 10;

    public People(){
	name = " ";
	grade = 'N';
	for(int i = 0; i < NUMBERofLABS; i++)
	    labs[i] = 0;
    }

    public People(String the_name, char the_grade, int[] the_labs){
	name = the_name;
	grade = the_grade;
	for(int i = 0; i < NUMBERofLABS; i++)
	    labs[i] = the_labs[i];
    }



    public String toString(){
	String details = "";
	for(int i = 0; i < NUMBERofLABS; i++)
	    details += " " + labs[i];
	return "\n" + name + "'s grade is: " + grade + " with the following lab scores: " + details;
    }

}
