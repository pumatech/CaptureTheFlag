package org.pumatech.teams.MemeDreamTeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

public class DefensePlayer extends AbstractPlayer {

	public static int type = 0;

	private String location;

	public ArrayList<Double> helpMe;

	public Location previousLoc;

	public int help;

	public ArrayList<Location> blackList;

	public ArrayList<Location> before;

	public int stuckCount;
	public DefensePlayer(Location startLocation) {
		super(startLocation);
		if (type == 0) {
			location = "top";

		} else if (type == 1) {
			location = "topmid";
		} else if (type == 2) {
			location = "botmid";
		} else {
			location = "bot";
		}
		type++;
		blackList = new ArrayList<Location>();
		before = new ArrayList<Location>();
		help = 0;
		stuckCount = 0;
	}

	public Location getHome() {
		if (location.equals("top")) {
			return new Location(this.getTeam().getFlag().getLocation().getRow() + 4,
					this.getTeam().getFlag().getLocation().getCol());
		} else if (location.equals("botmid")) {
			return new Location(this.getTeam().getFlag().getLocation().getRow() - 4,
					this.getTeam().getFlag().getLocation().getCol() + 4);
		} else if (location.equals("topmid")) {
			return new Location(this.getTeam().getFlag().getLocation().getRow() + 4,
					this.getTeam().getFlag().getLocation().getCol() + 4);
		} else {
			return new Location(this.getTeam().getFlag().getLocation().getRow() - 4,
					this.getTeam().getFlag().getLocation().getCol());
		}
	}

	public Location getMoveLocation() {

		for (AbstractPlayer p : this.getTeam().getOpposingTeam().getPlayers()) {
			if (p.hasFlag() || p.getTeam().nearFlag(p.getLocation())) {
				before.add(this.getLocation());
				
				if(this.getTeam().getSide() ==0) {
					return avoidRocks(new Location(p.getLocation().getRow(),p.getLocation().getCol()+1));
				}
				else {
				return avoidRocks(new Location(p.getLocation().getRow(),p.getLocation().getCol()-1));
			}
			}
		}
		if (Math.abs(this.getLocation().getRow() - this.nearestEnemy().getRow()) <= 3
				&& Math.abs(this.getLocation().getCol() - this.nearestEnemy().getCol()) <= 3) {
			if (Math.abs(this.getLocation().getRow() - this.getTeam().getFlag().getLocation().getRow()) <= 8
					&& Math.abs(this.getLocation().getCol() - this.getTeam().getFlag().getLocation().getCol()) <= 8) {

				before.add(this.getLocation());
				return this.nearestEnemy();
			}
		}
		if (!this.getTeam().nearFlag(this.getLocation())) {
			if (location.equals("top")) {

				before.add(this.getLocation()); // return
				// avoidRocks(this.getTeam().getFlag().getLocation().getAdjacentLocation(Location.NORTH)
				// .getAdjacentLocation(Location.NORTH).getAdjacentLocation(Location.NORTH));
				return avoidRocks(this.getHome());
			} else if (location.equals("mid")) {
				// return
				// this.getTeam().getFlag().getLocation().getAdjacentLocation(this.getTeam().getFlag().getLocation()
				// .getDirectionToward(this.getTeam().getOpposingTeam().getFlag().getLocation()));

				before.add(this.getLocation()); // return
				// avoidRocks(this.getTeam().getFlag().getLocation().getAdjacentLocation(Location.EAST)
				// .getAdjacentLocation(Location.EAST).getAdjacentLocation(Location.EAST));
				return avoidRocks(this.getHome());
			} else {

				before.add(this.getLocation()); // return
				// avoidRocks(this.getTeam().getFlag().getLocation().getAdjacentLocation(Location.SOUTH)
				// .getAdjacentLocation(Location.SOUTH).getAdjacentLocation(Location.SOUTH));
				return avoidRocks(this.getHome());
			}
		} else {

			before.add(this.getLocation());
			return this.getLocation().getAdjacentLocation(
					-1 * this.getTeam().getFlag().getLocation().getDirectionToward(this.getLocation()));

		}
	}

	public String toString() {
		// return nearestEnemy().toString() + (Math.abs(this.getLocation().getRow() -
		// this.nearestEnemy().getRow()) <= 4
		// && Math.abs(this.getLocation().getCol() - this.nearestEnemy().getCol()) <=
		// 4);
		ArrayList<String> str = new ArrayList<String>();
		for (Location l : blackList) {
			str.add(l.toString());
		}
		return str.toString();

	}

	public Location nearestEnemy() {
		List<AbstractPlayer> lst = this.getTeam().getOpposingTeam().getPlayers();
		int difR = 100;
		int difC = 100;
		int index = 0;
		for (AbstractPlayer p : lst) {
			if (Math.abs(this.getLocation().getCol() - p.getLocation().getCol()) < difC
					&& Math.abs(this.getLocation().getRow() - p.getLocation().getRow()) < difR) {
				difR = Math.abs(this.getLocation().getRow() - p.getLocation().getRow());
				difC = Math.abs(this.getLocation().getCol() - p.getLocation().getCol());
				index = lst.indexOf(p);
			}

		}
		return lst.get(index).getLocation();

	}

	public int getBlackList() {
		return this.blackList.size();
	}

	public boolean isStuck() {
		if (!before.isEmpty() && before.size()>3) {
			if (before.get(before.size() - 1).equals(before.get(before.size() - 3))) {
				return true;
			}
			else return false;
		} else
			return false;

	}


	public Location avoidRocks(Location location) {

		ArrayList<Location> lst = this.getGrid().getEmptyAdjacentLocations(this.getLocation());

		TreeMap<Double, Location> map = new TreeMap<Double, Location>();

		for (Location l : lst) {
			map.put(Math.sqrt(Math.pow(l.getRow() - this.getTeam().getFlag().getLocation().getRow(), 2)
					+ Math.pow(l.getCol() - this.getTeam().getFlag().getLocation().getCol(), 2)), l);
		}

		if (blackList.size() > 0) {
			for (int i = 0; i<lst.size();i++) {
				if (blackList.contains(lst.get(i))) {
					lst.remove(i);
				}
			}

		}
		// put in thing to return first loc
		if (isStuck() &&Math.sqrt(Math.pow(this.getLocation().getRow() - this.getTeam().getFlag().getLocation().getRow(), 2)
				+ Math.pow(this.getLocation().getCol() - this.getTeam().getFlag().getLocation().getCol(), 2))>5.0 ) {

			blackList.add(this.getLocation());
			

		}
		return map.firstEntry().getValue();

	}

}
