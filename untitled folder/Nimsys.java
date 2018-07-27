
/*
* Name:Swapnil Shailee
* Student ID:952247
* Username:sshailee
* Project C
*/
import java.util.ArrayList;
import java.util.Scanner;

class Nimsys {
	public static String command;
	public static Scanner input; // Scanner for input

	public static void main(String[] args)

	{
		Nimgame ng = new Nimgame(); // object of nimgame class
		input = new Scanner(System.in);
		ng.loadfile(); // loads all players from players.dat file
		System.out.println("Welcome to Nim");
		System.out.println();
		System.out.print("$");
		command = input.nextLine();

		int num = command.length();
		// loop runs for accepting commands and performing functions
		while (!command.equals("exit"))

		{ // calling game play function of nimgame class
			ng.Game(command, num, input);
			command = ng.prompt(input);
			num = command.length();

		}
		if (command.equals("exit")) {
			ng.filesave(); // save all player data to file players.dat file
			System.out.println(); // exit condition
			System.exit(0);
		}

	}
}

