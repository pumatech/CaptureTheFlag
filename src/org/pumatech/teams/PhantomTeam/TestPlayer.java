package org.pumatech.teams.PhantomTeam;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public class TestPlayer extends AbstractPlayer {

	public TestPlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		if (hasFlag())
			return getTeam().getFlag().getLocation();
		return getTeam().getOpposingTeam().getFlag().getLocation();
	}

}
