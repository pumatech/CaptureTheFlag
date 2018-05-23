package ctf.teams.TestTeam;

import java.awt.Color;
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

public class FlexPlayer extends AbstractPlayer{
	private static int defenceCount=0;
	private boolean defence;
	public FlexPlayer(Location startLocation) {
		super(startLocation);
		if(defenceCount<2) {
			defence=true;
			this.setColor(Color.WHITE);
			defenceCount++;
		}else{
			defence=false;
			this.setColor(Color.GREEN);
		}	
	}
	
	public Location getMoveLocation() {
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
		
		Location flagloc=this.getTeam().getFlag().getLocation();
		double count=0;
		for(AbstractPlayer p:this.getTeam().getOpposingTeam().getPlayers()) {
			if(this.getTeam().onSide(p.getLocation())) {
				count++;
			}
		}
		double s=10;
		
		double daa = 2;
		double dab = 7;
		double dba = 2;
		double dca = 3;
		
		double oaa = 2;
		double oba = 20;
		double obb = 2;
		
		if(defence) {
			if(count>defenceCount) {
				defence=false;
				defenceCount--;
				this.setColor(Color.GREEN);
			}
		}else {
			if(count<defenceCount+1) {
				defence=true;
				defenceCount++;
				this.setColor(Color.WHITE);
			}
		}
		
		for (Feild feil : adja) {
			if (defence) {
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
						if (a == 0) {
							return this.getTeam().getOpposingTeam().getFlag().getLocation();
						}
						if (this.getTeam().nearFlag(feil.getLocation())) {
							a *= 0.01;
						}
					}
					
					b = 1 / b;
					b = (oba * b - oba / obb) / (2 * Math.pow(1 + Math.pow(oba * b - oba / obb, 2), 0.5)) + 0.5;
					if(this.getTeam().onSide(this.getLocation())) {
						b=1/b;
					}
					c = 1 / c;
					choices.put(feil.getLocation(), a * b * c);
				}
			}
		}
		Location key = Collections.max(choices.entrySet(), Map.Entry.comparingByValue()).getKey();
		return key;
	}
	public double distance(Location l1, Location l2) {
		return Math.sqrt(Math.pow(l1.getCol() - l2.getCol(), 2) + Math.pow(l1.getRow() - l2.getRow(), 2));
	}
}
