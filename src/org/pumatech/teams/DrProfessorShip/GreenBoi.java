package org.pumatech.teams.DrProfessorShip;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class GreenBoi extends Player {
	private int[][] pathMap;
	
	public GreenBoi(Location startLocation) {
		super(startLocation);
		pathMap = new int[50][100];		
	}
	
	public void mapIt() {
		//Clears out pathmap for use
		pathMap = new int[50][100];
		Location flag = getTeam().getFlag().getLocation();
		List<AbstractPlayer> opposing = getTeam().getOpposingTeam().getPlayers();
		ArrayList<Location> rocks = getGrid().getOccupiedLocations();
		Grid<Actor> g = getGrid();
		ArrayList<Location> contains = g.getOccupiedLocations();
		//Sets all rocks in the pathmap to -1
		for(int i = 0; i<contains.size(); i++) {
			Location l = contains.get(i);
			if(g.get(l) instanceof Rock) {
				pathMap[l.getRow()][l.getCol()] = -1;
			}
		} 
		ArrayList<ValuedLocation> locs = new ArrayList<ValuedLocation>();
		//Sets all locations where there are enemies to 1, the lowest a player can move to on the path map
		for(int i = 0; i<opposing.size(); i++) {
			Location temp = opposing.get(i).getLocation();
			locs.add(new ValuedLocation(temp.getCol(),temp.getRow(),1));
		}
		//Builds the pathmap
		while(locs.size()>0) {
			ArrayList<ValuedLocation> tempLocs = new ArrayList<ValuedLocation>();
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
		//Creates a wall so that the defenders will not cross the line
		for(int i = 0; i<50; i++) {
			pathMap[i][47] = -1;
			pathMap[i][49] = -1;
			pathMap[i][50] = -1;
			pathMap[i][48] = -1;
			pathMap[i][51] = -1;
			pathMap[i][52] = -1;
		}
		//Sets all locations around and near the flag to -1
		int bleh = flag.getCol();
		int why = flag.getRow();
		for(int i = bleh-3; i<bleh+3; i++) {
			for(int j = why-3; j<why+3; j++) {
				pathMap[j][i] = -1;
			}
		}
	}
	
	public Location findSmallest(Location l) {
		//Returns best spot to move to
		int x = l.getCol();
		int y = l.getRow();
		Location loc = new Location(x,y);
		int num = pathMap[y][x];
		Grid<Actor> g = getGrid();
		ArrayList<Location> spots = g.getEmptyAdjacentLocations(l);
		for(int i = 0; i<spots.size(); i++) {
			int temp = pathMap[spots.get(i).getRow()][spots.get(i).getCol()];
			if(temp<=num&&temp>0) {
				loc = spots.get(i);
				num = temp;
			}
		}
		return loc;
	}
	
	public Location getMoveLocation() {
		//Creates pathmap
		mapIt();
		//Gets ideal spot to move to
		Location moveTo = findSmallest(this.getLocation());
		//This is Max's thing
		runit(moveTo);
		avoidRocks(moveTo);
		//Returns location to move to
		return moveTo;
	}

}
