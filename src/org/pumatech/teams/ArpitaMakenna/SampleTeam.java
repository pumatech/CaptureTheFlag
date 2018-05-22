package org.pumatech.teams.ArpitaMakenna;

import java.awt.Color;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class SampleTeam extends Team {

	public SampleTeam() {
		this(Color.CYAN);
	}

	public SampleTeam(Color color) {
		this("ArpitaMakenna", color);
	}

	public SampleTeam(String name, Color color) {
		super(name, color);
		addPlayer(new aStar(new Location(15, 20)));
		addPlayer(new amami(new Location(25, 25)));
		addPlayer(new amami(new Location(35, 20)));
		addPlayer(new aStar(new Location(15, 20)));
		addPlayer(new aStar(new Location(25, 25)));
		addPlayer(new aStar(new Location(35, 20)));
		addPlayer(new amami(new Location(15, 35)));
		addPlayer(new amami(new Location(20, 32)));
	}

}