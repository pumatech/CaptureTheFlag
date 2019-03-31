package org.pumatech.teams.BustAChestnut;
import java.util.List;

import org.pumatech.CTF2018.*;

import info.gridworld.grid.Location;

public class NoseTackle extends AbstractPlayer {

	public NoseTackle(Location startLocation) {
		super(startLocation);
	}
	
	public Location getMoveLocation() {
		if (hasFlag())
			return getTeam().getFlag().getLocation();
		return getTeam().getOpposingTeam().getFlag().getLocation();
	}
	
}