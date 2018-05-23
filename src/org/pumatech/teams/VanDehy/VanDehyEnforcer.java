package org.pumatech.teams.VanDehy;

import java.util.List;

import org.pumatech.ctf.Flag;

import info.gridworld.grid.Location;

public class VanDehyEnforcer extends VanDehyPlayer {
	private Location origin;

	public VanDehyEnforcer(Location startLocation) {
		super(startLocation);
		origin = startLocation;
	}

	@Override
	public Location getMoveLocation() {
		if (getSteps() == 1)
			origin = getTeam().adjustForSide(origin, getGrid());
		Location gol = determineGoal();
		Node goal = new Node(gol.getRow(), gol.getCol());
		Node start = new Node();
		start.setLocation(this.getLocation());
		start.setH(start.findH(goal));
		start.setG(0);
		start.setF(start.findF());
		if (!(getGrid().get(getTeam().getFlag().getLocation()) instanceof Flag)) {
			gol = getTeam().getFlag().getLocation();
			goal.setRow(gol.getRow());
			goal.setCol(gol.getCol());
		}
		Node a = aStar(start, goal);
		Location loc = start.getLocation();
		try {
			loc = recurParent(a).getLocation();
		} catch (NullPointerException e) {
		}
		return loc;
	}

	public Location determineGoal() {
		List<Location> enemyLocs = getEnemyLocations();
		Location gol = origin;
		for (int j = enemyLocs.size() - 1; j >= 0; j--) {
			if (!getTeam().onSide(enemyLocs.get(j))) {
				enemyLocs.remove(j);
			}
		}
		if (enemyLocs.size() != 0) {
			Location closestEnemy = enemyLocs.get(0);
			for (int i = 1; i < enemyLocs.size(); i++) {
				if (calculateDistanceBetween(closestEnemy,
						getTeam().getFlag().getLocation()) > calculateDistanceBetween(enemyLocs.get(i),
								getTeam().getFlag().getLocation())) {
					closestEnemy = enemyLocs.get(i);
				}
			}
			gol = closestEnemy;
		}
		return gol;
	}

	@Override
	public int calculateMovementCost(Node to) {
		int sum = 1;
		List<Location> enemyLocs = getEnemyLocations();
		for (int i = 0; i < enemyLocs.size(); i++) {
			if (this.getTeam().onSide(enemyLocs.get(i))) {
				// the minimum steps in a straight line from enemyLoc to end
				sum += (calculateDistanceBetween(enemyLocs.get(i), to.getLocation()));
			}
		}
		return sum;
	}
}
