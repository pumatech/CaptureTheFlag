package org.pumatech.ctf.teams.danielRuiFanClub;
import java.util.ArrayList;

import org.pumatech.ctf.AbstractPlayer;
import org.pumatech.ctf.Flag;
import org.pumatech.ctf.Team;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Attacker extends FinalPathfindPlayer{
	public Location pastLoc = this.getLocation();
	Location flagLoc = null;
	
	public Attacker(Location startLocation) {
		super(startLocation);
	}
	
}


