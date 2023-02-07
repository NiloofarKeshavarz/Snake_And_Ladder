package assignment2;
import java.io.IOException;
import java.util.InputMismatchException;
	/* Assignment (2)
	 * Question: (Snakes and Ladders game)
	 * Written by: (Aftab Safdar (id 0514314) and Niloofar (id 2228051) )
	 * Description:	A Snakes and Ladders game which gets the number of players from the user
	 * 				A roll decides the order of the players. If there's a tie, it breaks the tie by rerolling for the tied players
	 * 				Each player takes turns rolling the dice and moving through the board
	 * 				If the player hits a ladder or a snake, they go up or down accordingly.
	 * 				The game ends when a player reaches 100.
	 */
import java.util.Scanner;

public class PlayLadderAndSnake {
	private final static int TOTAL_INPUT_ATTEMPTS = 4;
	
	public static void main(String[] args) {
		System.out.println("============================================================");
		System.out.println("=  Welcome to Snake and ladder game by Aftab and Niloofar  =");
		System.out.println("============================================================\n");
		Scanner input = new Scanner(System.in);
		
		do {
			//Get num of players from user
			int numOfPlayers = askNumOfPlayers(input);
			Player[] players = new Player[numOfPlayers];
			
			//get players' names
			for(int i = 0; i< players.length; i++) {
				String name = askPlayerNames(i, input);
				players[i] = new Player(name);
			}
			System.out.printf("Welcome %s\n", Player.getNames(players));
			
			//decide player order and re-assign sorted array
			System.out.println("Let's decide who goes first! Press enter to roll the dice.");
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			players = Player.decidePlayersOrder(players);
			System.out.printf("\nThe order of the players will be: %s\n", Player.getNames(players));
			
			LadderAndSnake game = new LadderAndSnake(players);
			game.play();
			
			System.out.println("Type \"Y\" or \"Yes\" if you would liek to continue?!");
			String answer = input.next();
			if(!answer.trim().toLowerCase().equals("yes") && !answer.trim().toLowerCase().equals("y")) {
				exit("Exiting..");
			}
		} while(true);
	}

	private static String askPlayerNames(int i, Scanner input) {
		int attemptCounter = 0;
		do {
			attemptCounter++;
			System.out.printf("Enter the name of player %d.\n", i+1);
			String name = input.next();
			if(name.isBlank())
				System.out.println("You did not enter anything!");
			else
				return name;
		} while(attemptCounter < TOTAL_INPUT_ATTEMPTS);
		exit("You have reached the limit of attempts! Program is terminating");
		return null;
	}

	// get the total number of players
	public static int askNumOfPlayers(Scanner input) {
		int attemptCounter = 0;
		do {
			attemptCounter++;
			try {
				System.out.printf("How many players would like to play (%d-%d)? ",LadderAndSnake.MIN_PLAYERS, LadderAndSnake.MAX_PLAYERS);
				int numOfPlayers = input.nextInt();
				if(numOfPlayers < LadderAndSnake.MIN_PLAYERS || numOfPlayers > LadderAndSnake.MAX_PLAYERS) {
					System.out.printf("Invalid entry! You can have %d to %d players.\n", LadderAndSnake.MIN_PLAYERS, LadderAndSnake.MAX_PLAYERS);
				} else {
					return numOfPlayers;
				}
			} catch(InputMismatchException e) {
				System.out.printf("Invalid entry! Only whole numbers between %d and %d are allowed.\nYou have %d tries left...\n\n", LadderAndSnake.MIN_PLAYERS, LadderAndSnake.MAX_PLAYERS, TOTAL_INPUT_ATTEMPTS - attemptCounter);
				input.next();
			}
		} while(attemptCounter < TOTAL_INPUT_ATTEMPTS);
		exit("You have reached the limit of attempts! Program is terminating");
		return -1;
	}

	public static void exit(String msg) {
		if(msg != null) {
			System.out.println(msg);
		}
		System.out.println("============================");
		System.out.println("=  Thank you for playing!  =");
		System.out.println("============================\n");
		System.exit(0);
	}
}