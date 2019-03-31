package org.pumatech.teams.PhantomTeam;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class aStar extends AbstractPlayer {

	/*
	 * Constructor: uses super keyword to implement Abstract PLayer constructor
	 */
	public aStar(Location startLocation) {
		super(startLocation);
	}

	/*
	 * Calculates the relative "H-Cost" of moving to a certain location based on the
	 * distance from the flag.
	 */
	public int distance(Location A, Location B) {
		int dx = Math.abs(A.getRow() - B.getRow());
		int dy = Math.abs(A.getCol() - B.getCol());
		if (dx > dy)
			return 14 * dy + 10 * (dx - dy);
		return 14 * dx + 10 * (dy - dx);
	}

	/*
	 * Reverses the path of the object to make it move back to where it started once
	 * it has the flag.
	 */
	public Location Retrace() {
		Grid<Actor> grid = this.getGrid();
		MyLocation flagLoc = new MyLocation(this.getLocation());
		MyLocation myLoc = new MyLocation(this.getTeam().getOpposingTeam().getFlag().getLocation());
		List<MyLocation> open = new ArrayList<MyLocation>();
		HashSet<MyLocation> closed = new HashSet<MyLocation>();
		open.add(myLoc);
		MyLocation currentNode = null;
		while (open.size() > 0) {
			currentNode = open.get(0);
			for (int i = 1; i < open.size(); i++) {
				if (open.get(i).fCost() < currentNode.fCost() || open.get(i).fCost() == currentNode.fCost()) {
					if (open.get(i).getHCost() < currentNode.getHCost()) {
						currentNode = open.get(i);
						return currentNode.getLocation();
					}
				}
			}
			open.remove(currentNode);
			closed.add(currentNode);
			
			ArrayList<MyLocation> emptyList = new ArrayList<MyLocation>();
			for (Location loc : grid.getEmptyAdjacentLocations(currentNode.getLocation())) {
				emptyList.add(new MyLocation(loc));
			}
			for (MyLocation loc : emptyList) {
				if (closed.contains(loc))
					continue;
				int newMoveCost = currentNode.getGCost() + distance(currentNode.getLocation(), loc.getLocation());
				if (newMoveCost < loc.getGCost() || !open.contains(loc)) {
					loc.setGCost(newMoveCost);
					loc.setHCost(distance(loc.getLocation(), flagLoc.getLocation()));
					loc.setParent(currentNode);
					if (!open.contains(loc))
						open.add(loc);
				}
			}
		}
		return currentNode.getLocation();
	}

	/*
	 * Finds the location with the lowest "F-Cost" to determine which path would be
	 * the shortest.
	 */
	public Location getMoveLocation() {
		Grid<Actor> grid = this.getGrid();
		MyLocation myLoc = new MyLocation(this.getLocation());
		MyLocation flagLoc = new MyLocation(this.getTeam().getOpposingTeam().getFlag().getLocation());
		List<MyLocation> open = new ArrayList<MyLocation>();
		HashSet<MyLocation> closed = new HashSet<MyLocation>();
		open.add(myLoc);
		MyLocation currentNode = null;
		while (open.size() > 0) {
			currentNode = open.get(0);
			for (int i = 1; i < open.size(); i++) {
				if (open.get(i).fCost() < currentNode.fCost() || open.get(i).fCost() == currentNode.fCost()) {
					if (open.get(i).getHCost() < currentNode.getHCost()) {
						currentNode = open.get(i);
						return currentNode.getLocation();
					}
				}
			}
			open.remove(currentNode);
			closed.add(currentNode);
			if (this.hasFlag() == true) {
				Retrace();
			}
			ArrayList<MyLocation> emptyList = new ArrayList<MyLocation>();
			for (Location loc : grid.getEmptyAdjacentLocations(currentNode.getLocation())) {
				emptyList.add(new MyLocation(loc));
			}
			for (MyLocation loc : emptyList) {
				if (closed.contains(loc))
					continue;
				int newMoveCost = currentNode.getGCost() + distance(currentNode.getLocation(), loc.getLocation());
				if (newMoveCost < loc.getGCost() || !open.contains(loc)) {
					loc.setGCost(newMoveCost);
					loc.setHCost(distance(loc.getLocation(), flagLoc.getLocation()));
					loc.setParent(currentNode);
					if (!open.contains(loc))
						open.add(loc);
				}
			}
		}
		return currentNode.getLocation();
	}
}