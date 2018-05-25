package org.pumatech.teams.newSample;

import java.util.List;

import org.pumatech.newCTF.AbstractPlayer;

import info.gridworld.grid.Location;

public class RandomPlayer extends AbstractPlayer {

	public RandomPlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation()); 
		if (possibleMoveLocations.size() == 0) return null;
		return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
	}

}
