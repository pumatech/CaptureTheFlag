package org.pumatech.teams.DrProfessorShip;

import java.util.ArrayList;

import info.gridworld.grid.Location;

public class RandoDude extends Player {

	public RandoDude(Location startLocation) {
		super(startLocation);
		// TODO Auto-generated constructor stub	
	}
/**
 * finds weighted random location to move to. 
 */
	public Location getMoveLocation() {
		boolean gotit = hasFlag();
		Location flag;
		if (gotit == false) {
			flag = flagIt();
		} else {
			flag = getWallSpot();
		}

		Location toFlag = inBet(flag);
		ArrayList<Location> locs = new ArrayList<Location>();
		Location here = this.getLocation();
		int row = here.getRow();
		int col = here.getCol();
		int l = account();
		Location ri = new Location(row, col + l);
		Location br = new Location(row + l, col + l);
		Location bo = new Location(row + l, col);
		Location bl = new Location(row + l, col - l);
		Location lf = new Location(row, col - l);
		Location tl = new Location(row - l, col - l);
		Location to = new Location(row - l, col);
		Location tr = new Location(row - l, col + l);
		Location one = ri;
		Location two = br;
		Location thr = tr;
		Location fou = bo;
		Location fiv = to;
		Location six = bl;
		Location sev = tl;
		Location eig = lf;
		Location cur;

		cur = toFlag;
		for (int i = 0; i < 150; i++) {
			locs.add(cur);
		}

		cur = one;
		for (int i = 0; i < 20; i++) {
			locs.add(cur);
		}

		cur = two;
		for (int i = 0; i < 10; i++) {
			locs.add(cur);
		}

		cur = thr;
		for (int i = 0; i < 10; i++) {
			locs.add(cur);
		}

		cur = fou;
		for (int i = 0; i < 5; i++) {
			locs.add(cur);
		}

		cur = fiv;
		for (int i = 0; i < 5; i++) {
			locs.add(cur);
		}

		cur = six;
		for (int i = 0; i < 1; i++) {
			locs.add(cur);
		}

		cur = sev;
		for (int i = 0; i < 1; i++) {
			locs.add(cur);
		}

		cur = eig;
		for (int i = 0; i < 1; i++) {
			locs.add(cur);
		}

		int len = locs.size();
		int ind = (int) (Math.random() * len);
		Location success = fix(locs.get(ind));
		return success;
	}
}
