package org.pumatech.teams.ArpitaMakenna;

import java.awt.Color;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class SampleTeam2 extends Team {

	public SampleTeam2() {
		this(Color.DARK_GRAY);
	}
	
	public SampleTeam2(Color color) {
		this("Sample Team", color);
	}
	 
	public SampleTeam2(String name, Color color) {
		super(name, color);
		addPlayer(new aStar(new Location(20, 30)));
		
		 
	} 

}
