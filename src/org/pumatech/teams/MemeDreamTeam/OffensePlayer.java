package org.pumatech.teams.MemeDreamTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public class OffensePlayer extends AbstractPlayer {

	public static int type = 0;

	private String location;

	public ArrayList<Double> helpMe;

	public Location previousLoc;

	public int help;

	public ArrayList<Location> blackList;

	public ArrayList<Location> before;

	public int stuckCount;
	public OffensePlayer(Location startLocation) {
		super(startLocation);

		blackList = new ArrayList<Location>();
		before = new ArrayList<Location>();
		help = 0;
		stuckCount = 0;
	}



	public Location getMoveLocation() {
		
//		if(this.getTeam().getSide()==1 &&this.hasFlag()) {
//			avoidRocksBack(new Location(this.getLocation().getRow(),this.getLocation().getCol()+1));
//		}
		
		if (hasFlag())
			return avoidRocksBack(getTeam().getFlag().getLocation());
		
		for (AbstractPlayer p: this.getTeam().getOpposingTeam().getPlayers()) {
			if(Math.abs(nearestEnemy().getCol()-this.getLocation().getCol())<=3||Math.abs(nearestEnemy().getRow()-this.getLocation().getRow())<=3) {
				return avoidRocks(this.getLocation().getAdjacentLocation(-1*this.getLocation().getDirectionToward(p.getLocation())));
			}
		}
		
		 return avoidRocks(this.getTeam().getOpposingTeam().getFlag().getLocation());

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


	public Location avoidRocksBack(Location location) {

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
public Location avoidRocks(Location location) {
		
		

		ArrayList<Location> lst = this.getGrid().getEmptyAdjacentLocations(this.getLocation());

		TreeMap<Double, Location> map = new TreeMap<Double, Location>();

		for (Location l : lst) {
			map.put(Math.sqrt(Math.pow(l.getRow() - this.getTeam().getOpposingTeam().getFlag().getLocation().getRow(), 2)
					+ Math.pow(l.getCol() - this.getTeam().getOpposingTeam().getFlag().getLocation().getCol(), 2)), l);
		}

		if (blackList.size() > 0) {
			for (int i = 0; i<lst.size();i++) {
				if (blackList.contains(lst.get(i))) {
					lst.remove(i);
				}
			}

		}
		// put in thing to return first loc
		if (isStuck() &&Math.sqrt(Math.pow(this.getLocation().getRow() - this.getTeam().getOpposingTeam().getFlag().getLocation().getRow(), 2)
				+ Math.pow(this.getLocation().getCol() - this.getTeam().getOpposingTeam().getFlag().getLocation().getCol(), 2))>5.0 ) {
			System.out.println("HELP ME");
			blackList.add(this.getLocation());
			

		}
		return map.firstEntry().getValue();

	}
	}


