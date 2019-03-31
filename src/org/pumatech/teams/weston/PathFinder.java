package org.pumatech.teams.weston;



import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;
import org.pumatech.CTF2018.Flag;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class PathFinder {
	private AbstractPlayer player;
	private boolean avoidEnemies;
	
	public PathFinder(AbstractPlayer player) {
		this(player, true);
	}
	
	public PathFinder(AbstractPlayer player, boolean avoidEnemies) {
		this.player = player;
		this.avoidEnemies = avoidEnemies;
	}

	public Location getNthNextLocation(Location start, HeuristicCalculator hc, int n) {
		boolean destIsFlag = hc.destIsFlag();
		List<Node> open = new ArrayList<Node>();
		List<Node> closed = new ArrayList<Node>();
		open.add(new Node(start, hc.getValue(start)));
		while (open.size() > 0) {
			Node lowestCostNode = open.get(0);
			for (int i = 1; i < open.size(); i++) {
				if (open.get(i).getTotalCost() < lowestCostNode.getTotalCost()) {
					lowestCostNode = open.get(i);
				}
			}
			open.remove(lowestCostNode);
			closed.add(lowestCostNode);
			if (lowestCostNode.getHeuristicCost() <= 0 && isMoveable(lowestCostNode.getLoc(), destIsFlag)) {
				int pathLength = 1;
				Node node = lowestCostNode;
				while (node.getParent() != null) {
					node = node.getParent();
					pathLength++;
				}
				if (player instanceof Midfielder)
					System.out.println(pathLength);
				if (n < 0) n += pathLength - 1;
				if (n < 0 || n >= pathLength)
					return lowestCostNode.getLoc();
				node = lowestCostNode;
				while (pathLength > n + 1) {
					node = node.getParent();
					pathLength--;
				}
				return node.getLoc();
			} else if (lowestCostNode.getMovementCost() > 150) {
				System.out.println("Dest unreachable");
				return start;
			} else {
				List<Node> newNodes = lowestCostNode.getNeighboringNodes(player.getGrid(), hc);
				for (int i = 0; i < newNodes.size(); i++) {
					if (player.getGrid().isValid(newNodes.get(i).getLoc()) && isMoveable(newNodes.get(i).getLoc(), destIsFlag) && !closed.contains(newNodes.get(i))) {
						if (!open.contains(newNodes.get(i))) {
							open.add(newNodes.get(i));
						} else if (newNodes.get(i).getMovementCost() < open.get(open.indexOf(newNodes.get(i)))
								.getMovementCost()) {
							newNodes.get(i).setParent(lowestCostNode);
						}
					}
				}
			}
		}
		return start;
	}
	
	public Location getNthNextLocation(Location start, final Location dest, int n) {
		final boolean destIsFlag = player.getGrid().get(dest) instanceof Flag;
		if (!isMoveable(dest, destIsFlag)) {
			System.out.println("Unmovable destination");
			ArrayList<Location> list = player.getGrid().getEmptyAdjacentLocations(start);
			if (list.size() == 0)
				return start;
			return list.get((int) (Math.random() * list.size()));
		}
		return getNthNextLocation(start, new HeuristicCalculator() {
			public int getValue(Location loc) {
				return 10 * Math.abs(Math.abs(loc.getRow() - dest.getRow()) - Math.abs(loc.getCol() - dest.getCol())) + 14 * Math.min(Math.abs(loc.getRow() - dest.getRow()), Math.abs(loc.getCol() - dest.getCol()));
			}

			public boolean destIsFlag() {
				return destIsFlag;
			}
		}, n);
	}
	
	public Location getNextLocation(Location start, final Location dest) {
		return getNthNextLocation(start, dest, 1);
	}
	
	public Location getNextLocation(Location start, HeuristicCalculator hc) {
		return getNthNextLocation(start, hc, 1);
	}

	public boolean isMoveable(Location loc, boolean destIsFlag) {
		Grid<Actor> grid = player.getGrid();
		return loc.getCol() < 100 && grid.isValid(loc) && (destIsFlag || !player.getTeam().nearFlag(loc)) && !(avoidEnemies && nearEnemy(loc))
				&& (grid.get(loc) == null || grid.get(loc) instanceof Flag || loc.equals(player.getLocation()) || grid.get(loc) instanceof AbstractPlayer
						&& !((AbstractPlayer) grid.get(loc)).getTeam().equals(player.getTeam()));
	}

	private boolean nearEnemy(Location loc) {
		Grid<Actor> grid = player.getGrid();
		if (player.getTeam().onSide(loc))
			return false;
		if (!grid.isValid(loc))
			return false;
		int searchRadius = 2;
		for (int row = loc.getRow() - searchRadius; row <= loc.getRow() + searchRadius; row++) {
			for (int col = loc.getCol() - searchRadius; col <= loc.getCol() + searchRadius; col++) {
				Location searchLoc = new Location(row, col);
				Actor a = grid.isValid(searchLoc) ? grid.get(searchLoc) : null;
				if (a != null && a instanceof AbstractPlayer && !((AbstractPlayer) a).getTeam().equals(player.getTeam())
						&& (!adjacent(player.getLocation(), searchLoc) || adjacent(searchLoc, loc))) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean adjacent(Location a, Location b) {
		return (Math.abs(a.getCol() - b.getCol()) <= 1 && Math.abs(a.getRow() - b.getRow()) <= 1) && !a.equals(b);
	}
	
	public AbstractPlayer getPlayer() {
		return player;
	}
}
