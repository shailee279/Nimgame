
/*
* Name:Swapnil Shailee
* Student ID:952247
* Username:sshailee
* Project C
*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.EOFException;
import java.io.File;

abstract class Nimplayer implements Serializable {

	// private instance variables
	private String Username;
	private String Familyname;
	private String Givenname;
	private int gamesplayed;
	private int gameswon;
	// static arraylist that will contain player objects
	static ArrayList<Nimplayer> players = new ArrayList<Nimplayer>();
	// static variables needed
	public final static int MIN = 0;
	public final static int INT_MAX = 100;
	public final static int SETONE = 1;
	public final static int SETTWO = 2;
	private static final long serialVersionUID = 1L;

	// constructor
	Nimplayer() {
		Username = "";
		Familyname = "";
		Givenname = "";
		gamesplayed = 0;
		gameswon = 0;

	}

	// constructor
	Nimplayer(String Username, String Familyname, String Givenname, int gameswon, int gamesplayed) {
		this.Username = Username;
		this.Familyname = Familyname;
		this.Givenname = Givenname;
		this.gamesplayed = gamesplayed;
		this.gameswon = gameswon;

	}

	// getters and setters for username,familyname,givenname,gamesplayed,gameswon
	public void setusername(String username) {
		Username = username;
	}

	public void setfamilyname(String familyname) {
		Familyname = familyname;
	}

	public void setgivenname(String gname) {
		Givenname = gname;
	}

	public void setgamesplayed(int gplayed) {
		gamesplayed = gplayed;
	}

	public void setgameswon(int gameswon) {
		this.gameswon = gameswon;
	}

	public String getusername() {
		return Username;
	}

	public String getfname() {
		return Familyname;
	}

	public String getgname() {
		return Givenname;
	}

	public int getgamesplayed() {
		return gamesplayed;
	}

	public int getgameswon() {
		return gameswon;
	}

	// function to display all players
	void displayallplayers() {
		Collections.sort(players, new comp());// sort players by comparator
		for (int i = 0; i < players.size(); i++) {
			System.out
					.println(players.get(i).Username + "," + players.get(i).Givenname + "," + players.get(i).Familyname
							+ "," + players.get(i).gamesplayed + " games," + players.get(i).gameswon + " wins");
		}
	}

	// function to display player of specific name
	void displayplayer(String uname) {

		boolean found = false;
		for (int i = 0; i < players.size(); i++) {
			if (uname.equals(players.get(i).getusername())) {
				int dindex = i;
				System.out.println(players.get(dindex).Username + "," + players.get(dindex).Givenname + ","
						+ players.get(dindex).Familyname + "," + players.get(dindex).gamesplayed + " games,"
						+ players.get(dindex).gameswon + " wins");
				found = true;
				break;
			}

		}
		if (found == false) {
			System.out.println("The player does not exist");
		}
	}

	// function to edit player details
	void EditPlayer(String uname, String newfname, String newgname) {

		int i = 0;
		boolean found = false;
		while (i < players.size()) {
			if (uname.equals(players.get(i).Username)) {
				int uindex = i;
				players.get(i).Familyname = newfname;
				players.get(i).Givenname = newgname;
				found = true;
				break;
			}
			i++;
		}
		if (found == false) {
			System.out.println("The player does not exist");
		}

	}

	// function to remove player
	void Removeplayer(String command, int num, Scanner input) {
		int removeindex = 0, i = 0;
		boolean found = false;

		if (command.length() == 12) {
			System.out.println("Are you sure you want to remove all the players? (y/n)");
			String in = input.next();

			if (in.equals("y")) {
				players.clear(); // removes all from players arraylist

			}

		} else {
			String words[] = command.split(" ");
			String uname;
			String com = words[1];

			if (com.contains(",")) {
				String word[] = com.split(",");

				uname = word[0];
			} else {
				uname = com;
			}
			for (i = 0; i < players.size(); i++) {
				if (uname.equals(players.get(i).getusername())) {
					removeindex = i;
					players.remove(removeindex);
					found = true;
					break;
				}

			}
			if (found == false) {
				System.out.println("The player does not exist");
			}

		}
	}

	// function to resetstats of players
	void ResetStats(String command, int num, Scanner input) {
		if (command.length() == 10) {
			System.out.println("Are you sure you want to reset all player statistics? (y/n) ");
			String in = input.next();
			if (in.equals("y")) {
				for (int i = 0; i < players.size(); i++) {
					players.get(i).gamesplayed = 0;
					players.get(i).gameswon = 0;
				}
			}
		} else {
			String words[] = command.split(" ");
			String com;
			String Fcom = words[1];
			if (Fcom.contains(",")) {
				String word[] = Fcom.split(",");
				com = word[0];

			} else {
				com = Fcom;
			}
			int i = 0, resetindex;
			boolean found = false;

			while (i < players.size()) {
				if (players.get(i).getusername().equals(com)) {
					resetindex = i;
					players.get(resetindex).gamesplayed = 0;
					players.get(resetindex).gameswon = 0;
					found = true;
					break;
				}
				i++;

			}

			if (found == false) {

				System.out.println("The player does not exists");

			}
		}
	}

	// function to start the game
	void Startgame(int initstones, int upperbound, String playerone, String playertwo, Scanner input) {
		int uone = 0, utwo = 0;
		int i = 0, j = 0;
		boolean found = false;
		int setone = 0;
		int settwo = 0;
		// check player one
		while (i < players.size()) {
			if (players.get(i).getusername().equals(playerone)) {
				uone = i;
				found = true;
				setone = SETONE;
				break;
			}

			i++;

		}
		// check player two
		while (j < players.size()) {
			if (players.get(j).getusername().equals(playertwo)) {
				utwo = j;
				found = true;
				settwo = SETTWO;
				break;
			}

			j++;
		}
		// if either player is not present
		if (setone != SETONE || settwo != SETTWO)

		{
			System.out.println("One of the players does not exist");
		}

		// all players present
		if (setone == SETONE && settwo == SETTWO) {
			System.out.println("Initial stone count: " + initstones);
			System.out.println("Maximum stone removal: " + upperbound);
			System.out.println("Player 1: " + players.get(uone).getgname() + " " + players.get(uone).getfname());
			System.out.println("Player 2: " + players.get(utwo).getgname() + " " + players.get(utwo).getfname());

			System.out.println();
			boolean aione;
			boolean aitwo;
			// create human and AI class objects
			NimHumanplayer h = new NimHumanplayer();
			NimAIPlayer a = new NimAIPlayer();
			// check if any player is AI or not
			aione = a.checkAIplayer(playerone);
			aitwo = a.checkAIplayer(playertwo);
			// if any player is AI,call AI play function,else Human play
			if (aione == true || aitwo == true) {
				a.play(initstones, upperbound, playerone, playertwo, input);
			} else {
				h.play(initstones, upperbound, playerone, playertwo, input);
			}
		}
	}

	abstract void play(int initialstones, int upperbound, String userone, String usertwo, Scanner input);
	// main stone removal abstract game function

	// The games played and games won is updated after a player wins; by
	// this function
	public static void updateWplayerstatistics(String playername) {
		int winindex;

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).Username.equals(playername)) {
				winindex = i;
				players.get(winindex).gamesplayed = players.get(winindex).gamesplayed + 1;
				players.get(winindex).gameswon = players.get(winindex).gameswon + 1;
			}

		}

	}

	// games played updated after player looses a game through this function
	public static void updateLplayerstatistics(String playername) {
		int looseindex;

		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).Username.equals(playername)) {
				looseindex = i;
				players.get(looseindex).gamesplayed = players.get(looseindex).gamesplayed + 1;

			}

		}
	}

	// Function to print pattern
	public static void printpattern(int initialstones) {
		if (initialstones > 0) {
			System.out.print(initialstones + " stones left:");
			for (int i = 1; i <= initialstones; i++) {
				System.out.print(" * ");
			}
			System.out.println();
		}
	}

	// function to rank all players using comparator
	void Rankings(String order) {
		int rank;
		Collections.sort(players, new Check());
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).gamesplayed != 0) {
				float val = (float) players.get(i).gameswon / (float) players.get(i).gamesplayed;
				int n = (int) (val * INT_MAX);
				rank = n;
			} else {
				rank = MIN;

			}
			String winrank = String.format("%d%%", rank);
			String winratio = String.format("%-5s", winrank);
			System.out.println(winratio + "| 0" + players.get(i).gamesplayed + " games | " + players.get(i).Givenname
					+ " " + players.get(i).Familyname);

		}

	}

	// function to obtain userindex of player when username is used as an argument
	int getUserIndex(String player) {
		int uindex = 0;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getusername().equals(player)) {
				uindex = i;
			}
		}
		return uindex;
	}

	// function to declare result
	public void DeclareResult(String userone) {
		System.out.println("Game Over");
		int uione = getUserIndex(userone);
		System.out.println(players.get(uione).getgname() + " " + players.get(uione).getfname() + " wins!");

	}

	// function to check if integer value is input
	public int checkinput(String player, int upper, Scanner input, int initialstones) {
		boolean isnumber;
		int remnum = 0;
		if (initialstones < upper) {
			upper = initialstones;
		}
		do {
			System.out.println(player + "'s turn - remove how many?");
			if (input.hasNextInt()) {
				remnum = input.nextInt();
				isnumber = true;
			} else {
				System.out.println("Invalid move.You must remove between 1 and " + upper);
				System.out.println();
				printpattern(initialstones);
				isnumber = false;
				input.next();
			}

		} while (!(isnumber));

		return remnum;
	}

	// check class that implements comparator for ranking players
	class Check implements Comparator<Nimplayer> {

		@Override
		public int compare(Nimplayer n1, Nimplayer n2) {

			float val1 = (float) n1.getgameswon() / (float) n1.getgamesplayed();
			int n = (int) (val1 * 100);
			float val2 = (float) n2.getgameswon() / (float) n2.getgamesplayed();
			int m = (int) (val2 * 100);

			if (n == m) {
				return n1.getusername().compareTo(n2.getusername());// alphabetically
			} else {
				return m - n;// decreasing
			}
		}
	}

	// comp class that implements comparator for displaying sorted players
	class comp implements Comparator<Nimplayer> {
		public int compare(Nimplayer n1, Nimplayer n2) {
			return n1.getusername().compareTo(n2.getusername());
		}

	}
}



