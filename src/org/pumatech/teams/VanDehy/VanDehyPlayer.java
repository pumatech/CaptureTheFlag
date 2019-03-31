package org.pumatech.teams.VanDehy;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.grid.Location;

/* to do list:
 * Avoid enemy players better
 * Make defense move with the flag
 * mid-field players that wait for enemy to cross the line?
 */
public class VanDehyPlayer extends AbstractPlayer {

	public VanDehyPlayer(Location startLocation) {
		super(startLocation);
	}

	@Override
	public Location getMoveLocation() {
		return null;
	}

	public Node aStar(Node start, Node target) {
		for (int k = 0; !isTraversable(target.getLocation()); k++) {
			target.setLocation(target.getLocation()
					.getAdjacentLocation(target.getLocation().getDirectionToward(start.getLocation())));
			if (k > 20) {
				List<Location> neighbors = this.getTraversableLocations(target.getLocation());
				try {
					target.setLocation(neighbors.get(0));
				} catch (IndexOutOfBoundsException e) {
					return start;
				}
			}
		}
		List<Node> open = new ArrayList<Node>();// set of nodes to be evaluated
		List<Node> closed = new ArrayList<Node>();// set of nodes already
		open.add(start);// add the start node to OPEN
		for (int i = 0; i < 5000; i++) {// loop
			// set "current" to the node in "open" with the lowest F cost
			Node current = new Node();
			try {
				current = open.get(open.size() - 1);
			} catch (IndexOutOfBoundsException e) {
				return start;
			}
			for (int j = open.size() - 1; j >= 0; j--) {
				if (current.getF() > open.get(j).getF()
						|| (current.getF() == open.get(j).getF() && current.getH() > open.get(j).getH())) {
					current = open.get(j);
				} else if (current.getF() == open.get(j).getF() && current.getH() == open.get(j).getH()) {
					double rand = Math.random() * 2;
					if (rand > 1) {
						current = open.get(j);
					}
				}
			}

			open.remove(current);
			closed.add(current);
			if (current.getLocation().equals(target.getLocation())) {
				return current;
			}
			Location currentLoc = current.getLocation();
			// foreach traversable neighbor of current Node
			for (Location neighbor : getTraversableLocations(currentLoc)) {
				Node n = new Node();
				n.setLocation(neighbor);
				boolean inOpen = false;
				int nDex = open.indexOf(n);
				if (nDex != -1) {
					n = open.get(nDex);
					inOpen = true;
				}
				if (!closed.contains(n)) {
					if (!inOpen || n.getG() > current.getG() + 1) {
						n.setG(current.getG() + calculateMovementCost(n));
						n.setH(n.findH(target));
						n.setF(n.getG() + n.getH());// +1 might need to be
													// something different later
													// when movement cost is
													// more dynamic
						n.setParent(current);
						if (!inOpen) {
							open.add(n);
						}
					}
				}
			}
		}
		return start;
	}

	public Node recurParent(Node a) {
		if (a.getParent().getParent() == null) {
			return a;
		}
		return recurParent(a.getParent());
	}

	// return the sum of the distance of the enemies from the end
	public int calculateMovementCost(Node to) {
		int sum = 1;
		Location end = to.getLocation();
		List<Location> enemyLocs = getEnemyLocations();
		for (int i = 0; i < enemyLocs.size(); i++) {
			if (!this.getTeam().onSide(enemyLocs.get(i))) {
				// the minimum steps in a straight line from enemyLoc to end
				sum += 8.0 / (Integer.max(Math.abs(end.getCol() - enemyLocs.get(i).getCol()),
						Math.abs(end.getRow() - enemyLocs.get(i).getRow())));
			}
		}
		return sum;
	}

	public int calculateDistanceBetween(Location start, Location end) {
		return Integer.max(Math.abs(start.getCol() - end.getCol()), Math.abs(start.getRow() - end.getRow()));
	}

	public boolean hasAdjacentEnemy(Location loc) {
		List<Location> adjacentLocs = getGrid().getValidAdjacentLocations(loc);
		List<Location> enemyLocs = getEnemyLocations();
		for (int i = 0; i < enemyLocs.size(); i++) {
			if (adjacentLocs.contains(enemyLocs.get(i))) {
				return true;
			}
		}
		return false;
	}

	public List<Location> getTraversableLocations(Location loc) {
		List<Location> traversableLocs = this.getGrid().getEmptyAdjacentLocations(loc);
		List<AbstractPlayer> enemyPlayers = getTeam().getOpposingTeam().getPlayers();
		for (int i = 0; i < enemyPlayers.size(); i++) {
			if (enemyPlayers.get(i).hasFlag()) {
				return traversableLocs;
			}
		}
		for (int i = traversableLocs.size() - 1; i >= 0; i--) {
			if (Math.sqrt(Math.pow(traversableLocs.get(i).getRow() - this.getTeam().getFlag().getLocation().getRow(), 2)
					+ Math.pow(traversableLocs.get(i).getCol() - this.getTeam().getFlag().getLocation().getCol(),
							2)) <= 4) {
				traversableLocs.remove(i);
			}
		}
		return traversableLocs;
	}

	protected boolean isTraversable(Location location) {
		return this.getGrid().isValid(location) && this.getGrid().get(location) == null
				&& (!this.getTeam().nearFlag(location)
						|| this.getGrid().get(this.getTeam().getFlag().getLocation()) instanceof AbstractPlayer);
	}

	public List<Location> getEnemyLocations() {
		List<Location> enemyLocations = new ArrayList<Location>();
		List<AbstractPlayer> enemyPlayers = getTeam().getOpposingTeam().getPlayers();
		for (int i = 0; i < enemyPlayers.size(); i++) {
			enemyLocations.add(enemyPlayers.get(i).getLocation());
		}
		return enemyLocations;
	}
	public final boolean nearFlagValid(Location loc) {
		return Math.sqrt(Math.pow(loc.getRow() - this.getTeam().getFlag().getLocation().getRow(), 2) + Math.pow(loc.getCol() - this.getTeam().getFlag().getLocation().getCol(), 2)) <= 5;
	}
}
