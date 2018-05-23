package ctf.teams.TestTeam;

import java.awt.Color;
import java.util.ArrayList;

import org.pumatech.ctf.Team;

import info.gridworld.grid.Location;

public class TestTeam extends Team{
	public TestTeam() {
		this(Color.RED);
	}
	
	public TestTeam(Color color) {
		this("TestTeam", color);
	}
	
	public TestTeam(String name, Color color) {
		super(name, color);
		ArrayList<FlexPlayer> players=new ArrayList<FlexPlayer>();
//		players.add(new Offensionis(new Location(22, 30)));
//		players.add(new Offensionis(new Location(21, 30)));
//		players.add(new Offensionis(new Location(20, 30)));
		
		players.add(new FlexPlayer(new Location(23, 30)));
		players.add(new FlexPlayer(new Location(24, 30)));
		players.add(new FlexPlayer(new Location(25, 30)));
		players.add(new FlexPlayer(new Location(26, 30)));
		players.add(new FlexPlayer(new Location(28, 30)));
		players.add(new FlexPlayer(new Location(27, 30)));
		players.add(new FlexPlayer(new Location(27, 31)));
		players.add(new FlexPlayer(new Location(27, 29)));
		
		
		
		
		for(FlexPlayer p:players) {
			addPlayer(p);
		}
	}
}
