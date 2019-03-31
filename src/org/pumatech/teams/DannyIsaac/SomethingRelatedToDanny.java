package org.pumatech.teams.DannyIsaac;

import java.awt.Color;

import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Location;

public class SomethingRelatedToDanny extends Team {

	public SomethingRelatedToDanny() {
		this(new Color(255, 224, 123));
	}
	
	public SomethingRelatedToDanny(Color color) {
		this("Danny and Isaac", color);
	}
	
	public SomethingRelatedToDanny(String name, Color color) {
		super(name, color);
		addPlayer(new IsaacDefense(new Location(5, 30)));
		addPlayer(new DannyOffense(new Location(10, 30)));
		addPlayer(new DannyOffense(new Location(15, 30)));
		addPlayer(new DannyOffense(new Location(20, 30)));
		addPlayer(new DannyOffense(new Location(30, 30)));
		addPlayer(new DannyOffense(new Location(35, 30)));
		addPlayer(new DannyOffense(new Location(40, 30)));
		addPlayer(new IsaacDefense(new Location(45, 30)));
	}

}
