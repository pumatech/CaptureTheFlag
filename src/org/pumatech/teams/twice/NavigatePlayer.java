package org.pumatech.teams.twice;

import java.util.List;
import java.util.Random;

import org.pumatech.CTF2018.AbstractPlayer;
import org.pumatech.CTF2018.Flag;
import org.pumatech.CTF2018.Team;

import com.sun.javafx.scene.traversal.Direction;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class NavigatePlayer extends AbstractPlayer {
	List <Location> blacklist;
	public NavigatePlayer (Location startLocation) {
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
		Flag theirFlag = theirTeam.getFlag();
		List<Location> locoptions=myGrid.getEmptyAdjacentLocations(myLoc);
		
		int way= myLoc.getDirectionToward(theirFlag.getLocation());
		if(this.hasFlag()) {
			way= myLoc.getDirectionToward(myFlag);
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
//			while (!(myGrid.isValid(myChoice) && (myGrid.get(myChoice) == null))){
//				way = way + 45;
//				myChoice=myLoc.getAdjacentLocation(way);
//			}
//			while (!(myGrid.isValid(myChoice) && (myGrid.get(myChoice) == null))){
////				way = way + 45;
////				myChoice=myLoc.getAdjacentLocation(way);
////			}
//			int choice = (int)(Math.random() * locoptions.size()); 
//			return locoptions.get(choice);
			return(myChoice);
		}

	}
	

}
