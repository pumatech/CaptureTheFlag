package ctf.teams.ConnorTeam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public abstract class AbstractFriendlyPlayer extends AbstractPlayer {

	public AbstractFriendlyPlayer(Location startLocation) {
		super(startLocation);

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
				// TODO Auto-generated catch block
				System.err.println("ran out of time");
			}
		}
		
		double daa = 2;
		double dab = 7;
		double dba = 2;
		double dca = 3;
		
		double oaa = 2;
		double oab = 1;
		double oba = 20;
		double obb = 2;
		double obc = 0.3;

		for (Feild feil : adja) {
			if (this instanceof Defensionis) {
				if (g.get(this.getTeam().getFlag().getLocation()) instanceof AbstractPlayer) {
					double a = (double) (feil.get(2));
					if (feil.distance(feil.getLocation(), this.getTeam().getFlag().getLocation()) > feil
							.distance(this.getLocation(), this.getTeam().getFlag().getLocation())) {
						a *= 0.001;
					}
					choices.put(feil.getLocation(), a);
				} else {
					double a = (double) (feil.get(1));
					double b = (double) (feil.get(2));
					double c = (double) (feil.get(3));
					
					a=1./a;
					a=Math.pow(daa, a-dab)+1;
					a=1./a;
					
					b = Math.pow(b, dba);
					
					c=1./c;
					c=-Math.pow(dca,-c)+1;
					
					choices.put(feil.getLocation(), a * b * c);
				}
			} else {
				if (this.hasFlag()) {
					double a = (double) (feil.get(1));
					double b = (double) (feil.get(2));
					if (feil.distance(feil.getLocation(), this.getTeam().getFlag().getLocation()) > feil
							.distance(this.getLocation(), this.getTeam().getFlag().getLocation())) {
						a *= 0.5;
					}
					a = Math.pow(a, oaa);
					b = 1 / b;
					b = (oba * b - oba / obb) / (2 * Math.pow(1 + Math.pow(oba * b - oba / obb, 2), 0.5)) + 0.5;
					choices.put(feil.getLocation(), a * b);
				}else {
					double a = (double) (feil.get(0));
					double b = (double) (feil.get(2));
					double c = (double) (feil.get(4));
					
					if(g.get(this.getTeam().getOpposingTeam().getFlag().getLocation()) instanceof AbstractPlayer) {
						a=1;
					}else {
						if (this.getTeam().nearFlag(feil.getLocation())) {
							a *= 0.01;
						}
					}
					
					if(this.getTeam().onSide(this.getLocation())) {
						b = 1 / b;
						b = (oba * b - oba * 10 / obb) / (2 * Math.pow(1 + Math.pow(oba * b - oba  * 10 / obb, 2), 0.5)) + 0.5;
						b = 1 / b;
					}else {
						b = 1 / b;
						b = (oba * b - oba / obb) / (2 * Math.pow(1 + Math.pow(oba * b - oba / obb, 2), 0.5)) + 0.5;
					}
					
					c = 1 / c;
					
					choices.put(feil.getLocation(), a * b * c);
				}
			}
		}
		Location key = Collections.max(choices.entrySet(), Map.Entry.comparingByValue()).getKey();

		if (this instanceof Offensionis) {
			this.setDirection(this.getLocation().getDirectionToward(key) - 45);
		}
		return key;
	}
}
