package org.pumatech.ctf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.pumatech.teams.BustAChestnut.BustAChestnut;
import org.pumatech.teams.adamTeam.AdamTeam;
import org.pumatech.teams.sample.SampleTeam;

public class TournamentRunner {

	public static void main(String[] args) {
		List<Team> teams = new ArrayList<Team>();
		teams.add(new BustAChestnut());
		
		Bracket bracket = new Bracket(teams);
		JFrame bracketViewer = new JFrame("Capture The Flag 2018 Bracket");
		bracketViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bracketViewer.setResizable(false);
		bracketViewer.add(bracket);
		bracketViewer.pack();
		bracketViewer.setLocationRelativeTo(null);
		bracketViewer.setVisible(true);
		
		bracket.getWinner();
	}
	
}
