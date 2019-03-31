package org.pumatech.newCTF;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.pumatech.teams.ConnorTeam.NewConnorTeam.NewQuadrigisTeam;
import org.pumatech.teams.newSample.NewSampleTeam;

import javax.imageio.ImageIO;

public class SimpleRunner {
	
	public static void main(String[] args) {
		Team a = new NewSampleTeam(Color.RED);
		Team b = new NewQuadrigisTeam(Color.BLUE);
		CtfWorld world = null;
		world = new CtfWorld(a, b);
		Match match = new Match(a, b,world);
		match.start();
		Team winner = match.getWinner();
		Team loser = (winner == a) ? b : a;
		System.out.println(winner.getName() + " has won with a score of " + winner.getScore() + " to " + loser.getScore());
		winner.displayStats(world.getSteps());
		loser.displayStats(world.getSteps());
	}
	
}
