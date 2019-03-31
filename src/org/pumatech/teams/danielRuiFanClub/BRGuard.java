package org.pumatech.teams.danielRuiFanClub;


import java.util.ArrayList;

import org.pumatech.CTF2018.AbstractPlayer;
import org.pumatech.CTF2018.Flag;
import org.pumatech.CTF2018.Team;

import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class BRGuard extends FinalPathfindPlayer{

	
	public Location pastLoc = this.getLocation();
	AbstractPlayer targetPlayer = null;
	boolean inPos = false;
	
	public BRGuard(Location startLocation) {
		super(startLocation);
	}
	public Location getMoveLocation() {
		flagLoc = this.getTeam().getFlag().getLocation();

		int x = flagLoc.getRow() + 5;
		int y = flagLoc.getCol() + 5;
		Location pos = new Location(x, y);
		
		while(this.getGrid().get(pos) instanceof Rock) {
			if(this.getGrid().isValid(new Location(pos.getRow(), pos.getCol()+1))) {
				pos.setCol(pos.getCol()+1);
			}
		}
		
//		System.out.println(inPos);
//		System.out.println(pos);
		
		if(enemyHasFlag()) {
			inPos = false;
			objective = flagLoc;
			return flagLoc;//test with and without pathfind
		}
		if(this.getLocation().equals(pos)) {
			inPos = true;
		}
		if(inPos) {
			objective = flagLoc;
			return flagLoc;
		}
		else {
			objective = pos;
			return pathfind(pos);
		}
		
//		Location myLoc = this.getLocation();
//		Grid myGrid = this.getGrid();
//		Team myTeam = this.getTeam();
//		Team theirTeam = myTeam.getOpposingTeam();
//		Flag theirFlag = theirTeam.getFlag();
//		Location myFlag = myTeam.getFlag().getLocation();
//
//		ArrayList<AbstractPlayer> p = new ArrayList<AbstractPlayer>();
//		p.addAll(theirTeam.getPlayers());
//		Location z = null;
//
//		int x = myFlag.getRow() + 3;
//		int y = myFlag.getCol() + 3;
//		Location pos = new Location(x, y);
//		boolean b=false;
//		
//		for (int i = 0; i < p.size(); i++) {
//			AbstractPlayer a = p.get(i);
//			if (a.hasFlag() == true) {
//				z = a.getLocation();
//				b=true;
//			}
//		}
//		if(!b) {
//			z=pos;
//		}
//
//		return z;
	}

}
