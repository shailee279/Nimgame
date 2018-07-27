/*
* Swapnil Shailee
* Nim game
*/
import java.util.Scanner;
import java.io.*;

class Nimgame {
	int stonecount;
	int upperbound;
	String playerone;
	String playertwo;
	final static int MINARG = 1;
	final static int ARGONE = 3;
	final static int ARGTWO = 4;

	Nimgame() {
		stonecount = 0;
		upperbound = 0;
		playerone = "";
		playertwo = "";

	}

	// creating objects of nimhumanplayer and nimaiplayer class as nimplayer class
	// is abstract
	NimHumanplayer np = new NimHumanplayer();
	NimAIPlayer na = new NimAIPlayer();

	void Game(String command, int num, Scanner input) {

		// exception block
		try {

			if (command.contains("addplayer") == true) {
				String word[] = command.split(" ");

				if (word.length == MINARG) {
					throw new IllegalArgumentException();
				}
				String com = word[1];
				String[] words = com.split(",");
				if (words.length < ARGONE) {
					throw new IllegalArgumentException();
					// insufficient arguments
				}
				String uname = words[0];
				String fname = words[1];
				String gname = words[2];
				int gameswon = 0;
				int gamesplayed = 0;
				np.addplayer(uname, fname, gname, gameswon, gamesplayed);

			} else if (command.contains("displayplayer")) {
				String words[] = command.split(" ");
				String com = words[0];
				if (command.length() == 13) {
					np.displayallplayers();
				} else {

					String player = words[1];
					np.displayplayer(player);
				}
			} else if (command.contains("editplayer")) {

				String word[] = command.split(" ");
				if (word.length == MINARG) {
					throw new IllegalArgumentException();
				}
				String com = word[1];
				String[] words = com.split(",");
				if (words.length < ARGONE) {
					throw new IllegalArgumentException();
				}
				String uname = words[0];
				String nFname = words[1];
				String nGname = words[2];
				np.EditPlayer(uname, nFname, nGname);

			} else if (command.contains("addaiplayer")) {
				String word[] = command.split(" ");

				if (word.length == MINARG) {
					throw new IllegalArgumentException();
				}
				String com = word[1];
				String[] words = com.split(",");
				if (words.length < ARGONE) {
					throw new IllegalArgumentException();

				}
				String uname = words[0];
				String fname = words[1];
				String gname = words[2];
				int gameswon = 0;
				int gamesplayed = 0;
				na.addaiplayer(uname, fname, gname, gameswon, gamesplayed);
			} else if (command.contains("removeplayer")) {

				np.Removeplayer(command, num, input);

			} else if (command.contains("resetstats")) {

				np.ResetStats(command, num, input);

			} else if (command.contains("rankings")) {
				if (command.length() == 8) {
					String com = "desc";
					np.Rankings(com);
				} else {
					String com;
					String fcom = command.substring(9, num);
					if (fcom.contains(",")) {
						String[] word = fcom.split(",");
						com = word[0];
					} else {
						com = fcom;
					}
					np.Rankings(com);
				}
			} else if (command.contains("startgame")) {
				String[] commandsplit = command.split(" ");
				if (commandsplit.length == MINARG) {
					throw new IllegalArgumentException();
				}
				String arguments = commandsplit[1];
				String[] words = arguments.split(",");
				if (words.length < ARGTWO) {
					throw new IllegalArgumentException();
				}
				int initstones = Integer.parseInt(words[0]);
				int upperbound = Integer.parseInt(words[1]);
				String playerone = words[2];
				String playertwo = words[3];

				np.Startgame(initstones, upperbound, playerone, playertwo, input);

				// start game by passing initial number of stones,upperbound ,player one
				// and player two usernames and total no.of players as arguments

			} else if (num == 0) {
				System.out.print("");
			}

			else {
				// exception thrown for incorrect command
				throw new IncorrectCommandException(command);
			}

		} catch (IllegalArgumentException e) {

			System.out.println("Incorrect number of arguments supplied to command.");
		} catch (IncorrectCommandException ec) {
			System.out.println("'" + ec.getmessage() + "' is not a valid command.");
		}
	}

	// function for user input
	String prompt(Scanner input) {
		System.out.println();
		System.out.print("$");

		String command = input.nextLine();
		return command;
	}

	// function to save player data to file
	void filesave() {
		np.savefile();
	}

	// function to load all player data
	void loadfile() {
		np.fileload();

	}

}