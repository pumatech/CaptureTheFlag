package org.pumatech.teams.ConnorTeam;

import java.awt.Color;
import java.util.ArrayList;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class Quadrigis extends Team{
	public Quadrigis() {
		this(Color.BLUE);
	}
	
	public Quadrigis(Color color) {
		this("Quadrigis", color);
	}
	
	public Quadrigis(String name, Color color) {
		super(name, color);
		ArrayList<AbstractFriendlyPlayer> players=new ArrayList<AbstractFriendlyPlayer>();
		int x=40;
		int y=27;
		players.add(new Offensionis(new Location(y-4, x)));
		players.add(new Offensionis(new Location(y-3, x)));
		players.add(new Offensionis(new Location(y-2, x)));
		players.add(new Offensionis(new Location(y-1, x)));
		players.add(new Defensionis(new Location(y, x)));
		players.add(new Defensionis(new Location(y, x+1)));
		players.add(new Defensionis(new Location(y, x-1)));
		players.add(new Defensionis(new Location(y+1, x)));
		
		
		for(AbstractFriendlyPlayer p:players) {
			if (p instanceof Offensionis) {
				p.setDirection(-45);
			}
			addPlayer(p);
		}
	}
}
