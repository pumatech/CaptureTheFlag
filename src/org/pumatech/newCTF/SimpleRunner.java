package org.pumatech.newCTF;

import java.awt.Color;

import org.pumatech.teams.newSample.NewSampleTeam;

public class SimpleRunner {
	
	public static void main(String[] args) {
		Team a = new NewSampleTeam(Color.RED);
		Team b = new NewSampleTeam(Color.BLUE);
		CtfWorld world = new CtfWorld(a, b);
		Match match = new Match(a, b,world);
		match.start();
		Team winner = match.getWinner();
		Team loser = (winner == a) ? b : a;
		System.out.println(winner.getName() + " has won with a score of " + winner.getScore() + " to " + loser.getScore());
	}
	
}
