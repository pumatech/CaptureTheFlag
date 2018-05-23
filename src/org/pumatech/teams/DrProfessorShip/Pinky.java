package org.pumatech.teams.DrProfessorShip;

import java.util.ArrayList;

import info.gridworld.grid.Location;

public class Pinky extends Player {
	public int ind;
	boolean fore = true;
	public static int ident;
	public static int[] boy = { 0, 0 };
	int l;
	public boolean update = false;
	int r = 5;
	int room = 5;
	public static Location aTarget;
	public static Location bTarget;
	Location lastSpot;
	Location lastishSpot;
	private ArrayList<Location> thed = new ArrayList<Location>();

	public Pinky(Location startLocation) {
		super(startLocation);
		if (boy[0] == 0) {
			ind = 1;
			boy[0] = 1;
			ident = 1;
		} else if(boy[1]==0){
			ind = 3;
			boy[1] = 1;
			ident = 2;
			
		}
		thed.add(new Location(0,0));
		thed.add(new Location(0,0));
		thed.add(new Location(0,0));
		// TODO Auto-generated constructor stub
	}

	public Location getMoveLocation() {
		Location there;
		boolean baddies = nearme();
		if (!baddies) {
			there = fix(nobad());
		} else {
			if (ident == 1) {
				there = target();
				aTarget = there;
			} else {
				there = sectarget();
				bTarget = there;
			}
			there = getOnOut(there);
			runit(there);
		}
		takeCare(there);
		return isThisGood(there);
	}
	/**
	 * updates a list of select locations
	 * @param loca
	 */
	public void takeCare(Location loca) {
		thed.remove(2);
		thed.add(0, loca);
	}
	/**
	 * determines if a Pinky is trapped. 
	 * @return
	 */
	public boolean saftyRed() {
		boolean truth = thed.get(0).equals(thed.get(1))&&thed.get(0).equals(thed.get(2));
//		if ((this.getLocation().equals(thed.get(0)))&&(this.getLocation().equals(thed.get(1)))&&(this.getLocation().equals(thed.get(2)))) {
//			return true;
//		}
//		return false;
		return truth;
	}
	/**
	 * Untraps a pinky if trapped
	 * @param thisplace
	 * @return
	 */
	public Location isThisGood(Location thisplace) {
		if(saftyRed()) {
			int c = (int)(100*Math.random());
			int r = (int)(50*Math.random());
			Location shm = new Location(r,c);
			return inBet(shm);
		} else {
			return thisplace;
		}
	}
	/**
	 * a primairy target for defense
	 */
	public Location target() {
		boolean worthit = nearme();
		Location prior = nobad();
		int sr = 100;
		int sc = 100;
		int sd = 100;
		int st = 10000;
		if(worthit) {
			for(Location ths:badloc) {
				int tr = ths.getRow();
				int tc = ths.getCol();
				int mr = this.getLocation().getRow();
				int mc = this.getLocation().getCol();
				int r = Math.abs(tr-mr);
				int c = Math.abs(tc-mc);
				double diag = Math.sqrt((r*r)+(c*c));
				int d = (int)diag;
				int t = r+c+d;
				if (t<st) {
					st = t;
					prior = ths;
				}
			}
		}
		return prior;
	}
	/**
	 * a secondairy target for defense
	 */
	public Location sectarget() {
		ArrayList<Location> jope = badloc;
		jope.remove(target());
		boolean worthit = nearme();
		Location prior = nobad();
		int sr = 100;
		int sc = 100;
		int sd = 100;
		int st = 10000;
		if(worthit) {
			for(Location ths:jope) {
				int tr = ths.getRow();
				int tc = ths.getCol();
				int mr = this.getLocation().getRow();
				int mc = this.getLocation().getCol();
				int r = Math.abs(tr-mr);
				int c = Math.abs(tc-mc);
				double diag = Math.sqrt((r*r)+(c*c));
				int d = (int)diag;
				int t = r+c+d;
				if (t<st) {
					st = t;
					prior = ths;
				}
			}
		}
		if (prior.equals(aTarget)) {
			prior = nobad();
		}
		return prior;
	}
	/** 
	 * the optimal location if there are no enemies near by. 
	 * @return
	 */
	public Location nobad() {
		if (this.getLocation().equals(lastishSpot)) {
			update = true; 
		}
		Location theSpot;
		if (ind == 1) {
			theSpot = destinationA();
			if (update) {
				if (fore == true) {
					ind++;
				} else {
					fore = true;
					ind++;
				}
			}
		} else if (ind == 2) {
			theSpot = destinationB();
			if (update) {
				if (fore == true) {
					ind++;
				} else {
					ind--;
				}
			}
		} else if (ind == 3) {
			theSpot = destinationC();
			if (update) {
				if (fore == true) {
					ind++;
				} else {
					ind--;
				}
			}
		} else {
			theSpot = destinationD();
			if (update) {
				fore = false;
				ind--;
			}
		}
		l = accounted();
		r = room*l;
		Location gol = fix(theSpot);
		if (update) {
			update = false;
		}
		
		lastSpot = theSpot;
		return gol;
	}

	// public void runit(Location loc) {
	// super.runit(loc);
	// if (ind<4) {
	// ind++;
	// } else {
	// ind = 1;
	// }
	// }
	/**
	 * the first destination in a security loop. 
	 * @return
	 */
	public Location destinationA() {
		Location flag = ourFlag();
		int row = flag.getRow();
		int col = flag.getCol();
		Location there = new Location(row - r, col);
		lastishSpot = there;
		return inBet(there);
	}
	/**
	 * the second destination in a security loop. 
	 * @return
	 */
	public Location destinationB() {
		Location flag = ourFlag();
		int row = flag.getRow();
		int col = flag.getCol();
		Location there = new Location(row - r, col + r);
		lastishSpot = there;
		return inBet(there);
	}
	/**
	 * the third destination in a security loop. 
	 * @return
	 */
	public Location destinationC() {
		Location flag = ourFlag();
		int row = flag.getRow();
		int col = flag.getCol();
		Location there = new Location(row + r, col + r);
		lastishSpot = there;
		return inBet(there);
	}
	/**
	 * the fourth destination in a security loop. 
	 * @return
	 */
	public Location destinationD() {
		Location flag = ourFlag();
		int row = flag.getRow();
		int col = flag.getCol();
		Location there = new Location(row + r, col);
		lastishSpot = there;
		return inBet(there);
	}
}
