package ctf.gen;

import java.awt.Color;
import java.util.ArrayList;

import org.pumatech.ctf.Team;
import info.gridworld.grid.Location;

public class GenTeam extends Team{
	double[][][] io;
	public GenTeam(String name, Color color, double[][][] ioin) {
		super(name, color);
		ArrayList<AbstractGenPlayer> players=new ArrayList<AbstractGenPlayer>();
		io=ioin;
		players.add(new GenO(new Location(23, 30),io));
//		players.add(new GenO(new Location(24, 30),io));
//		players.add(new GenO(new Location(25, 30),io));
//		players.add(new GenO(new Location(26, 30),io));
//		players.add(new GenD(new Location(28, 30),io));
//		players.add(new GenD(new Location(27, 30),io));
//		players.add(new GenD(new Location(27, 31),io));
		players.add(new GenD(new Location(27, 29),io));
		for(AbstractGenPlayer p:players) {
			addPlayer(p);
		}
	}
	public double[][][] getIo() {
		return io;
	}
}
