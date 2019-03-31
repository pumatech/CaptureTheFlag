package org.pumatech.teams.VanDehy;

import java.util.List;

import org.pumatech.CTF2018.Flag;

import info.gridworld.grid.Location;

public class VanDehyDefense extends VanDehyPlayer {
	private Location origin;
	private Location campSpot;

	public VanDehyDefense(Location startLocation, Location camp) {
		super(startLocation);
		origin = startLocation;
		campSpot = camp;
	}

	@Override
	public Location getMoveLocation() {
		if(getSteps()==1){
			origin = getTeam().adjustForSide(origin, getGrid());
			campSpot = getTeam().adjustForSide(campSpot, getGrid());
		}
		if(!getTeam().getFlag().getLocation().equals(getTeam().adjustForSide(new Location (24,10), getGrid()))){
			campSpot = getTeam().getFlag().getLocation();
		}
		

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
			boolean danger = false;
			for (int i = 1; i < enemyLocs.size(); i++) {
				if (enemyLocs.get(i).equals(getTeam().getFlag().getLocation())
						|| getTeam().nearFlag(enemyLocs.get(i))) {
					closestEnemy = enemyLocs.get(i);
					i = enemyLocs.size();
					danger = true;
				} else if (calculateDistanceBetween(closestEnemy,
						campSpot) > calculateDistanceBetween(enemyLocs.get(i), campSpot)) {
					closestEnemy = enemyLocs.get(i);
				}
			}
			Location current = closestEnemy;
			Location best = current;
			int m = calculateDistanceBetween(closestEnemy, current);
			int n = calculateDistanceBetween(getLocation(), current);
			int L = m - n;
			if (!danger) {
				while (!this.getTeam().nearFlag(current)) {
					current = current
							.getAdjacentLocation(current.getDirectionToward(getTeam().getFlag().getLocation()));
					m = calculateDistanceBetween(closestEnemy, current);
					n = calculateDistanceBetween(getLocation(), current);
					if ((m - n) > L
							&& L < calculateDistanceBetween(closestEnemy, getTeam().getFlag().getLocation()) / 3) {
						L = m - n;
						if (isTraversable(current)) {
							best = current;
						}
					}
				}
			} else {
				if (this.getTeam().getSide() == 0) {
					if (closestEnemy.getCol() > getLocation().getCol()) {
						best = closestEnemy;
					} else {
						best = new Location(closestEnemy.getRow(), (closestEnemy.getCol() + 49) / 2);
					}
				} else {
					if (closestEnemy.getCol() < getLocation().getCol()) {
						best = closestEnemy;
					} else {
						best = new Location(closestEnemy.getRow(), (closestEnemy.getCol() + 49) / 2);
					}
				}
			}
			gol = best;
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
