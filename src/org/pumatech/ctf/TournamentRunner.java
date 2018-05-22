package org.pumatech.ctf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.pumatech.teams.BustAChestnut.BustAChestnut;
import org.pumatech.teams.DannyIsaac.SomethingRelatedToDanny;
import org.pumatech.teams.Skynet.SkynetTeam;
import org.pumatech.teams.ThiccBoisUnited.ThiccBoisUnited;
import org.pumatech.teams.XLpackage.AGAKTeam;
import org.pumatech.teams.sample.SampleTeam;
import org.pumatech.teams.twice.TeamTwice;

public class TournamentRunner {

	public static void main(String[] args) {
		List<Team> teams = new ArrayList<Team>();
		teams.add(new BustAChestnut());
		teams.add(new SomethingRelatedToDanny());
		teams.add(new SkynetTeam());
		teams.add(new ThiccBoisUnited());
		teams.add(new TeamTwice());
		teams.add(new AGAKTeam());
		
		Bracket bracket = new Bracket(teams);
		JFrame bracketViewer = new JFrame("Capture The Flag 2017 Bracket");
		bracketViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bracketViewer.setResizable(false);
		bracketViewer.add(bracket);
		bracketViewer.pack();
		bracketViewer.setLocationRelativeTo(null);
		bracketViewer.setVisible(true);
		
		bracket.getWinner();
	}
	
}
