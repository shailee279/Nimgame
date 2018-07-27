
/*
* Swapnil Shailee
* Nim Game
*/
import java.util.Scanner;

//class for incorrect move that inherits exception class
public class IncorrectMoveException extends Exception {
	private int upper;

	public IncorrectMoveException(int upperbound) {
		upper = upperbound;

	}

	public void getmessage() {
		System.out.println("Invalid move.You must remove between 1 and " + upper);

	}

}