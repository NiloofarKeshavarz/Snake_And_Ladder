package assignment2;

import java.io.IOException;

public class LadderAndSnake {
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	private static final int[][] LADDER = {{1, 38}, {4,14}, {9, 31}, {21, 42}, {28, 84}, {36, 44}, {51, 67}, {71, 91}, {80, 100}};
	private static final int[][] SNAKE = {{16, 6}, {48,30}, {64, 60}, {79, 19}, {93, 68}, {95, 24}, {97, 76}, {98, 78}};
	Player[] players; //it is going to be sorted by which player goes first
	
	public LadderAndSnake(Player[] players) {
		this.players = players;
	}
	
	public void play() {
		System.out.println("\nLet's Play!");
		boolean gameEnded = false;
		while(gameEnded == false) {
			System.out.println("Press ENTER to roll the dice...\n"
							 + "===============================");
			try {
				System.in.read();
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < players.length && gameEnded == false; i++) {
				Player player = players[i];
				int roll = flipDice();
				System.out.printf("%s rolled a %d\n", player.getName(), roll);
				if((player.getCurPos() + roll) > 100) {
					int diff = (player.getCurPos() + roll) - 100;
					System.out.printf("\t%s went past 100, cutting the difference of %d\n", player.getName(), diff);
					moveTo(player, 100 - diff);
				} else {
					gameEnded = moveTo(player, player.getCurPos() + roll);
				}
			}
		}
	}
	
	public static int flipDice() {
		return (int)(Math.random()* 6 + 1) ;
	}
	
	//Moves the player and returns true/false if the game has ended or not
	private boolean moveTo(Player player, int newPos) {
		System.out.printf("\t%s moved from %d to %d\n", player.getName(), player.getCurPos(), newPos);
		player.setCurPos(newPos);
		if(newPos == 100) {
			System.out.printf("Congratulations %s! You win!\n", player.getName());
			return true;
		} else if(getLadderEnd(newPos) != -1) {
			System.out.printf("\tYAY! %s can go up a ladder!\n", player.getName());
			return moveTo(player, getLadderEnd(newPos));
		} else if(getSnakeEnd(newPos) != -1) {
			System.out.printf("\tOOPS! %s hit a snake!\n", player.getName());
			return moveTo(player, getSnakeEnd(newPos));
		}
		return false;
	}
	
	private int getLadderEnd(int pos) {
		for(int i = 0; i < LADDER.length; i++) {
			if(pos == LADDER[i][0]) {
				return LADDER[i][1];
			}
		}
		return -1;
	}
	
	private int getSnakeEnd(int pos) {
		for(int i = 0; i < SNAKE.length; i++) {
			if(pos == SNAKE[i][0]) {
				return SNAKE[i][1];
			}
		}
		return -1;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	public void setPlayers(Player[] players) {
		this.players = players;
	}

}