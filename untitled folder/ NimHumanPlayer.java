/*
* Swapnil Shailee
* Nim game
*/
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

//inherits abstract nimplayer class
class NimHumanplayer extends Nimplayer {

	private String Username;
	private String Familyname;
	private String Givenname;
	private int gamesplayed;
	private int gameswon;

	// constructor
	NimHumanplayer() {
		Username = "";
		Familyname = "";
		Givenname = "";
		gamesplayed = 0;
		gameswon = 0;

	}

	// constructor
	NimHumanplayer(String Username, String Familyname, String Givenname, int gameswon, int gamesplayed) {
		this.Username = Username;
		this.Familyname = Familyname;
		this.Givenname = Givenname;
		this.gamesplayed = gamesplayed;
		this.gameswon = gameswon;

	}

	// function to add players
	void addplayer(String Username, String Familyname, String Givenname, int gameswon, int gamesplayed) {
		NimHumanplayer p = new NimHumanplayer();
		int i = 0;
		boolean found = false;
		p.setusername(Username);
		p.setfamilyname(Familyname);
		p.setgivenname(Givenname);
		p.setgameswon(gameswon);
		p.setgamesplayed(gamesplayed);
		while (i < players.size()) {
			if (players.get(i).getusername().equals(Username)) {
				System.out.println("The player already exists");
				found = true;
				break;
			}

			i++;
		}
		if (found == false && players.size() != INT_MAX)

		{
			players.add(p);
		}

	}

	// stone removal game function for humans
	void play(int initialstones, int upperbound, String userone, String usertwo, Scanner input) {
		int uone = getUserIndex(userone);
		int utwo = getUserIndex(usertwo);
		// playerone playertwo are given names of players
		String playerone = players.get(uone).getgname();
		String playertwo = players.get(utwo).getgname();
		int remnum = 0; // remaining number of stones
		printpattern(initialstones);
		boolean turn = true;
		while (initialstones > 0) {

			if (turn == true) {
				remnum = checkinput(playerone, upperbound, input, initialstones);

				if (remnum > upperbound || remnum == MIN) {
					try { // exception handling
						if (initialstones < upperbound) {
							upperbound = initialstones;
						}
						throw new IncorrectMoveException(upperbound);

					} catch (IncorrectMoveException e) {

						e.getmessage();// error message
						System.out.println();
						printpattern(initialstones);
						remnum = checkinput(playerone, upperbound, input, initialstones);
						initialstones = initialstones - remnum;
						printpattern(initialstones);
						turn = false;
					}

				} else {

					initialstones = initialstones - remnum;
					printpattern(initialstones);
					turn = false;
				}

			} else {

				remnum = checkinput(playertwo, upperbound, input, initialstones);
				if (remnum > upperbound || remnum == MIN) {
					try { // exception handling
						if (initialstones < upperbound) {
							upperbound = initialstones;
						}
						throw new IncorrectMoveException(upperbound);

					} catch (IncorrectMoveException e) {
						e.getmessage();
						System.out.println();
						printpattern(initialstones);
						remnum = checkinput(playertwo, upperbound, input, initialstones);
						initialstones = initialstones - remnum;
						printpattern(initialstones);

						turn = true;
					}

				} else {
					initialstones = initialstones - remnum;
					printpattern(initialstones);
					turn = true;
				}
			}

		}
		if (initialstones <= 0) {
			if (turn == true) {
				DeclareResult(userone);
				updateWplayerstatistics(userone);
				updateLplayerstatistics(usertwo);
			} else {
				DeclareResult(usertwo);
				updateWplayerstatistics(usertwo);
				updateLplayerstatistics(userone);
			}

		}
	}

	// function to call write to file
	void savefile() {
		String filepath = "./players.dat.txt";
		writeToFile(players, filepath);

	}

	// function that writes player objects to players.dat text file when exit is
	// called
	public void writeToFile(ArrayList<Nimplayer> players, String file) {
		ObjectOutputStream outStream = null;
		try {
			outStream = new ObjectOutputStream(new FileOutputStream(file));
			for (Nimplayer p : players) {
				outStream.writeObject(p);
			}

		} catch (IOException ioException) {
			System.err.println("Error opening file.");
		} catch (NoSuchElementException noSuchElementException) {
			System.err.println("Invalid input.");
		} finally {
			try {
				if (outStream != null)
					outStream.close();
			} catch (IOException ioException) {
				System.err.println("Error closing file.");
			}
		}
	}

	// function to read objects from players.dat text file
	public ArrayList<Nimplayer> readFromFile(String file) {
		ArrayList<Nimplayer> list = new ArrayList<>();
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(file));
			while (true) {
				Nimplayer p = (Nimplayer) inputStream.readObject();
				list.add(p);
			}
		} catch (EOFException eofException) {
			return list;
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println("Object creation failed.");
		} catch (IOException ioException) {
			System.err.println("Error opening file.");
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException ioException) {
				System.err.println("Error closing file.");
			}
		}
		return list;
	}

	// loading player objects to players arraylist
	void fileload() {
		ArrayList<Nimplayer> templist = new ArrayList<>();
		String filePath = "./players.dat.txt";
		if (new File("./players.dat.txt").exists()) {

			templist = readFromFile(filePath);
			for (Nimplayer p : templist) {
				players.add(p);
			}

		}

	}

}
