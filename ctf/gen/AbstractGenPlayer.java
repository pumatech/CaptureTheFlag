package ctf.gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.pumatech.ctf.AbstractPlayer;

import ctf.teams.ConnorTeam.Feild;
import ctf.teams.ConnorTeam.OuterThread;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public abstract class AbstractGenPlayer extends AbstractPlayer {
	double[][][] io;
	
	public AbstractGenPlayer(Location startLocation, double[][][] io) {
		super(startLocation);
		this.io=io;
	}

	public final Location getMoveLocation() {
		Grid<Actor> g = this.getGrid();
		Map<Location, Double> choices = new HashMap<Location, Double>();
		ArrayList<Feild> adja = new ArrayList<Feild>();
		ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<Location> locs = g.getEmptyAdjacentLocations(this.getLocation());
	if (locs.isEmpty()) {
			System.out.println("cant move");
			return this.getLocation();
		}
		for (Location l : locs) {
			Feild feil = new Feild(l, g, this.getTeam());
			adja.add(feil);
			Runnable r = new OuterThread(feil);
			Thread t = new Thread(r);
			threads.add(t);
			t.start();
		}
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.err.println("ran out of time");
			}
		}

		for (Feild feil : adja) {
			
			ArrayList<Double> ins=new ArrayList<Double>();
			int flag;
			int player;
			
			if (this instanceof GenD) {
				if (g.get(this.getTeam().getFlag().getLocation()) instanceof AbstractPlayer) {
					flag=1;
				}else {
					flag=1;
				}
				
				player=-1;
			} else {
				if (this.hasFlag()) {
					flag=1;
				}else{
					flag=0;
				}
				player=1;
			}
			
			ins.add( (double)(feil.get(flag) ));
			ins.add( (double)(feil.get(Math.abs(flag-1))));
			ins.add( (double)(feil.get(2)) );
			ins.add( (double)(feil.get(3)) );
			ins.add( (double)(feil.get(4)) );
			
			
			
			double[][] nodes=new double[io.length][io[0].length];
			for(int i=0;i<nodes[0].length;i++) {
				for(int j=0;j<ins.size();j++) {
					nodes[0][i]+=ins.get(j)*io[0][i][j];
				}
			}
			for(int n=1;n<nodes.length;n++) {
				for(int i=0;i<nodes[n].length;i++) {
					for(int j=0;j<nodes[n-1].length;j++) {
						double a=nodes[n-1][j];
						double b=io[n][i][j];
						double c=nodes[n-1][j]*io[n][i][j];
						nodes[n][i]+=c;
					}
				}
			}
			double o=0;
			for(int i=0;i<nodes[nodes.length-1].length;i++) {
				o+=nodes[nodes.length-1][i];
			}
			choices.put(feil.getLocation(), o);
		}
		Location key = Collections.max(choices.entrySet(), Map.Entry.comparingByValue()).getKey();

		if (this instanceof GenO) {
			this.setDirection(this.getLocation().getDirectionToward(key) - 45);
		}
		return key;
	}
}
