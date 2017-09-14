package practice;
import java.util.*;


public class multiclass_practice {
	public static void main(String[] args){
		MixStrings anObject = new MixStrings("World");
		System.out.println(anObject);
		Fen fe = new Fen("whatever");
        fe.fen();
	}

}

class MixStrings {
	private String message;
	MixStrings(String insert){
		message = "O! Hello "+insert+"! Look at this!";
	}
	public String toString(){
		return message;
	}
}



class Fen {
    Fen(String lol) {
        System.out.println("fen "+lol+" construuctor");

    }
    void fen() {
        System.out.println("Fen method");
    }
}