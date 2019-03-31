package org.pumatech.teams.danielRuiFanClub;

import java.util.ArrayList;

import org.pumatech.CTF2018.AbstractPlayer;
import org.pumatech.CTF2018.Flag;
import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Defender extends FinalPathfindPlayer{

	
	public Location pastLoc = this.getLocation();
	AbstractPlayer targetPlayer = null;
	
	public Defender(Location startLocation) {
		super(startLocation);
	}
	public Location getMoveLocation() {
		flagLoc = this.getTeam().getFlag().getLocation();
		Team myTeam = this.getTeam();
		Team theirTeam = myTeam.getOpposingTeam();
		
		if(targetPlayer != null && targetPlayer.getTeam().onSide(targetPlayer.getLocation())) {
			targetPlayer = null;
		}
		else if(targetPlayer != null && !enemyHasFlag() && getDistance(flagLoc, this.getLocation()) > (getDistance(flagLoc, targetPlayer.getLocation())+2) ) {
			targetPlayer = null;
		}
		else {
			ArrayList<AbstractPlayer> p = new ArrayList<AbstractPlayer>();
			p.addAll(theirTeam.getPlayers());
			for (int i = 0; i < p.size(); i++) {
				AbstractPlayer a = p.get(i);
				if(!a.getTeam().onSide(a.getLocation()) && targetPlayer == null) {
					targetPlayer = a;
				}
			}
		}
		if(targetPlayer == null) {
			objective = this.getTeam().getFlag().getLocation();
			return pathfind(this.getTeam().getFlag().getLocation());
		}
		else {
			objective = targetPlayer.getLocation();
			return pathfind(targetPlayer.getLocation());
		}
	}

}
