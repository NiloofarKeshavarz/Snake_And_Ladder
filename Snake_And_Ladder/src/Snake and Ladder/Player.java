package assignment2;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
	private int curPos;
	private String name;

	public Player(String name) {
		this.name = name;
		curPos = 0;
	}

	/* Takes an unsorted Player array
	 * Rolls dice for each player
	 * Sorts the array
	 * Returns the sorted array
	 */
	public static Player[] decidePlayersOrder(Player[] players) {
		ArrayList<Player> sortedPlayers = new ArrayList<>();
		int[] rolls = getNewRolls(players.length); //The indices for each roll is for the player at that index in players[]
		System.out.printf("The rolls are: %s\n", getRolls(rolls));
		sortedPlayers = sort(rolls, playerArrayToList(players));
		return playerListToArray(sortedPlayers);
	}

	/* Takes the rolls and players
	 * Loops from roll 6 to 1
	 * if curRoll is found
	 * '->Looks if there's a tie
	 * 			'-> Yes ->	performTieBreaker()
	 * 			'-> No ->	adds the player to the sorted list
	 * if roll # is not found -> try again with # - 1
	 */
	private static ArrayList<Player> sort(int[] rolls, ArrayList<Player> players) {
		ArrayList<Player> sortedPlayers = new ArrayList<>();
		for (int query = 6; query > 0; query--) {
			for (int i = 0; i < rolls.length; i++) {
				if (rolls[i] == query) {
					ArrayList<Player> tiedPlayers = findTies(i, query, rolls, players); // returns null if no ties and list of tiedPlayers if there is a tie
					if (tiedPlayers == null) {
						sortedPlayers.add(players.get(i));
					} else {
						//perform tie breaker
						int[] rollsTies = getNewRolls(tiedPlayers.size());
						System.out.printf("%s tied! Rerolling.. The new rolls are: %s\n",getNames(tiedPlayers), getRolls(rollsTies));
						sortedPlayers.addAll(sort(rollsTies, tiedPlayers)); //sort tied players and add to list
					}
					break;
				}
			}
		}
		return sortedPlayers;	
	}

	/* 
	 * Returns a list of players who are tied or null if no ties
	 */
	private static ArrayList<Player> findTies(int startIndex, int roll, int[] rolls, ArrayList<Player> players) {
		if(startIndex == rolls.length - 1) //if we're starting at the last index
			return null;
		ArrayList<Player> ties = new ArrayList<>();
		ties.add(players.get(startIndex));
		for (int i = startIndex + 1; i < rolls.length; i++) {
			if (roll == rolls[i]) {
				ties.add(players.get(i));
			}
		}
		if(ties.size() == 1)
			return null;
		else {
			return ties;
		}
	}
	
	//Flips dice X times and returns the array 
	private static int[] getNewRolls(int x) {
		int[] rolls = new int[x];
		for (int i = 0; i < x; i++) {
			rolls[i] = LadderAndSnake.flipDice();
		}
		return rolls;
	}

	private static ArrayList<Player> playerArrayToList(Player[] array) {
		return new ArrayList<>(Arrays.asList(array));
	}

	private static Player[] playerListToArray(ArrayList<Player> list) {
		return (Player[]) list.toArray(new Player[list.size()]);

	}
	
	public static String getRolls(int[] rolls) {
		String out = "";
		for(int i = 0; i < rolls.length; i++) {
			out += rolls[i];
			if(i != rolls.length - 1)
				out += ", ";
		}
		return out;
	}
	
	public static String getNames(Player[] players) {
		return getNames(playerArrayToList(players));
	}
	
	public static String getNames(ArrayList<Player> players) {
		String out = "";
		for(int i = 0; i < players.size(); i++) {
			out += players.get(i).getName().substring(0,1).toUpperCase() + players.get(i).getName().substring(1).toLowerCase();
			if(i != players.size() - 1)
				out += ", ";
		}
		return out;
	}

	public int getCurPos() {
		return curPos;
	}

	public void setCurPos(int curPos) {
		this.curPos = curPos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}