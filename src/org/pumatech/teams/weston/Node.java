package org.pumatech.teams.weston;


import java.util.ArrayList;
import java.util.List;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Node {

	private Location loc;
	private Node parent;
	private int heuristicCost, movementCost;

	public Node(Location loc, int heuristicCost) {
		this(loc, heuristicCost, null);
	}

	private Node(Location loc, int heuristicCost, Node parent) {
		this.heuristicCost = heuristicCost;
		this.loc = loc;
		setParent(parent);
	}

	public List<Node> getNeighboringNodes(Grid<Actor> grid, HeuristicCalculator hc) {
		List<Node> res = new ArrayList<Node>();
		for (int row = loc.getRow() - 1; row <= loc.getRow() + 1; row++) {
			for (int col = loc.getCol() - 1; col <= loc.getCol() + 1; col++) {
				if (!(col == loc.getCol() && row == loc.getRow())) {
					res.add(new Node(new Location(row, col), hc.getValue(new Location(row, col)), this));
				}
			}
		}
		return res;
	}

	public int getTotalCost() {
		return heuristicCost + movementCost;
	}

	public int getMovementCost() {
		return movementCost;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public Location getLoc() {
		return loc;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
		if (parent == null)
			movementCost = 0;
		else
			movementCost = parent.getMovementCost() + parent.getLoc().getCol() == loc.getCol() || parent.getLoc().getRow() == loc.getRow() ? 10 : 14;
	}

	public boolean equals(Object o) {
		return (o instanceof Node) && ((Node) o).getLoc().equals(getLoc());
	}
}