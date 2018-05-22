package ctf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class BasicO extends AbstractPlayer{
	private ArrayList<Location> checked = new ArrayList<Location>();
	public BasicO(Location startLocation) {
		super(startLocation);
	}
	public Location getMoveLocation() {
		Grid <Actor> g=this.getGrid();
		Location runTo;
		if (hasFlag()) {
			runTo = getTeam().getFlag().getLocation();
		}else {
			runTo = getTeam().getOpposingTeam().getFlag().getLocation();
		}
		ArrayList <Integer> rems=new ArrayList <Integer>();
		ArrayList <Location> posibs=g.getEmptyAdjacentLocations(this.getLocation());
		for (Location l:posibs) {
			ArrayList <Location> next=g.getOccupiedAdjacentLocations(l);
			if(distance(l,this.getTeam().getFlag().getLocation())<5) {
				rems.add(posibs.indexOf(l));
			}
			for (Location loc:next) {
				if(g.get(loc) instanceof AbstractPlayer && ((AbstractPlayer)g.get(loc)).getTeam()!=this.getTeam()) {
					rems.add(posibs.indexOf(l));
					break;
				}
			}
		}
		Collections.shuffle(posibs);
		ArrayList <Location> goods=new ArrayList <Location>();
		for(int i=0;i<posibs.size();i++) {
			if (!rems.contains(i)) {
				goods.add(posibs.get(i));
			}
		}
		if(goods.size()==0) {
			return this.getLocation();
		}
		int[] scores=new int[goods.size()];
		for (int i=0;i<goods.size();i++) {
			scores[i]= (Integer)path(goods.get(i),runTo);
		}
		Location next=goods.get(getIndexOfSmallest(scores));
		System.out.println(next);
		return next;
	}
	
	//stacked
	public int getIndexOfSmallest( int[] array ){
	  if ( array == null || array.length == 0 ) return -1; // null or empty
	  int largest = 0;
	  for ( int i = 1; i < array.length; i++ ){
	      if ( array[i] < array[largest] ) largest = i;
	  }
	  return largest; // position of the first largest found
	}
	
	
	public int path(Location l,Location d) {
		// return distance(l,d);
		int o = move(l, d);
		checked.clear();
		return o;
	}

	public int move(Location l, Location d) {
		Grid<Actor>g=this.getGrid();
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
		Grid<Actor>g=this.getGrid();
		ArrayList<Location> locs = g.getEmptyAdjacentLocations(l2);
		for (int i = locs.size() - 1; i >= 0; i--) {
			if (checked.indexOf(locs.get(i)) != -1) {
				locs.remove(i);
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
}