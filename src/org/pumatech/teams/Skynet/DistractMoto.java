package org.pumatech.teams.Skynet;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.*;

import info.gridworld.actor.*;
import info.gridworld.grid.Location;

/*
This is Skynet's main offensive player.
This class is my pride and joy. Break it and I will be angry with you. 
-Jeffrey Collins
*/

public class DistractMoto extends MovingPlayer {

	private Location pastLocation;
	private int targetRow;

	public DistractMoto(Location startLocation, int targetRow) {
		super(startLocation);
		this.targetRow = targetRow;
	}

	public Location getMoveLocation() {
		if(targetRow == 49) {
			targetRow = this.getGrid().getNumRows()-1;
		}
		List<Location> possibleMoveLocations = this.getGrid().getEmptyAdjacentLocations(getLocation());
		if (possibleMoveLocations.size() == 0) {
			return null;
		}
		// keep the blacklist at a specified size
		if (locationBlacklist.size() > blacklistSize) {
			for (int i = 0; i < locationBlacklist.size() - blacklistSize; i++) {
				locationBlacklist.remove(locationBlacklist.size() - 1);
			}
		}
		// go to corner of field to draw off defenders
		if (this.getTeam().getOpposingTeam().getFlag().getLocation().getCol() < this.getLocation().getCol()) {
			return avoid(possibleMoveLocations, new Location(targetRow, 0));
		} else {
			return avoid(possibleMoveLocations, new Location(targetRow, this.getGrid().getNumCols()-1));
		}
	}

	public Location avoid(List<Location> scan, Location target) {
		// do nothing if there are no empty locations
		if (scan.size() <= 0) {
			return this.getLocation();
		}
		// prevent concurrent modification exceptions
		ArrayList<Location> temp = new ArrayList<Location>(scan);
		// remove unsafe movement options
		for (Location test : scan) {
			//remove blacklisted options
			if (locationBlacklist.contains(test)) {
				temp.remove(test);
			}
			// avoid being tagged
			List<AbstractPlayer> theirPlayers = this.getTeam().getOpposingTeam().getPlayers();
			for (AbstractPlayer detect : theirPlayers) {
				if (this.getGrid().get(test) == detect) {
					temp.remove(test);
				}
				for (Actor a : this.getGrid().getNeighbors(detect.getLocation())) {
					if (a.equals(detect)) {
						temp.remove(test);
					}
					if (!(detect.getTeam() instanceof SkynetTeam)) {
						if (detect.getMoveLocation() != null) {
							for (Location tem : getGrid().getEmptyAdjacentLocations(detect.getMoveLocation())) {
								if (a == getGrid().get(tem)) {
									temp.remove(test);
								}
							}
						}
					}
				}
			}
		}
		// update scan
		scan = temp;

		// do nothing if there are no safe locations
		if (scan.size() <= 0) {
			return this.getLocation();
		}

		// determine optimal location based on direction
		int minDir = 360;
		Location best = scan.get(0);
		for (Location loc : scan) {
			int actual = this.getLocation().getDirectionToward(loc);
			int optimal = this.getLocation().getDirectionToward(target);
			if (Math.abs(optimal - actual) < minDir) {
				if (this.getGrid().getEmptyAdjacentLocations(loc).size() >= 1) {
					if (!locationBlacklist.contains(loc) && !loc.equals(pastLocation)) {
						best = loc;
					}
				}
				minDir = Math.abs(optimal - actual);
			}
		}
		// blacklist unsuitable locations
		if (Math.abs(
				this.getLocation().getDirectionToward(best) - this.getLocation().getDirectionToward(target)) >= 90) {
			if (!locationBlacklist.contains(pastLocation)) {
				locationBlacklist.add(pastLocation);
			}
		}
		if (best.equals(pastLocation)) {
			// blacklist to prevent moving back
			if (!locationBlacklist.contains(this.getLocation())) {
				locationBlacklist.add(this.getLocation());
			}
			// take a step back
			if(target.getCol() > this.getLocation().getCol()) {
				if(this.getLocation().getCol() > 0) {
					Location stepBack = new Location(this.getLocation().getCol()-1, this.getLocation().getRow());
					if(scan.contains(stepBack)) {
						best = stepBack;
					}
				}
			}
			if(target.getCol() < this.getLocation().getCol()) {
				if(this.getLocation().getCol() < this.getGrid().getNumCols()) {
					Location stepBack = new Location(this.getLocation().getCol()+1, this.getLocation().getRow());
					if(scan.contains(stepBack)) {
						best = stepBack;
					}
				}
			}
		}
		// update pastLocation and return
		pastLocation = this.getLocation();
		return best;
	}

	// formerly known as public void takeOnMe()
	public void printMap() {
		// print out game map
		List<Location> occupied = this.getGrid().getOccupiedLocations();
		for (int row = 0; row < this.getGrid().getNumRows(); row++) {
			for (int col = 0; col < this.getGrid().getNumCols(); col++) {
				Location scan = new Location(row, col);
				if (occupied.contains(scan)) {
					// print actors
					Actor obj = this.getGrid().get(scan);
					if (obj instanceof Rock) {
						System.out.print("#");
					} else if (obj instanceof Flag) {
						System.out.print("F");
					} else if (obj instanceof Moto) {
						System.out.print("M");
					} else if (obj instanceof T850) {
						System.out.print("A");
					} else if (obj instanceof T1K) {
						System.out.print("T");
					} else if (obj instanceof SkynetDupe) {
						System.out.print("S");
					}
				} else if (locationBlacklist.contains(scan)) {
					// print blacklisted Locations
					System.err.print("#");
				} else {
					// print empty
					System.out.print("-");
				}
			}
			// new line
			System.out.println();
		}
	}

}
