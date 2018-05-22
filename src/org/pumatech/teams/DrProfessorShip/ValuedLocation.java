package org.pumatech.teams.DrProfessorShip;

public class ValuedLocation {
	private int x;
	private int y;
	private int v;
	
	public ValuedLocation(int xLoc, int yLoc, int val) {
		x = xLoc;
		y = yLoc;
		v = val;
	};
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getV() {
		return v;
	}
}
