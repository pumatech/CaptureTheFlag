package org.pumatech.teams.BustAChestnut;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.*;
import info.gridworld.grid.*;
import info.gridworld.actor.*;
import info.gridworld.world.*;

public class EdgeRusher extends AbstractPlayer {

	protected static ArrayList <Location> blacklist;
	protected ArrayList <Location> tempBlacklist;
	
	public EdgeRusher(Location startLocation) {
		super(startLocation);
		blacklist = new ArrayList<Location>();
		tempBlacklist = new ArrayList<Location>();
	}
	
	public Location getMoveLocation() {
		List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
		if (possibleMoveLocations.size() == 0) return null;
		Team opp = getTeam().getOpposingTeam();
		Location otherFlag = opp.getFlag().getLocation();
		if (!hasFlag()) return getBestAttackMove(possibleMoveLocations, otherFlag);
		else return getBestReturnMove(possibleMoveLocations);
	}
	
	public Location getBestAttackMove (List <Location> locs, Location flag) {
		Location prevSpot = this.getLocation();
		double bestScore = 999;
		Location bestMove = getLocation();
		int flagRow = flag.getRow();
		int flagCol = flag.getCol();
		
		for (int i = 0; i < locs.size(); i++) {
			int rowDiff = locs.get(i).getRow() - flagRow;
			int colDiff = locs.get(i).getCol() - flagCol;
			double dist = Math.sqrt(Math.pow(rowDiff, 2) + Math.pow(colDiff, 2));
			// Lower scores are better
			int flagCheck = locs.get(i).getCol() - getTeam().getFlag().getLocation().getCol();
			
			Location nearEnemy = checkForEnemies(this);
			if (nearEnemy != null) {
				tempBlacklist.add(locs.get(i));
				bestMove = this.getLocation();
			}
			else if (flagCheck <= 5) {
				addToBlacklist(locs.get(i));
			}
			else if (locs.get(i).equals(prevSpot)) {
				addToBlacklist(locs.get(i));
			}
			else if (isinBlacklist(locs.get(i))) {
				bestMove = this.getLocation();
			}
			else if (dist < bestScore) {
				bestScore = dist;
				bestMove = locs.get(i);
			}
		}
		
		System.out.println(bestMove);
		return bestMove;
	}
	
	public Location getBestReturnMove (List <Location> locs) {
		double bestScore = 999;
		Location bestMove = null;
		Location flag = getTeam().getFlag().getLocation();
		int flagRow = flag.getRow();
		int flagCol = flag.getCol();
		for (int i = 0; i < locs.size(); i++) {
			int rowDiff = Math.abs(locs.get(i).getRow() - flagRow);
			int colDiff = Math.abs(locs.get(i).getCol() - flagCol);
			double dist = Math.hypot(rowDiff, colDiff);
			// Lower scores are better
			if (dist < bestScore) {
				bestScore = dist;
				bestMove = locs.get(i);
			}
		}
		return bestMove;
	}
	
	public void addToBlacklist (Location loc) {
		blacklist.add(loc);
	}
	
	public boolean isinBlacklist (Location loc) {
		if (blacklist.contains(loc)) return true;
		else return false;
	}
	
	public boolean isinTempBlacklist (Location loc) {
		if (tempBlacklist.contains(loc)) return true;
		else return false;
	}
	
	public Location checkForEnemies (EdgeRusher player) {
		Location loc = player.getLocation();
		ArrayList <AbstractPlayer> enemies = new ArrayList <AbstractPlayer> ();
		for (int i = 0; i < getTeam().getOpposingTeam().getPlayers().size(); i++) {
			enemies.add(getTeam().getOpposingTeam().getPlayers().get(i));
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			int enemyLocRow = Math.abs(loc.getRow() - enemies.get(i).getLocation().getRow());
			int enemyLocCol = Math.abs(loc.getCol() - enemies.get(i).getLocation().getCol());
			double enemyLoc = Math.hypot(enemyLocRow, enemyLocCol);
			if (enemyLoc <= 4) {
				return enemies.get(i).getLocation();
			}			
		}
		
		return null;
		
	}
}
