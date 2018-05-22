package org.pumatech.teams.DrProfessorShip;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class MulletMan extends Player {
	private int[][] pathMap;
	private int[][] reverse;

	public MulletMan(Location startLocation) {
		super(startLocation);
		pathMap = new int[50][100];
		reverse = new int[50][100];
	}
	
	public void mapIt() {
		//Clears out the path map for use
		pathMap = new int[50][100];
		Location flag = super.flagIt();
		List<AbstractPlayer> opposing = getTeam().getOpposingTeam().getPlayers();
		ArrayList<Location> rocks = getGrid().getOccupiedLocations();
		Grid<Actor> g = getGrid();
		ArrayList<Location> contains = g.getOccupiedLocations();
		//Marks the locations of the rocks as -1 in the path map
		for(int i = 0; i<contains.size(); i++) {
			Location l = contains.get(i);
			if(g.get(l) instanceof Rock) {
				pathMap[l.getRow()][l.getCol()] = -1;
			}
		}
		//This array list contains objects that populate the path map
		ArrayList<ValuedLocation> locs = new ArrayList<ValuedLocation>();
		//Adds the first object at the location of the enemy flag
		locs.add(new ValuedLocation(flag.getCol(),flag.getRow(),1));
		for(int i = 0; i<opposing.size(); i++) {
			Location temp = opposing.get(i).getLocation();
			pathMap[temp.getRow()][temp.getCol()] = -1;
		}
		while(locs.size()>0) {
			//Creates a temporary array list to store values
			ArrayList<ValuedLocation> tempLocs = new ArrayList<ValuedLocation>();
			//Creates a new object based off of spots that are available in the grid
			for(int i = 0; i<locs.size(); i++) {
				ValuedLocation t = locs.get(i);
				int tempX = t.getX();
				int tempY = t.getY();
				int tempV = t.getV();
				if(tempX>0) {
					if(pathMap[tempY][tempX-1]==0) {
						tempLocs.add(new ValuedLocation(tempX-1,tempY,tempV+1));
						pathMap[tempY][tempX-1]=tempV+1;
					}
				}
				if(tempX<99) {
					if(pathMap[tempY][tempX+1]==0) {
						tempLocs.add(new ValuedLocation(tempX+1,tempY,tempV+1));
						pathMap[tempY][tempX+1]=tempV+1;
					}
				}
				if(tempY>0) {
					if(pathMap[tempY-1][tempX]==0) {
						tempLocs.add(new ValuedLocation(tempX,tempY-1,tempV+1));
						pathMap[tempY-1][tempX]=tempV+1;
					}
				}
				if(tempY<49) {
					if(pathMap[tempY+1][tempX]==0) {
						tempLocs.add(new ValuedLocation(tempX,tempY+1,tempV+1));
						pathMap[tempY+1][tempX]=tempV+1;
					}
				}
				if(tempX>0&&tempY>0) {
					if(pathMap[tempY-1][tempX-1]==0) {
						tempLocs.add(new ValuedLocation(tempX-1,tempY-1,tempV+1));
						pathMap[tempY-1][tempX-1]=tempV+1;
					}
				}
				if(tempX>0&&tempY<49) {
					if(pathMap[tempY+1][tempX-1]==0) {
						tempLocs.add(new ValuedLocation(tempX-1,tempY+1,tempV+1));
						pathMap[tempY+1][tempX-1]=tempV+1;
					}
				}
				if(tempX<99&&tempY<49) {
					if(pathMap[tempY+1][tempX+1]==0) {
						tempLocs.add(new ValuedLocation(tempX+1,tempY+1,tempV+1));
						pathMap[tempY+1][tempX+1]=tempV+1;
					}
				}
				if(tempX<99&&tempY>0) {
					if(pathMap[tempY-1][tempX+1]==0) {
						tempLocs.add(new ValuedLocation(tempX+1,tempY-1,tempV+1));
						pathMap[tempY-1][tempX+1]=tempV+1;
					}
				}
			}
			locs = new ArrayList<ValuedLocation>();
			locs = (ArrayList<ValuedLocation>) tempLocs.clone();
		}
		
		pathMap[flag.getRow()][flag.getCol()] = 1;
	}
	
	public String getMap() {
		//Simply prints out a visual of the path map
		String s = "";
		for(int i = 0; i<pathMap.length; i++) {
			for(int j = 0; j<pathMap[i].length; j++) {
				s+=pathMap[i][j]+" ";
			}
			s+="\n";
		}
		return s;
	};
	
	public void reverseIt() {
		//Clears out reverse map for use
		reverse = new int[50][100];
		Location flag = getTeam().getFlag().getLocation();
		List<AbstractPlayer> opposing = getTeam().getOpposingTeam().getPlayers();
		ArrayList<Location> rocks = getGrid().getOccupiedLocations();
		Grid<Actor> g = getGrid();
		ArrayList<Location> contains = g.getOccupiedLocations();
		//Marks all rocks as -1
		for(int i = 0; i<contains.size(); i++) {
			Location l = contains.get(i);
			if(g.get(l) instanceof Rock) {
				reverse[l.getRow()][l.getCol()] = -1;
			}
		} 
		ArrayList<ValuedLocation> locs = new ArrayList<ValuedLocation>();
		//Creates a 'wall' of low values in the reverse map so that way the players naturally move towards the wall
		int xPos = (flag.getCol()<50?40:60);
		for(int i = 0; i<50; i++) {
			locs.add(new ValuedLocation(xPos,i,1));
		}
		//Populates reverse map
		while(locs.size()>0) {
			ArrayList<ValuedLocation> tempLocs = new ArrayList<ValuedLocation>();
			for(int i = 0; i<locs.size(); i++) {
				ValuedLocation t = locs.get(i);
				int tempX = t.getX();
				int tempY = t.getY();
				int tempV = t.getV();
				if(tempX>0) {
					if(reverse[tempY][tempX-1]==0) {
						tempLocs.add(new ValuedLocation(tempX-1,tempY,tempV+1));
						reverse[tempY][tempX-1]=tempV+1;
					}
				}
				if(tempX<99) {
					if(reverse[tempY][tempX+1]==0) {
						tempLocs.add(new ValuedLocation(tempX+1,tempY,tempV+1));
						reverse[tempY][tempX+1]=tempV+1;
					}
				}
				if(tempY>0) {
					if(reverse[tempY-1][tempX]==0) {
						tempLocs.add(new ValuedLocation(tempX,tempY-1,tempV+1));
						reverse[tempY-1][tempX]=tempV+1;
					}
				}
				if(tempY<49) {
					if(reverse[tempY+1][tempX]==0) {
						tempLocs.add(new ValuedLocation(tempX,tempY+1,tempV+1));
						reverse[tempY+1][tempX]=tempV+1;
					}
				}
				if(tempX>0&&tempY>0) {
					if(reverse[tempY-1][tempX-1]==0) {
						tempLocs.add(new ValuedLocation(tempX-1,tempY-1,tempV+1));
						reverse[tempY-1][tempX-1]=tempV+1;
					}
				}
				if(tempX>0&&tempY<49) {
					if(reverse[tempY+1][tempX-1]==0) {
						tempLocs.add(new ValuedLocation(tempX-1,tempY+1,tempV+1));
						reverse[tempY+1][tempX-1]=tempV+1;
					}
				}
				if(tempX<99&&tempY<49) {
					if(reverse[tempY+1][tempX+1]==0) {
						tempLocs.add(new ValuedLocation(tempX+1,tempY+1,tempV+1));
						reverse[tempY+1][tempX+1]=tempV+1;
					}
				}
				if(tempX<99&&tempY>0) {
					if(reverse[tempY-1][tempX+1]==0) {
						tempLocs.add(new ValuedLocation(tempX+1,tempY-1,tempV+1));
						reverse[tempY-1][tempX+1]=tempV+1;
					}
				}
			}
			locs = new ArrayList<ValuedLocation>();
			locs = (ArrayList<ValuedLocation>) tempLocs.clone();
		}
	};
	
	
	public void instate() {
//		System.out.println(getMap());
		//Useless
	}
	
	public Location findSmallest(Location l) {
		//Returns the spot in the pathmap with the optimal location to move to
		int x = l.getCol();
		int y = l.getRow();
		Location loc = new Location(x,y);
		int num = pathMap[y][x];
		if(this.hasFlag()) {
			num = reverse[y][x];
		}
		Grid<Actor> g = getGrid();
		ArrayList<Location> spots = g.getEmptyAdjacentLocations(l);
		for(int i = 0; i<spots.size(); i++) {
			if(this.hasFlag()) {
				if(reverse[spots.get(i).getRow()][spots.get(i).getCol()]<num) {
					loc = spots.get(i);
					num = reverse[spots.get(i).getRow()][spots.get(i).getCol()];
				}
			} else {
				if(pathMap[spots.get(i).getRow()][spots.get(i).getCol()]<=num) {
					loc = spots.get(i);
					num = pathMap[spots.get(i).getRow()][spots.get(i).getCol()];
				}
			}
		}
		return loc;
	}
	
	public Location getMoveLocation() {
		//Creates a path map to the flag
		mapIt();
		//Creates a reverse map back to safety
		reverseIt();
		//Gets ideal location to move to
		Location moveTo = findSmallest(this.getLocation());
		//This is for Max, I'm not sure what it does
		runit(moveTo);
		avoidRocks(moveTo);
		//Returns the ideal move location
		return moveTo;
	}
	
	public int[][] path() {
		return pathMap;
	}
}