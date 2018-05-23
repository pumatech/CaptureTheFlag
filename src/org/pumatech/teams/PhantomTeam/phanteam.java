package org.pumatech.teams.PhantomTeam;

import java.awt.Color;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class phanteam extends Team {

	public phanteam() {
		this(Color.MAGENTA);
	}

	public phanteam(Color color) {
		this("Phantom Knights", color);
	}

	public phanteam(String name, Color color) {
		super(name, color);
		addPlayer(new mezzanotte(new Location(15, 20)));
		addPlayer(new aStar(new Location(25, 25)));
		addPlayer(new mezzanotte(new Location(35, 20)));

		addPlayer(new aStar(new Location(15, 35)));
		addPlayer(new mezzanotte(new Location(20, 32)));
		addPlayer(new aStar(new Location(25, 30)));
		addPlayer(new mezzanotte(new Location(30, 32)));
		addPlayer(new aStar(new Location(35, 35)));
	}
	// Arpita make sure to drag over the player gifs as well.
	// aStar keeps getting stuck on rocks, maybe only put it in the first three
	// slots bc mine play good D.
}