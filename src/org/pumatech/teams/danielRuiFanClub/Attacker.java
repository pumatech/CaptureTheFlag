package org.pumatech.teams.danielRuiFanClub;

import java.util.ArrayList;

import org.pumatech.CTF2018.AbstractPlayer;
import org.pumatech.CTF2018.Flag;
import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Attacker extends FinalPathfindPlayer{
	public Location pastLoc = this.getLocation();
	Location flagLoc = null;
	
	public Attacker(Location startLocation) {
		super(startLocation);
	}
	
}
