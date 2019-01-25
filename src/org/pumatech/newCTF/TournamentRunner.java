package org.pumatech.newCTF;

import java.awt.Color;
import java.util.ArrayList;

import org.pumatech.teams.ConnorTeam.NewConnorTeam.NewQuadrigisTeam;
import org.pumatech.teams.SquadTeamTeamSquad.SquadTeamTeamSquadTeam;
import org.pumatech.teams.newSample.NewSampleTeam;

public class TournamentRunner {
	public static void main(String[] args) {
		ArrayList<Team> teams = new ArrayList<Team>();
		
		teams.add(new NewSampleTeam(Color.RED));
		teams.add(new SquadTeamTeamSquadTeam(Color.PINK));
		teams.add(new NewQuadrigisTeam(Color.BLUE));
		teams.add(new NewSampleTeam(Color.GREEN));
		CtfWorld world = new CtfWorld(teams.get(0), teams.get(1));
		Tournament tournament = new Tournament(teams,world);
		tournament.start();
		Team winner = tournament.getWinner();
		System.out.println(winner.getName()+" won the tournament 2");
	}
}
