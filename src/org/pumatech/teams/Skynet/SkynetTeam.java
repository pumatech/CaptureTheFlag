package org.pumatech.teams.Skynet;

import java.awt.Color;

import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Location;

public class SkynetTeam extends Team{
	public SkynetTeam() {
		// should be this("Skynet", null); but Hettsy hasn't patched it yet
		this("Skynet", Color.RED);
	}
	
	public SkynetTeam(String name) {
		// should be this(name, null); but Hettsy hasn't patched it yet
		this(name, Color.RED);
	}
	
	public SkynetTeam(Color color) {
		this("Skynet", color);
	}
	
	public SkynetTeam(String name, Color color) {
		super(name, color);
		addPlayer(new Moto(new Location(5, 30)));
		addPlayer(new Moto(new Location(10, 30)));
		//addPlayer(new DistractMoto(new Location(10, 30), 0));
		addPlayer(new T1K(new Location(15, 30)));
		addPlayer(new SkynetDupe(new Location(20, 30)));
		addPlayer(new T850(new Location(30, 30)));
		addPlayer(new T1K(new Location(35, 30)));
		//addPlayer(new DistractMoto(new Location(40, 30), 49));
		addPlayer(new Moto(new Location(40, 30)));
		addPlayer(new Moto(new Location(45, 30)));
	}
}
