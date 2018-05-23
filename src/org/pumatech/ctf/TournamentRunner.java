package org.pumatech.ctf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.pumatech.teams.BustAChestnut.BustAChestnut;
import org.pumatech.teams.adamTeam.AdamTeam;
import org.pumatech.teams.BustAChestnut.BustAChestnut;
import org.pumatech.teams.Connor.ConnorTeam.Quadrigis;
import org.pumatech.teams.DannyIsaac.SomethingRelatedToDanny;
import org.pumatech.teams.DrProfessorShip.DrProfessorShipTheTeam;
import org.pumatech.teams.Skynet.SkynetTeam;
import org.pumatech.teams.ThiccBoisUnited.ThiccBoisUnited;
import org.pumatech.teams.XLpackage.AGAKTeam;
import org.pumatech.teams.danielRuiFanClub.danielRuiFanClub;
import org.pumatech.teams.sample.SampleTeam;
import org.pumatech.teams.twice.TeamTwice;

public class TournamentRunner {

	public static void main(String[] args) {
		List<Team> teams = new ArrayList<Team>();
		teams.add(new BustAChestnut());
		teams.add(new BustAChestnut());
		teams.add(new SomethingRelatedToDanny());
		teams.add(new SkynetTeam());
		teams.add(new ThiccBoisUnited());
		teams.add(new TeamTwice());
		teams.add(new AGAKTeam());
		teams.add(new DrProfessorShipTheTeam());	
		teams.add(new danielRuiFanClub());
		//teams.add(new Quadrigis());
		teams.add(new BustAChestnut());
		teams.add(new SomethingRelatedToDanny());
		teams.add(new SkynetTeam());
		teams.add(new ThiccBoisUnited());
		teams.add(new TeamTwice());
		teams.add(new AGAKTeam());
		teams.add(new DrProfessorShipTheTeam());	
		teams.add(new danielRuiFanClub());
		teams.add(new Quadrigis());
		
		Collections.shuffle(teams);

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
