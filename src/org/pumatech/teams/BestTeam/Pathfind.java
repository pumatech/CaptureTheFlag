package org.pumatech.teams.BestTeam;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;
import org.pumatech.CTF2018.Flag;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Pathfind {
	private Location goal;
	private List<Location> path;
	private Grid<Actor> gr;
	private int last;
	private boolean backtrack;
//	private List<Location> bad;
//	private AbstractPlayer ac;
	

	public Pathfind(Location goal, Location start, AbstractPlayer ac) {
//		this.ac=ac;
		this.goal = goal;
		path = new ArrayList<Location>((int) distanceFromGoal(start, goal));
		path.add(start);
		last = 1;
		backtrack = false;
		Location temp = path.get(0);
		this.gr = ac.getGrid();
//		if(gr.get(ac.getTeam().getFlag().getLocation())instanceof Flag) {
//			bad=new ArrayList<Location>();
//			for(int i=0;i<7;i++) {
//				bad.add(new Location(goal.getCol()-3+i,goal.getRow()+3));
//				bad.add(new Location(goal.getCol()-3+i,goal.getRow()-3));
//			}
//			for(int i=0;i<5;i++) {
//				System.out.println("here?");
//				bad.add(new Location(goal.getCol()-3,goal.getRow()-2+i));
//				bad.add(new Location(goal.getCol()+3,goal.getRow()-2+i));
//			}
//			System.out.println("bad:"+bad.size());
//		}
		for (int i = 0; distanceFromGoal(path.get(i), goal) > Math.sqrt(2); i++) {
			if (i < 1) {
			} else if (!backtrack && distanceFromGoal(path.get(i), goal) > distanceFromGoal(path.get(i - 1), goal)) {
				backtrack = true;
				temp = path.get(i - 1);
			} else if (backtrack && distanceFromGoal(path.get(i), goal) < distanceFromGoal(temp, goal)) {
				backtrack = false;
				Location g = path.get(path.size() - 1);
				while (last < path.size()) {
					path.remove(last);
				}
				Pathfind p = new Pathfind(g, path.get(last - 1), ac);
				path.addAll(p.getPath());
				last = path.size();
				i = path.size() - 1;
			}
			path.add(findPath(path.get(i)));

		}
		path.add(goal);
	}

	public Location nextMove() {
		if (path.size() <= 1) {
			return path.get(0);
		}
		return path.get(1);
	}

	public List<Location> getPath() {
		return path;
	}

	private static double distanceFromGoal(Location a, Location b) {
		return (Math.sqrt(Math.pow(a.getCol() - b.getCol(), 2) + Math.pow(a.getRow() - b.getRow(), 2)));
	}

	private Location findPath(Location a) {
		List<Location> locs = gr.getEmptyAdjacentLocations(a);
//		for (int i = locs.size() - 1; i >= 0; i--) {
//			if(gr.get(ac.getTeam().getFlag().getLocation())instanceof Flag) {
//				for(int j=0;j<bad.size();j++) {
//					if(locs.get(i).compareTo(bad.get(j))==0) {
//						locs.remove(i);
//					}
//				}
//			}
//		}
		for	(int i = locs.size() - 1; i >= 0; i--) {
			if (backtrack) {
				for (int j = 0; j < path.size(); j++) {
					if (locs.get(i).compareTo(path.get(j)) == 0) {
						locs.remove(i);
						break;
					}
				}
			}
			
		}
		if (locs.size() <= 0) {
			return a;
		}

		Location best = locs.get(0);
		for (Location b : locs) {
			if (distanceFromGoal(best, goal) > distanceFromGoal(b, goal)) {
				best = b;
			}
		}
		return best;
	}
}
