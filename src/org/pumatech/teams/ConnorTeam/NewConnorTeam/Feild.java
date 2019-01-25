
package org.pumatech.teams.ConnorTeam.NewConnorTeam;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import org.pumatech.newCTF.AbstractPlayer;
import org.pumatech.newCTF.Team;

import java.util.ArrayList;
import java.util.HashMap;

public class Feild {
	private Location l;
	private ArrayList<Double> main = new ArrayList<Double>(); // 0:enemy flag 1:my flag 2:enemy players 3:my Defence
																// 4:my Offence...
	private ArrayList<Location> checked = new ArrayList<Location>();
	private ArrayList<Location> open = new ArrayList<Location>();
	public Grid<Actor> g;
	public Team t;

	public Feild(Location ln, Grid<Actor> b, Team team) {
		l = ln;
		g = b;
		t = team;
		main.add(1.);
		main.add(1.);
		main.add(1.);
		main.add(1.);
		main.add(1.); // change when more are added
	}

	public ArrayList<Double> getVal() {
		return main;
	}

	public void calc() {
		
		try {
			main.set(0, (double) 1. / path(t.getOpposingTeam().getFlag().getLocation()));
			main.set(1, (double) 1. / path(t.getFlag().getLocation()));
			for (Location la : g.getOccupiedLocations()) {
				Actor a = g.get(la);
				if (a instanceof AbstractPlayer) {
					double d = distance(l, a.getLocation());
					double pat;
					if(d>=10) {
						pat=25;
					}else {
						pat=path(la);
					}
					if(pat!=0) {
						double sco = 1. / pat;
						if (((AbstractPlayer) a).getTeam() == t) {
							if (a instanceof Defensionis) {
								main.set(3, main.get(3) + sco);
							} else if (a instanceof Offensionis) {
								main.set(4, main.get(4) + sco);
							} // change when more are added
						} else {
							main.set(2, main.get(2) + sco);
						}
					}
					
				}
			}
		} catch (Exception e) {
			System.out.println("Ran out of time " + e);
		}
	}
	public int path(Location d) {
		// return distance(l,d);
		open.add(this.getLocation());
		int o = move(l, d);
		checked.clear();
		open.clear();
		return o;
	}

	public int move(Location l, Location d) {
		if (g.getValidAdjacentLocations(l).contains(d)) {
			return 1;
		} else {
			for (Location l2 : closest(l, d)) {
				int r = move(l2, d);
				if (r != -1) {
					return r + 1;
				}
			}
		}
		return -1;
	}

	public ArrayList<Location> closest(Location l2, Location d) {
		ArrayList<Location> locs = g.getEmptyAdjacentLocations(l2);
		for (int i = locs.size() - 1; i >= 0; i--) {
			if (checked.contains(locs.get(i))||open.contains(locs.get(i))) {
				locs.remove(i);
			}else{
				open.add(locs.get(i));
			}
		}
		checked.addAll(locs);
		ArrayList<Double> score = new ArrayList<Double>();
		int n = locs.size();
		for (int i = 0; i < n; ++i) {
			score.add((Double) distance(locs.get(i), d));
		}
		for (int i = 1; i < n; ++i) {
			Double key = score.get(i);
			Location val = locs.get(i);
			int j = i - 1;
			while (j >= 0 && score.get(j) > key) {
				score.set(j + 1, score.get(j));
				locs.set(j + 1, locs.get(j));
				j = j - 1;
			}
			score.set(j + 1, key);
			locs.set(j + 1, val);
		}
		return locs;
	}

	public double distance(Location l1, Location l2) {
		return Math.sqrt(Math.pow(l1.getCol() - l2.getCol(), 2) + Math.pow(l1.getRow() - l2.getRow(), 2));
	}

	public int aStar(Location goal) {
		try {
			ArrayList<Location> closedSet = new ArrayList<Location>();
			ArrayList<Location> openSet = new ArrayList<Location>();
			HashMap<Location, Integer> gScore = new HashMap<Location, Integer>();
			HashMap<Location, Integer> fScore = new HashMap<Location, Integer>();

			openSet.add(l);
			gScore.put(l, 0);
			fScore.put(l, (int) distance(l, goal));

			while (!openSet.isEmpty()) {
				Location current;
				current = openSet.get(0);

				if (g.getValidAdjacentLocations(current).contains(goal)) {
					return gScore.get(current)+1;
				}
				openSet.remove(0);

				closedSet.add(current);

				for (Location neighbor : g.getEmptyAdjacentLocations(current)) {
					if (closedSet.contains(neighbor)) {
						continue;
					}
					int tentative_gScore = gScore.get(current) + 1;
					if (gScore.containsKey(neighbor) && tentative_gScore >= gScore.get(neighbor)) {
						continue;
					}

					gScore.put(neighbor, tentative_gScore);
					fScore.put(neighbor, tentative_gScore + (int) distance(neighbor, goal));
					
					if (openSet.contains(neighbor)) {
						openSet.remove(openSet.indexOf(neighbor));
					}
					int key = fScore.get(neighbor);
					int i = 0;
					while (i < openSet.size()) {
						if (fScore.get(openSet.get(i)) > key) {
							break;
						}
						i++;
					}
					openSet.add(i, neighbor);
				}

			}
			System.out.println("failure");
			return -1;
		} catch (Exception e) {
			System.out.println("hey" + e);
		}
		return -1;
	}

	public double get(int i) {
		return main.get(i);
	}

	public Location getLocation() {
		return l;
	}

}