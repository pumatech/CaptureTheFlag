package org.pumatech.teams.Skynet;

import java.util.ArrayList;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public abstract class MovingPlayer extends AbstractPlayer {

	static ArrayList<Location> locationBlacklist = new ArrayList<Location>();
	static int blacklistSize = 24; // used to be 30
	
	public MovingPlayer(Location startLocation) {
		super(startLocation);
	}
	
}
