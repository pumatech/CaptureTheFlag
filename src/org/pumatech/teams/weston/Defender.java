package org.pumatech.teams.weston;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public class Defender extends AbstractPlayer {

	private PathFinder pathFinder;

	public Defender(Location startLocation) {
		super(startLocation);
		pathFinder = new PathFinder(this, false);
	}

	public Location getMoveLocation() {
		Location flagLocation = getTeam().getFlag().getLocation();
		Location destLocation = null;
		@SuppressWarnings("unchecked")
		List<AbstractPlayer> myTeam = (List<AbstractPlayer>) ((ArrayList<AbstractPlayer>) getTeam().getPlayers()).clone();
		for (int i = myTeam.size() - 1; i >= 0; i--) {
			if (!(myTeam.get(i) instanceof Defender))
				myTeam.remove(i);
		}
		List<AbstractPlayer> opposingTeam = getTeam().getOpposingTeam().getPlayers();
		for (int i = 0; i < opposingTeam.size(); i++) {
			Location playerLoc = opposingTeam.get(i).getLocation();
			double distanceToPlayer = distanceBetween(getLocation(), playerLoc);
			double distanceToFlag = distanceBetween(flagLocation, playerLoc);
			if (((AbstractPlayer) getGrid().get(playerLoc)).hasFlag()) {
				PathFinder enemyPathFinder = new PathFinder((AbstractPlayer) getGrid().get(playerLoc), false);
				final int teamNum = getTeam().getOpposingTeam().getSide();
				HeuristicCalculator enemyHeuristic = new HeuristicCalculator() {
					public int getValue(Location loc) {
						if (teamNum == 0)
							return loc.getCol() - getGrid().getNumCols() / 2 + 1;
						else
							return getGrid().getNumCols() / 2 - loc.getCol() - 1;
					}

					public boolean destIsFlag() {
						return false;
					}
				};
				int numCloserDefenders = 0;
				for (int j = 0; j < myTeam.size(); j++) {
					if (distanceBetween(myTeam.get(j).getLocation(), playerLoc) + 2 < distanceToPlayer)
						numCloserDefenders++;
				}
				if (numCloserDefenders <= 1)
					destLocation = playerLoc;
				if (numCloserDefenders != 0) {
					if (enemyHeuristic.getValue(getLocation()) < enemyHeuristic.getValue(playerLoc) - 4)
						playerLoc = enemyPathFinder.getNthNextLocation(playerLoc, enemyHeuristic, 10);
					else if (enemyHeuristic.getValue(getLocation()) < enemyHeuristic.getValue(playerLoc) - 2)
						playerLoc = enemyPathFinder.getNthNextLocation(playerLoc, enemyHeuristic, 5);
					else if (enemyHeuristic.getValue(getLocation()) < enemyHeuristic.getValue(playerLoc) - 1)
						playerLoc = enemyPathFinder.getNthNextLocation(playerLoc, enemyHeuristic, 3);
				}
				return pathFinder.getNextLocation(getLocation(), playerLoc);
			} else if (destLocation == null || distanceBetween(flagLocation, destLocation) > distanceToFlag) {
				if (distanceToFlag < 35) {
					int numCloserDefenders = 0;
					for (int j = 0; j < myTeam.size(); j++) {
						if (distanceBetween(myTeam.get(j).getLocation(), playerLoc) + 2 < distanceToPlayer)
							numCloserDefenders++;
					}
					if (numCloserDefenders <= 1)
						destLocation = playerLoc;
				}
			}
		}
		if (destLocation == null) {
			destLocation = flagLocation;
		} else if (distanceBetween(destLocation, flagLocation) > 6) {
			PathFinder enemyPathFinder = new PathFinder((AbstractPlayer) getGrid().get(destLocation), false);
			destLocation = enemyPathFinder.getNthNextLocation(destLocation, flagLocation, -4);
			if (distanceBetween(destLocation, getLocation()) < 1.5) {
				destLocation = enemyPathFinder.getNthNextLocation(enemyPathFinder.getPlayer().getLocation(),
						flagLocation, 2);
			}
		}
		return pathFinder.getNextLocation(getLocation(), destLocation);
	}

	private double distanceBetween(Location a, Location b) {
		return Math.sqrt(Math.pow(a.getCol() - b.getCol(), 2) + Math.pow(a.getRow() - b.getRow(), 2));
	}

}
