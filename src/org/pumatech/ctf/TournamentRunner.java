package org.pumatech.ctf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.pumatech.teams.BustAChestnut.BustAChestnut;
import org.pumatech.teams.ConnorTeam.QuadrigisTeam;
import org.pumatech.teams.BustAChestnut.BustAChestnut;
import org.pumatech.teams.BestTeam.BestTeam;
import org.pumatech.teams.DannyIsaac.SomethingRelatedToDanny;
import org.pumatech.teams.DrProfessorShip.DrProfessorShipTheTeam;
import org.pumatech.teams.MemeDreamTeam.MemeDreamTeam;
import org.pumatech.teams.Skynet.SkynetTeam;
import org.pumatech.teams.ThiccBoisUnited.ThiccBoisUnited;
import org.pumatech.teams.XLpackage.AGAKTeam;
import org.pumatech.teams.danielRuiFanClub.danielRuiFanClub;
import org.pumatech.teams.sample.SampleTeam;
import org.pumatech.teams.twice.TeamTwice;

public class TournamentRunner {

	public static void main(String[] args) {
		List<Team> teams = new ArrayList<Team>();
		
//		teams.add(new org.pumatech.teams.ArpitaMakenna.SampleTeam());
		teams.add(new BestTeam());
//		teams.add(new BustAChestnut());
		teams.add(new QuadrigisTeam());
//		teams.add(new danielRuiFanClub());
//		teams.add(new SomethingRelatedToDanny());
//		teams.add(new DrProfessorShipTheTeam());	
//		teams.add(new MemeDreamTeam());	
//		teams.add(new SkynetTeam());
//		teams.add(new ThiccBoisUnited());
//		teams.add(new TeamTwice());
//		teams.add(new AGAKTeam());
		
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
