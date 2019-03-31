package org.pumatech.teams.DannyIsaac;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

public class IsaacDefense extends AbstractPlayer{

	public ArrayList<Location> blacklist = new ArrayList<Location>();
	public Location prevLoc;
	public Location myLoc;
	public Location newLoc;
	
	public IsaacDefense(Location startLocation) {
		super(startLocation);
		// TODO Auto-generated constructor stub
	}

	public Location helper() {
		Location myLoc = getLocation();
		Location otherFlag = getTeam().getOpposingTeam().getFlag().getLocation();
		Location myFlag = getTeam().getFlag().getLocation();
		Location goal;
		int dirMy = myLoc.getDirectionToward(myFlag);
		int dirOther = myLoc.getDirectionToward(otherFlag);
		Location adjLocInDirOther = myLoc.getAdjacentLocation(dirOther);
		Location adjLocInDirMy = myLoc.getAdjacentLocation(dirMy);
		
		if (this.getGrid().get(myFlag) instanceof AbstractPlayer) {
			int distance = distanceFrom(myFlag);	
			if (getTeam().getSide() == 0)
				goal = new Location(myFlag.getRow(), myFlag.getCol() + distance);
			else
				goal = new Location(myFlag.getRow(), myFlag.getCol() - distance);			
		}
		else {
			int smallest = 10;
			AbstractPlayer p = new IsaacDefense(new Location(0, 0));
			for (AbstractPlayer a: getTeam().getOpposingTeam().getPlayers()) {
				if (distanceFrom(a.getLocation()) < smallest) {
					smallest = distanceFrom(a.getLocation());
					p = a;
				}
			}
			if (smallest < 10)
				goal = p.getLocation();
			else
				goal = myFlag;
		}
		
		dirMy = myLoc.getDirectionToward(goal);
		adjLocInDirMy = myLoc.getAdjacentLocation(dirMy);
		System.out.println(goal.equals(myFlag));
		
		if (this.getGrid().get(adjLocInDirMy) instanceof Rock ||
				this.getGrid().get(adjLocInDirMy) instanceof AbstractPlayer)
			return getAroundRocks(0);
		else if (this.getGrid().get(adjLocInDirOther) instanceof Rock ||
				this.getGrid().get(adjLocInDirOther) instanceof AbstractPlayer)
			return getAroundRocks(1);
		else
			return getTeam().getFlag().getLocation();
	}
	
	public Location getMoveLocation() {
		Location chosen = helper();
		newLoc = chosen;
		// adds to blacklist
		if (prevLoc!=null && prevLoc.equals(newLoc)) {
			blacklist.add(myLoc);
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
		double highScore = -1;
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
				if (this.getGrid().get(sideLoc) instanceof AbstractPlayer && ((AbstractPlayer)this.getGrid().get(sideLoc)).getTeam() != this.getTeam()) {
					counter++;
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
			}
			if (score>highScore) {
				highScore = score;
				bestLoc = loc;
			}
		}
		return bestLoc;
	}

	
	public int distanceFrom(Location loc) {
		int x = Math.abs(getLocation().getCol() - loc.getCol());
		int y = Math.abs(getLocation().getRow() - loc.getRow());
		if (x > y) 
			return x;
		else
			return y;
	}
}