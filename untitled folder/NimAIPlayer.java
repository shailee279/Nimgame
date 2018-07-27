/*
* Swapnil Shailee
* Nim game
*/
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class NimAIPlayer extends Nimplayer{

	static ArrayList<NimAIPlayer> AIplayers = new ArrayList<NimAIPlayer>();

	public NimAIPlayer() {

	}

	// function to add AI player
	void addaiplayer(String Username, String Familyname, String Givenname, int gameswon, int gamesplayed) {
		NimAIPlayer p = new NimAIPlayer();
		p.setusername(Username);
		p.setfamilyname(Familyname);
		p.setgivenname(Givenname);
		p.setgameswon(gameswon);
		p.setgamesplayed(gamesplayed);
		AIplayers.add(p);
		players.add(p);

	}

	// function to check for AI player username when starting a game
	boolean checkAIplayer(String player) {
		boolean found = false;
		for (int i = 0; i < AIplayers.size(); i++) {
			if (AIplayers.get(i).getusername().equals(player)) {
				found = true;
			}
		}

		return found;
	}

	

	// AI player move
	int AImove(String player, int initialstones, int remstones, int upperbound) {
		System.out.println(player + "'s turn - remove how many?");
		System.out.println();
		if (initialstones < upperbound) {
			initialstones = upperbound;
			remstones = removeStone(initialstones, upperbound);
			initialstones = initialstones - remstones;
		} else {
			remstones = removeStone(initialstones, upperbound);
			initialstones = initialstones - remstones;
		}
		return initialstones;
	}

	// Remove stone function for AI players
	int removeStone(int initialstones, int upperbound) {
		int remstones = 0;
		if (initialstones == upperbound && upperbound != 1) {
			remstones = upperbound - 1;
		} else if (initialstones > upperbound) {
			remstones = upperbound;
		} else {
			remstones = 1;
		}
		return remstones;
	}

	// stone removal game function for AI and human
	void play(int initialstones, int upperbound, String userone, String usertwo, Scanner input) {

		int uone = getUserIndex(userone);
		int utwo = getUserIndex(usertwo);
		String playerone = players.get(uone).getgname();
		String playertwo = players.get(utwo).getgname();
		int set = MIN;
		// loops for checking if player one is AI or player two is AI
		for (int i = 0; i < AIplayers.size(); i++) {
			if (AIplayers.get(i).getusername().equals(userone)) {
				set = SETONE;
			}
		}
		for (int i = 0; i < AIplayers.size(); i++) {
			if (AIplayers.get(i).getusername().equals(usertwo)) {
				set = SETONE;
			}
		}

		if (set == SETTWO) // userone is AI player
		{
			int remnum = 0; // remaining number of stones
			boolean turn = true;

			printpattern(initialstones);
			while (initialstones > 0) {

				if (turn == true) {

					initialstones = AImove(playerone, initialstones, remnum, upperbound);
					printpattern(initialstones);
					System.out.println();
					turn = false;

				} else {
					remnum = checkinput(playertwo, upperbound, input, initialstones);

					if (remnum > upperbound || remnum == MIN) { // exception handling for incorrect move.
						try {
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

		} else // usertwo is AI player
		{
			int remnum = 0; // remaining number of stones
			printpattern(initialstones);
			boolean turn = true;
			while (initialstones > 0) {
				if (turn == true) {
					remnum = checkinput(playerone, upperbound, input, initialstones);
					if (remnum > upperbound || remnum == MIN) { // exception handling for incorrect move
						try {
							throw new IncorrectMoveException(upperbound);

						} catch (IncorrectMoveException e) {
							e.getmessage();
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
				}

				else {

					initialstones = AImove(playertwo, initialstones, remnum, upperbound);
					printpattern(initialstones);

					turn = true;
				}

			}
			if (initialstones <= 0) {
				if (turn == true) { // declare result and update stats
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

	}

}
