package org.pumatech.teams.DannyIsaac;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

public class DannyOffense extends AbstractPlayer {
	
	public ArrayList<Location> blacklist = new ArrayList<Location>();
	public Location prevLoc;
	public Location myLoc;
	public Location newLoc;

	public DannyOffense(Location startLocation) {
		super(startLocation);
	}

	public Location helper() {
		Location myLoc = getLocation();
		Location otherFlag = getTeam().getOpposingTeam().getFlag().getLocation();
		Location myFlag = getTeam().getFlag().getLocation();
		int dirMy = myLoc.getDirectionToward(myFlag);
		int dirOther = myLoc.getDirectionToward(otherFlag);
		Location adjLocInDirOther = myLoc.getAdjacentLocation(dirOther);
		Location adjLocInDirMy = myLoc.getAdjacentLocation(dirMy);
		
		if (hasFlag()) {
			if (this.getGrid().get(adjLocInDirMy) instanceof Rock || this.getGrid().get(adjLocInDirMy) instanceof AbstractPlayer) {
				return getAroundRocks(1);
//				return randomWalk();
			}
			return getTeam().getFlag().getLocation();
		}
		
		if (this.getGrid().get(otherFlag) instanceof AbstractPlayer) {
			return randomWalk();
		}
		if (this.getGrid().get(adjLocInDirOther) instanceof Rock || this.getGrid().get(adjLocInDirOther) instanceof AbstractPlayer) {
			return getAroundRocks(0);
//			return randomWalk();
		}
		return getTeam().getOpposingTeam().getFlag().getLocation();
	}
	public Location getPrev() {
		return prevLoc;
	}
	public Location getMoveLocation() {
		myLoc = getLocation();
		Location chosen = helper();
		newLoc = chosen;
		System.out.println(prevLoc);
		System.out.println(chosen);
		// adds to blacklist
		if (prevLoc!=null && prevLoc.equals(newLoc)) {
			blacklist.add(myLoc);
			System.out.println("blacklistaddeedddd");
			newLoc = helper();
		}
		prevLoc = myLoc;
		return newLoc;
	}
	
	public Location getAroundRocks(int a) {
		List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
		Location otherFlag = getTeam().getOpposingTeam().getFlag().getLocation();
		if (possibleMoveLocations.size() == 0) return null;
		Location myFlag = getTeam().getFlag().getLocation();
		
		Location bestLoc = null;
		double highScore = -50;
		int distSquared = 0;
		
		for (Location loc: possibleMoveLocations) {
			if (a == 0) {
				distSquared = (int) (Math.pow((otherFlag.getCol()-loc.getCol()), 2) + Math.pow((otherFlag.getRow()-loc.getRow()), 2));
			} else {
				distSquared = (int) (Math.pow((myFlag.getCol()-loc.getCol()), 2) + Math.pow((myFlag.getRow()-loc.getRow()), 2));
			}
			
			// set score
			double score = 0;
			if (score > -50) {
				score = 100/Math.pow(distSquared, 0.5);
			}
			
			// enemies nearby
			int counter = 0;
			for (Location sideLoc: getGrid().getOccupiedAdjacentLocations(loc)) {
//				System.out.println(this.getGrid().get(sideLoc));
				if (this.getGrid().get(sideLoc) instanceof AbstractPlayer && ((AbstractPlayer)this.getGrid().get(sideLoc)).getTeam() != this.getTeam()) {
					counter++;
					System.out.println("enemy near by");
				}
				for (Location sidesideLoc: getGrid().getOccupiedAdjacentLocations(sideLoc)) {
					if (this.getGrid().get(sidesideLoc) instanceof AbstractPlayer && ((AbstractPlayer)this.getGrid().get(sidesideLoc)).getTeam() != this.getTeam()) {
						counter++;
					}
				}
			}
			if (counter>0) {
				score = -counter;
			}
			
			// blacklist
			if (blacklist.contains(loc)) {
				score = -100;
				System.out.println(blacklist);
				System.out.println("thiiisss issss noooo goooodddd");
			}
			if (score>highScore) {
				highScore = score;
				bestLoc = loc;
			}
		}
		return bestLoc;
	}
	
	public Location randomWalk() {
		List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
		if (possibleMoveLocations.size() == 0) return null;
		return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
	}

}
