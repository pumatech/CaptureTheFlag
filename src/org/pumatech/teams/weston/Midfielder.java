package org.pumatech.teams.weston;


import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public class Midfielder extends AbstractPlayer {

	private PathFinder pathFinder;
	
	public Midfielder(Location startLocation) {
		super(startLocation);
		pathFinder = new PathFinder(this);
	}

	public Location getMoveLocation() {
		final int teamNum = getTeam().getSide();
		Location flagLocation = getTeam().getFlag().getLocation();
		Location destLocation = null;
		@SuppressWarnings("unchecked")
		List<AbstractPlayer> myTeam = (List<AbstractPlayer>) ((ArrayList<AbstractPlayer>) getTeam().getPlayers()).clone();
		for (int i = myTeam.size() - 1; i >= 0; i--) {
			if (!(myTeam.get(i) instanceof Midfielder))
				myTeam.remove(i);
		}
		List<AbstractPlayer> opposingTeam = getTeam().getOpposingTeam().getPlayers();
		for (int i = 0; i < opposingTeam.size(); i++) {
			Location playerLoc = opposingTeam.get(i).getLocation();
			double distanceToPlayer = distanceBetween(getLocation(), playerLoc);
			double distanceToFlag = distanceBetween(flagLocation, playerLoc);
			if (getTeam().onSide(playerLoc)) {
				if (((AbstractPlayer) getGrid().get(playerLoc)).hasFlag()) {
					PathFinder enemyPathFinder = new PathFinder((AbstractPlayer) getGrid().get(playerLoc), false);
					final int otherTeamNum = getTeam().getOpposingTeam().getSide();
					HeuristicCalculator enemyHeuristic = new HeuristicCalculator() {
						public int getValue(Location loc) {
							if (otherTeamNum == 0)
								return loc.getCol() - getGrid().getNumCols() / 2 + 1;
							else
								return getGrid().getNumCols() / 2 - loc.getCol() - 1;
						}
						
						public boolean destIsFlag() {
							return false;
						}
					};
					if (Math.abs(playerLoc.getCol() - 50) > 30 && distanceBetween(getLocation(), playerLoc) > 10)
						playerLoc = enemyPathFinder.getNthNextLocation(playerLoc, enemyHeuristic, 10);
					else if (Math.abs(playerLoc.getCol() - 50) > 10 && distanceBetween(getLocation(), playerLoc) > 5)
						playerLoc = enemyPathFinder.getNthNextLocation(playerLoc, enemyHeuristic, 5);
					else if (distanceBetween(getLocation(), playerLoc) > 5)
						playerLoc = enemyPathFinder.getNthNextLocation(playerLoc, enemyHeuristic, 3);
					return pathFinder.getNextLocation(getLocation(), playerLoc);
				} else if (destLocation == null || distanceBetween(flagLocation, destLocation) > distanceToFlag) {
					int numCloserDefenders = 0;
					for (int j = 0; j < myTeam.size(); j++) {
						if (distanceBetween(myTeam.get(j).getLocation(), playerLoc) < distanceToPlayer)
							numCloserDefenders++;
					}
					if (numCloserDefenders <= 0)
						destLocation = playerLoc;
				}
			}
		}
		if (destLocation != null) {
			return pathFinder.getNextLocation(getLocation(), destLocation);
		} else {
			HeuristicCalculator hc = new HeuristicCalculator() {
				public int getValue(Location loc) {
					return Math.abs(loc.getCol() - (getGrid().getNumCols() / 2 + (teamNum == 0 ? 5 : -5)));
				}

				public boolean destIsFlag() {
					return false;
				}
			};
			return pathFinder.getNextLocation(getLocation(), hc);
		}
	}

	private double distanceBetween(Location a, Location b) {
		return Math.sqrt(Math.pow(a.getCol() - b.getCol(), 2) + Math.pow(a.getRow() - b.getRow(), 2));
	}

}
