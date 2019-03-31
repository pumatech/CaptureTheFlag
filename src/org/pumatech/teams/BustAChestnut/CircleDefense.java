package org.pumatech.teams.BustAChestnut;

import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.grid.Location;

public class CircleDefense extends AbstractPlayer {

	protected int[][] blackList;
	protected List <AbstractPlayer> defenseTeam;

	public CircleDefense(Location startLocation) {
		super(startLocation);
		blackList = new int[50][100];
	}

	public Location getMoveLocation() {
		if (this.getSteps() % 50 == 0) {
			blackList = new int[50][100];
		}
		defenseTeam = getTeam().getPlayers();
		Location loc = getTeam().getFlag().getLocation();
		
		for(int j = 0; j < defenseTeam.size(); j++) {
			int flagRow = defenseTeam.get(j).getTeam().getFlag().getLocation().getRow();
			int flagCol = defenseTeam.get(j).getTeam().getFlag().getLocation().getCol();
			int playerRow = defenseTeam.get(j).getLocation().getRow();
			int playerCol = defenseTeam.get(j).getLocation().getCol();
			
			double distance = Math.hypot(playerRow - flagRow, playerCol - flagCol);
//			System.out.println(distance);
			if(distance <= 5) {
				loc = circleFlag();
			}
		}
		loc = getBestMove(loc);
		blackList[loc.getRow()][loc.getCol()]++;
		return loc;
		
		// if in range of flag, turn 1 degree and move

	}
	
	
	
	public Location circleFlag() {
		
		Location flag = getTeam().getFlag().getLocation();
		int degree = this.getLocation().getDirectionToward(flag);
		Location move = this.getLocation().getAdjacentLocation(degree + 90);
//		System.out.println(move);
		return move;
		
	}

	public Location getBestMove(Location goalLoc) {
		List<Location> emptyAdjacent = this.getGrid().getEmptyAdjacentLocations(this.getLocation());
		Location bestLoc = null;
		double highScore = -10000;
		for (Location location : emptyAdjacent) {
			double score = getScore(location, goalLoc);
			if (score > highScore) {
				highScore = score;
				bestLoc = location;
			}
		}
		return bestLoc;
	}

	public double getScore(Location loc, Location goalLoc) {
		double distance = Math.hypot(loc.getRow() - goalLoc.getRow(), loc.getCol() - goalLoc.getCol());
		double distanceScore = 1 / distance;
		int numVisits = blackList[loc.getRow()][loc.getCol()];
		double visitBadness = 1;
		if (numVisits != 0) {
			visitBadness = 1.0 / blackList[loc.getRow()][loc.getCol()];
		}
		return distanceScore * visitBadness;
	}

}
