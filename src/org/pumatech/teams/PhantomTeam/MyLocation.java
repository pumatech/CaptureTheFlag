package org.pumatech.teams.PhantomTeam;

import info.gridworld.grid.Location;

public class MyLocation{
public Location standard;
	public MyLocation(Location x) {
		standard = x;
	}
	public Location getLocation() {
		return standard;
	}
	
	public void setLocation(Location x) {
		standard = x;
	}
	private int gCost;
	private int hCost;
	private MyLocation parent;
	public int fCost() {
		return gCost + hCost;
	}

	public int getGCost() {
		return gCost;
	}
	public int getHCost() {
		return hCost;
	}
	public void setGCost(int gCost) {
		this.gCost = gCost;
	}
	public void setHCost(int hCost) {
		this.hCost = hCost;
	}
	public MyLocation getParent() {
		return parent;
	}
	public void setParent(MyLocation parent) {
		this.parent = parent;
	}


}
