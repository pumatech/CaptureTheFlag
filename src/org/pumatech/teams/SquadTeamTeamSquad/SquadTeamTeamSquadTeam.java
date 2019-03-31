
package org.pumatech.teams.SquadTeamTeamSquad;
import java.awt.Color;

import org.pumatech.CTF2019.Team;
import org.pumatech.teams.sample.BeelinePlayer;
import org.pumatech.teams.sample.RandomPlayer;

import info.gridworld.grid.Location;

public class SquadTeamTeamSquadTeam extends Team{
	public SquadTeamTeamSquadTeam(){
		this(Color.WHITE);
		
	}
	public SquadTeamTeamSquadTeam(Color color) {
		this("Squad Team Team Squad", color);
	}
	public SquadTeamTeamSquadTeam(String name, Color color) {
		super(name, color);
		
//		addPlayer(new STTS_avgJoe(new Location(15, 30)));
//		addPlayer(new STTS_avgJoe(new Location(25, 30)));
//		addPlayer(new STTS_avgJoe(new Location(35, 30)));
//		addPlayer(new STTS_avgJoe(new Location(45, 30)));
//		addPlayer(new STTS_avgJoe(new Location(15, 20)));
//		addPlayer(new STTS_avgJoe(new Location(25, 20)));
//		addPlayer(new STTS_avgJoe(new Location(35, 20)));
//		addPlayer(new STTS_avgJoe(new Location(45, 20)));
	}
	public void generateTeam() {
		addPlayer(new STTS_baitPlayer(new Location(10, 30)));
		addPlayer(new STTS_avgJoe(new Location(20, 30)));
		addPlayer(new STTS_midPlayer(new Location(30, 30)));
		addPlayer(new STTS_midPlayer(new Location(40, 30)));
		addPlayer(new STTS_midPlayer(new Location(10, 20)));
		addPlayer(new STTS_defPlayer(new Location(20, 20)));
		addPlayer(new STTS_defPlayer(new Location(30, 20)));
		addPlayer(new STTS_defPlayer(new Location(40, 20)));
	}
}