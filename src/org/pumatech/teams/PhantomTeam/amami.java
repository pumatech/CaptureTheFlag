package org.pumatech.teams.PhantomTeam;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;
import org.pumatech.ctf.Flag;
import org.pumatech.ctf.Team;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class amami<E> extends AbstractPlayer {
	private ArrayList<Location> past = new ArrayList<Location>();
	int turner = 45;
	int cooldown = 0;
	//private Grid grid = this.getGrid();
	private Team team = this.getTeam();
	
	public amami(Location startLocation) {
		super(startLocation);
	}

	@Override
	public Location getMoveLocation() {
		Grid grid = this.getGrid();
		Team team = this.getTeam();
		Team oteam = team.getOpposingTeam();
		List<AbstractPlayer> theirPlayers = oteam.getPlayers();
		Location home = getTeam().getFlag().getLocation();
		Location away = getTeam().getOpposingTeam().getFlag().getLocation();
		Location cation = getLocation();
		if(hasFlag()){
			return goBack();
		}
//		else{
//			if(cooldown > 0){
//				if(cooldown >= 2){
//					cooldown--;
//					return goBack();
//				}
//			else if(cooldown < 2){
//				cooldown--;
//					if(cation.getRow()>=25){
//						if(grid.isValid(cation.getAdjacentLocation(0))) return cation.getAdjacentLocation(0);
//						else{ return cation.getAdjacentLocation(180);}
//					}
//					
//						else{
//							if(grid.isValid(cation.getAdjacentLocation(180))) return cation.getAdjacentLocation(180);
//							else{ return cation.getAdjacentLocation(0);}
//						}
//				}
//			
//				
//			}
//			
//	
			
			else{
		int next = cation.getDirectionToward(away);
		Location moneymove = cation.getAdjacentLocation(next);
		if(grid.isValid(moneymove) && otherTeam(moneymove) == false){
	boolean swing = false;
			if(grid.get(moneymove) instanceof Rock || grid.isValid(moneymove)==false){
				
				moneymove = cation.getAdjacentLocation(next+45);
				//cooldown++;
				swing = true;
			}
			
	//turner = 0;
			if(swing == true) cooldown+=3;
			swing = false;
			past.add(moneymove);
			return moneymove;
			
		}
		else{
			int count = 0;
			
			while(grid.isValid(moneymove)==false && count <= 7 && otherTeam(moneymove) == false){
				next+=45;
				moneymove = cation.getAdjacentLocation(next);
						count++;
			}
			if(count>=7) return getLocation();
			boolean swing = false;
			if(grid.get(moneymove) instanceof Rock || grid.isValid(moneymove)==false){
				moneymove = cation.getAdjacentLocation(next +45);
			}
			swing = true;
			past.add(moneymove);
			//turner = 0;
			return moneymove;
		}
		//System.out.println(grid.isValid(moneymove));
//		if(grid.isValid(moneymove) == false){
//			int turns = 0;
//			boolean good = true;
//			do{
//				 good = true;
//				next +=45;
//				moneymove = cation.getAdjacentLocation(next);
//				if(grid.isValid(moneymove)){
//					ArrayList<Actor> caution = grid.getNeighbors(moneymove);
//					
//					for(Actor seek: caution){
//						if(!(seek instanceof Rock || seek instanceof amami)){
//							good = false;
//						}
//					}
//				}
//				turns++;
//			}
//			while(turns < 7 && (good == false));
//		
//		}
//		else{
		//ArrayList<Location> caution = new ArrayList<Location>();
//		int x = 0;
//		while(x < 315){
//			caution.add(cation.getAdjacentLocation(next));
//			x +=45;
//		}
		//boolean nomove = false;
//			ArrayList<Actor> caution = grid.getNeighbors(moneymove);
//
//		for(Actor seek: caution){
//			if(!(seek instanceof Rock || seek instanceof amami)){
//				//nomove = true;
//				return cation.getAdjacentLocation(next+180);
//			}
//		}
		//if(nomove == false) return moneymove;
//		//else{
//			next+=180;
//			moneymove = cation.getAdjacentLocation(next);
//			if(grid.isValid(moneymove)) return moneymove;
//	
//		}
		//}
		//return getLocation();
			
		}
		}

	
	
	public ArrayList<Location> source(){ 
		ArrayList<Location> next = new ArrayList<Location>();
		
		return null;
		
	}
	public ArrayList<Integer> obstacle(ArrayList<Location> next){
		return null;
		
	}
	public Location goBack(){
		Location where = past.get(past.size() - 1);
		past.remove(past.size()-1);
		return where;
	}
	public boolean otherTeam(Location now){
		Grid grid = this.getGrid();
		Team team = this.getTeam();
		Team oteam = team.getOpposingTeam();
		ArrayList<Actor> caution = grid.getNeighbors(now);
		
				for(Actor seek: caution){
					if(!(seek instanceof Rock || seek instanceof amami)){
						
						return false;
					}
				}
		return true;
		
	
	}

}