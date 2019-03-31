package org.pumatech.teams.DrProfessorShip;

import java.awt.Color;

import org.pumatech.CTF2018.Team;

import info.gridworld.grid.Location;

public class DrProfessorShipTheTeam extends Team{
	public static boolean lickthesoap = true;
	public static boolean emotable = false;
	public DrProfessorShipTheTeam() {
		this(new Color(0x00cc00));
	}
	
	public DrProfessorShipTheTeam(Color color) {
		this("DrProfessorShip", color);
	}
	
	public DrProfessorShipTheTeam(String name, Color color) {
		super(name, color);
		addPlayer(new GreenBoi(new Location(5, 30)));
		addPlayer(new GreenBoi(new Location(45, 30)));
		addPlayer(new MulletMan(new Location(15, 30)));
		addPlayer(new MulletMan(new Location(20, 30)));
		addPlayer(new Pinky(new Location(30, 30)));
		addPlayer(new Pinky(new Location(35, 30)));
		addPlayer(new RandoDude(new Location(40, 30)));
		addPlayer(new RandoDude(new Location(10, 30)));
//		
//		addPlayer(new Player(new Location(5, 30)));
//		addPlayer(new Player(new Location(10, 30))); 10, 30
//		addPlayer(new Player(new Location(15, 30)));
//		addPlayer(new Player(new Location(20, 30)));
//		addPlayer(new Player(new Location(30, 30)));
//		addPlayer(new Player(new Location(35, 30)));
//		addPlayer(new Player(new Location(40, 30)));
//		addPlayer(new Player(new Location(45, 30)));
	}

}
