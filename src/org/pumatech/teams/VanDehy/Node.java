package org.pumatech.teams.VanDehy;

import info.gridworld.grid.Location;

public class Node {
	private int row;
	private int col;
	private Node parent;
	private int g;
	private int h;
	private int f;

	public Node(int row, int col) {
		this.row = row;
		this.col = col;
		parent = null;
	}
	public Node(){
		
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getF(){
		return f;
	}
	public void setF(int f){
		this.f=f;
	}
	public Location getLocation(){
		return new Location(this.getRow(),this.getCol());
	}
	public int findH(Node end){//distance from end node
		return Integer.max(Math.abs(end.getCol()-getCol()), Math.abs(end.getRow() - getRow()));
	}
	public int findF(){//total cost of H + G
		return getH()+getG();
	}
	public String toString(){
		return "("+ getRow() + ", " + getCol() + ")";
	}
	public void setLocation(Location loc){
		this.setCol(loc.getCol());
		this.setRow(loc.getRow());
	}
	public boolean equals(Node o){
		return this.getLocation().equals(o.getLocation());
	}
	public boolean equals(Object o){
		if(o instanceof Node == false){
			return false;
		}
		else {
			return equals((Node) o);
		}
	}
}
