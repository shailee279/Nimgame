/*
* Swapnil Shailee
* Nim Game
*/
import java.io.*;

//class for incorrect command that inherits exception class
public class IncorrectCommandException extends Exception {
	private String command;

	public IncorrectCommandException(String com) {
		String[] word = com.split(" ");
		command = word[0];

	}

	public String getmessage() {
		return command;
	}
}
