package org.pumatech.teams.twice;

import java.util.List;

import org.pumatech.ctf.AbstractPlayer;
import org.pumatech.ctf.Flag;
import org.pumatech.ctf.Team;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class DefensePlayer extends AbstractPlayer {
	public DefensePlayer (Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		Location myLoc = this.getLocation();
		Grid<Actor> myGrid = this.getGrid();
		Team myTeam = this.getTeam();
		List <AbstractPlayer> myPlayers = myTeam.getPlayers();
		Team theirTeam = myTeam.getOpposingTeam();
		List<AbstractPlayer> theirPlayers = theirTeam.getPlayers();
		Location myFlag= myTeam.getFlag().getLocation();
		Location theirFlagLoc= theirTeam.getFlag().getLocation();
		Flag theirFlag = theirTeam.getFlag();
		List<Location> locoptions=myGrid.getEmptyAdjacentLocations(myLoc);
		int way= myLoc.getDirectionToward(myFlag);
		boolean theirTeamHasFlag=false;
		
		for(AbstractPlayer i: theirPlayers){
			if(i.hasFlag()==true){
				way=myLoc.getDirectionToward(i.getLocation());
				theirTeamHasFlag=true;
				break;
			}
		}
		if(theirTeam.nearFlag(myFlag)==true || this.tooFarFromFlag(myLoc, myFlag)==true){
			way=myLoc.getDirectionToward(myFlag);
		}
		else if(theirTeamHasFlag==false){
			//towards theirFlag +- 90 degrees
			way= myLoc.getDirectionToward(theirFlagLoc);
			int random= (int) (Math.random()*4)-2;
			way=way+(random*45);
		}
		Location myChoice= myLoc.getAdjacentLocation(way);
		
		if (myGrid.isValid(myChoice) && (myGrid.get(myChoice) == null)) {
			return myChoice;
		}
		else {
			while (!(myGrid.isValid(myChoice) && (myGrid.get(myChoice) == null))){
				int choice = (int)(Math.random() * (locoptions.size() -1)); 
				myChoice = locoptions.get(choice);
				//way = way + 45;
				//myChoice=myLoc.getAdjacentLocation(way);
			}
			
			//int choice = (int)(Math.random() * locoptions.size()); 
			//return locoptions.get(choice);
			return(myChoice);
		}
		
	}
	
	private boolean tooFarFromFlag(Location loc, Location myFlag) {
		double distance =  Math.sqrt(Math.pow(loc.getRow() - myFlag.getRow(), 2) + Math.pow(loc.getCol() - myFlag.getCol(), 2));
		boolean tooFar = (distance >=6 );
		System.out.println("tooFar: " + tooFar);
		return tooFar;
	}
}
