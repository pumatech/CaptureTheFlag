package org.pumatech.teams.danielRuiFanClub;

import java.util.ArrayList;

import org.pumatech.ctf.AbstractPlayer;
import org.pumatech.ctf.Team;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

public class FinalPathfindPlayer extends AbstractPlayer {
	
	public Location pastLoc = this.getLocation();
	Location flagLoc = null;
	int pastLocCounter = 0;
	Location objective = null;
	
	public FinalPathfindPlayer(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
	flagLoc = this.getTeam().getFlag().getLocation();
//	Location testLoc= new Location(flagLoc.getRow(), flagLoc.getCol());
//	testLoc.setCol(2);
//	testLoc.setRow(24);
	
		if (hasFlag()) {
			objective = getTeam().getFlag().getLocation();
			return pathfind(getTeam().getFlag().getLocation());
		}
		objective = getTeam().getOpposingTeam().getFlag().getLocation();
		return pathfind(getTeam().getOpposingTeam().getFlag().getLocation());
	}
	
	public Location pathfind(Location targetLocation) {
		int targetDirection = this.getLocation().getDirectionToward(targetLocation);
		
		if(this.getGrid().isValid(infront(targetDirection)) && !(this.getGrid().get(infront(targetDirection)) instanceof Rock) && clearThreeInfront(infront(targetDirection), targetDirection) && !isFlagZone(infront(targetDirection)) && !friendlyPlayer(infront(targetDirection)) && !enemyNear(infront(targetDirection))) {
//			System.out.println("front");
			if(infront(targetDirection).equals(pastLoc)) {
				pastLocCounter++;
			}
			else {
				pastLocCounter = 0;
			}
			pastLoc = this.getLocation();
			return infront(targetDirection);
		}
		
		//check the front sides to see if they have a clearing
		else if(this.getGrid().isValid(frontLeft(targetDirection)) && !(this.getGrid().get(frontLeft(targetDirection)) instanceof Rock) && clearThreeInfront(frontLeft(targetDirection), targetDirection) && !isFlagZone(frontLeft(targetDirection)) && !friendlyPlayer(frontLeft(targetDirection)) && !enemyNear(frontLeft(targetDirection))) {
//			System.out.println("frontleft");
//			System.out.println(frontLeft(targetDirection));
//			System.out.println(this.getGrid().get(frontLeft(targetDirection)));
			if(infront(targetDirection).equals(pastLoc)) {
				pastLocCounter++;
			}
			else {
				pastLocCounter = 0;
			}
			pastLoc = this.getLocation();
			return frontLeft(targetDirection);
		}
		else if(this.getGrid().isValid(frontRight(targetDirection)) && !(this.getGrid().get(frontRight(targetDirection)) instanceof Rock) && clearThreeInfront(frontRight(targetDirection), targetDirection) && !isFlagZone(frontRight(targetDirection)) && !friendlyPlayer(frontRight(targetDirection)) && !enemyNear(frontRight(targetDirection))) {
//			System.out.println("frontright");
//			System.out.println(frontRight(targetDirection));
//			System.out.println(this.getGrid().get(frontRight(targetDirection)));
			if(infront(targetDirection).equals(pastLoc)) {
				pastLocCounter++;
			}
			else {
				pastLocCounter = 0;
			}
			pastLoc = this.getLocation();
			return frontRight(targetDirection);
		}
		
		//if no clearings in front move left or right
		else if(this.getGrid().isValid(left(targetDirection)) && !(this.getGrid().get(left(targetDirection)) instanceof Rock) && !isFlagZone(left(targetDirection)) && !friendlyPlayer(left(targetDirection)) && !enemyNear(left(targetDirection))) {
//			System.out.println("left");
			if(infront(targetDirection).equals(pastLoc)) {
				pastLocCounter++;
			}
			else {
				pastLocCounter = 0;
			}
			pastLoc = this.getLocation();
			return left(targetDirection);
		}
		else if(this.getGrid().isValid(right(targetDirection)) && !(this.getGrid().get(right(targetDirection)) instanceof Rock) && !isFlagZone(right(targetDirection)) && !friendlyPlayer(right(targetDirection)) && !enemyNear(right(targetDirection))) {
//			System.out.println("right");
			if(infront(targetDirection).equals(pastLoc)) {
				pastLocCounter++;
			}
			else {
				pastLocCounter = 0;
			}
			pastLoc = this.getLocation();
			return right(targetDirection);
		}
		//if it cant move left or right move diag back
		else if(this.getGrid().isValid(backLeft(targetDirection)) && !(this.getGrid().get(backLeft(targetDirection)) instanceof Rock) && !isFlagZone(backLeft(targetDirection)) && !friendlyPlayer(backLeft(targetDirection)) && !enemyNear(backLeft(targetDirection))) {
//			System.out.println("backleft");
			if(infront(targetDirection).equals(pastLoc)) {
				pastLocCounter++;
			}
			else {
				pastLocCounter = 0;
			}
			pastLoc = this.getLocation();
			return backLeft(targetDirection);
		}
		else if(this.getGrid().isValid(backRight(targetDirection)) && !(this.getGrid().get(backRight(targetDirection)) instanceof Rock) && !isFlagZone(backRight(targetDirection)) && !friendlyPlayer(backRight(targetDirection)) && !enemyNear(backRight(targetDirection))) {
//			System.out.println("backright");
			if(infront(targetDirection).equals(pastLoc)) {
				pastLocCounter++;
			}
			else {
				pastLocCounter = 0;
			}
			pastLoc = this.getLocation();
			return backRight(targetDirection);
		}
		
		else if (this.getGrid().isValid(infront(targetDirection+180)) && !(this.getGrid().get(infront(targetDirection+180)) instanceof Rock) && !isFlagZone(infront(targetDirection+180)) && !friendlyPlayer(infront(targetDirection+180)) && !enemyNear(infront(targetDirection+180))){
//			System.out.println("back");
			return infront(targetDirection+180);
		}
		//if you havn't found a path by now your f'ed.
		return infront(targetDirection+180);
	}
	
//	check out my +90 for only right and - 90 for left
	
	
	
	public Location twoInfront(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection);
	}	
	
	public Location infront(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection);
	}
	
	public Location frontRight(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection+45);
	}
	
	public Location frontLeft(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection-45);
	}
	
	public Location twoInfrontRight(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection+90);
	}
	
	public Location twoInfrontLeft(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection-90);
	}	
	
	public Location left(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection-90);
	}	
	
	public Location right(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection+90);
	}
	
	public Location backRight(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection+135);
	}
	
	public Location backLeft(int targetDirection) {
		return this.getLocation().getAdjacentLocation(targetDirection-135);
	}	
	
	
	
	public boolean isClearInfront(Location loc, int targetDirection) {	//out dated method but keep it cause it might be used somewhere im not aware
		if(!(this.getGrid().get(loc.getAdjacentLocation(targetDirection)) instanceof Rock) || !(this.getGrid().get(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection-90)) instanceof Rock) || !(this.getGrid().get(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection+90)) instanceof Rock)) {
			return true;
		}
		else return false;
	}	
	
	//return valid locations in front (check the 3 in front)	
	public ArrayList<Location> clearLocs(Location loc, int targetDirection) {
		ArrayList<Location> clearLocs = new ArrayList<Location>();
		if(this.getGrid().isValid(loc.getAdjacentLocation(targetDirection)) && !(this.getGrid().get(loc.getAdjacentLocation(targetDirection)) instanceof Rock)) {
			clearLocs.add(loc.getAdjacentLocation(targetDirection));
		}
		if(this.getGrid().isValid(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection-90)) && !(this.getGrid().get(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection-90)) instanceof Rock)) {
			clearLocs.add(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection-90));
		}
		if(this.getGrid().isValid(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection+90)) && !(this.getGrid().get(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection+90)) instanceof Rock)) {
			clearLocs.add(loc.getAdjacentLocation(targetDirection).getAdjacentLocation(targetDirection+90));
		}
		return clearLocs;
	}
	public boolean clearThreeInfront(Location loc, int targetDirection) {
		ArrayList<Location> clearOneLocs = clearLocs(loc, targetDirection);
		ArrayList<Location> clearTwoLocs = new ArrayList<Location>();
		ArrayList<Location> clearThreeLocs = new ArrayList<Location>();
		ArrayList<Location> clearFourLocs = new ArrayList<Location>();
		ArrayList<Location> clearFiveLocs = new ArrayList<Location>();
		ArrayList<Location> clearSixLocs = new ArrayList<Location>();
		//look two in front
		for(Location locations: clearOneLocs) {
			ArrayList<Location> returnTwoLocs = clearLocs(locations, targetDirectionUpdate(locations, objective));
			for (Location x : returnTwoLocs){
				   if (!clearTwoLocs.contains(x))
					   clearTwoLocs.add(x);
			}
		}
		//look three in front
		for(Location locations: clearTwoLocs) {
			ArrayList<Location> returnThreeLocs = clearLocs(locations, targetDirectionUpdate(locations, objective));
			for (Location x : returnThreeLocs){
				   if (!clearThreeLocs.contains(x))
					   clearThreeLocs.add(x);
			}
		}
		//look four in front
		for(Location locations: clearThreeLocs) {
			ArrayList<Location> returnFourLocs = clearLocs(locations, targetDirectionUpdate(locations, objective));
			for (Location x : returnFourLocs){
				   if (!clearFourLocs.contains(x))
					   clearFourLocs.add(x);
			}
		}
		//look five in front
		for(Location locations: clearFourLocs) {
			ArrayList<Location> returnFiveLocs = clearLocs(locations, targetDirectionUpdate(locations, objective));
			for (Location x : returnFiveLocs){
				   if (!clearFiveLocs.contains(x))
					   clearFiveLocs.add(x);
			}
		}
		//look four in front
		for(Location locations: clearFiveLocs) {
			ArrayList<Location> returnSixLocs = clearLocs(locations, targetDirectionUpdate(locations, objective));
			for (Location x : returnSixLocs){
				   if (!clearSixLocs.contains(x))
					   clearSixLocs.add(x);
			}
		}
		if(clearSixLocs.size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean isFlagZone(Location loc) {
		if(enemyHasFlag()) {
			return false;
		}
		if(loc.getRow() > flagLoc.getRow()-5 && loc.getRow() < flagLoc.getRow()+5 && loc.getCol() > flagLoc.getCol()-5 && loc.getCol() < flagLoc.getCol()+5) {
			return true;
		}
		return false;
	}
	
	public boolean enemyHasFlag() {
		ArrayList<AbstractPlayer> players = new ArrayList<AbstractPlayer>();
		players.addAll(this.getTeam().getOpposingTeam().getPlayers());
		for(AbstractPlayer player: players) {
			if(player.hasFlag()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean enemyNear(Location loc) {
		if(this.getTeam().onSide(loc)) {
			return false;
		}
		ArrayList<Location> nearLocs = this.getGrid().getValidAdjacentLocations(loc);
		for(Location near: nearLocs) {
			ArrayList<Location> occupiedLocs = this.getGrid().getOccupiedAdjacentLocations(near);
			for(Location occupied: occupiedLocs) {
				if(this.getGrid().get(occupied) instanceof AbstractPlayer && ((AbstractPlayer) this.getGrid().get(occupied)).getTeam() != this.getTeam()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean friendlyPlayer(Location loc) {
		if(this.getGrid().get(loc) instanceof AbstractPlayer && ((AbstractPlayer) this.getGrid().get(loc)).getTeam() == this.getTeam()) {
			return true;
		}
		return false;
	}

	public double getDistance(Location one, Location two) {
		Double y = (double) Math.abs(one.getCol() - two.getCol());
		Double x = (double) Math.abs(one.getRow() - two.getRow());
		return Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
	}
	public int targetDirectionUpdate(Location loc, Location target) {
		return loc.getDirectionToward(target);
	}
}


