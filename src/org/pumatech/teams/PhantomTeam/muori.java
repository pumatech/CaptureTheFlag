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

public class muori extends AbstractPlayer {
	private ArrayList<Location> past = new ArrayList<Location>();
		//private Grid grid = this.getGrid();

	protected static boolean gottem = false;
	private boolean me = false;
	public muori(Location startLocation) {
		super(startLocation);
		past.add(startLocation);
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
		ArrayList<Location> tion = grid.getValidAdjacentLocations(cation);
		int next = cation.getDirectionToward(away);
		if(gottem == true && me == true){
			if(hasFlag() == false){
				gottem = false;
			}
		}
		else if(gottem == true){
			return goBack();
		}
		else if(hasFlag()){
			 me = true;
			return goBack();
		}
		 
		

		 else{
		//System.out.println(away);
		Location moneymove = cation.getAdjacentLocation(next);
		//System.out.println(next);
		if(grid.isValid(moneymove) && otherTeam(moneymove) == false && !(grid.get(moneymove) instanceof Rock) && !(grid.get(moneymove) instanceof muori) && !(grid.get(moneymove) instanceof aStar)){
			//System.out.println(next+" "+away+" "+moneymove);
	//boolean swing = false;
//			while(grid.get(moneymove) instanceof Rock || grid.isValid(moneymove)==false){
//				next+=45;
//				moneymove = cation.getAdjacentLocation(next);
//				//cooldown++;
//				//swing = true;
//			}
			
	//turner = 0;
//			if(swing == true) cooldown+=3;
//			swing = false;
			if(!(team.onSide(moneymove))){
			for(Location meek: tion){
				ArrayList<Location> catty = grid.getOccupiedAdjacentLocations(cation);
				for(Location mill: catty){
					if(!((grid.get(mill) instanceof muori )||(grid.get(mill) instanceof Rock )|| (grid.get(mill) instanceof aStar ))) return cation.getAdjacentLocation(cation.getDirectionToward(mill)+45);
				}
			}
			}
			past.add(moneymove);
			return moneymove;
			
		}
		else{
			//ArrayList<Location> tion = grid.getValidAdjacentLocations(cation);
			if (tion.size() == 0) return getLocation();
			Location newt = tion.get((int) (Math.random() * tion.size()));
			past.add(newt);
			return newt;
//			int count = 0;
//			
//			while(grid.isValid(moneymove)==false && count < 7 && otherTeam(moneymove) == false){
//				next+=45;
//				moneymove = cation.getAdjacentLocation(next);
//						count++;
//			}
//			if(count>=7) return getLocation();
			
//			while(grid.get(moneymove) instanceof Rock || grid.isValid(moneymove)==false){
//				next+=45;
//				moneymove = cation.getAdjacentLocation(next);
//			}
//			if(swing == true) cooldown+=3;
//			swing = false;
			//past.add(moneymove);
			//turner = 0;
			//return moneymove;
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
//						if(!(seek instanceof Rock || seek instanceof muori)){
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
//			if(!(seek instanceof Rock || seek instanceof muori)){
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
		
			
	
		 }
		return cation;
		
		}
		

	
	
	

	public ArrayList<Location> source(){ 
		ArrayList<Location> next = new ArrayList<Location>();
		
		return null;
		
	}
	public ArrayList<Integer> obstacle(ArrayList<Location> next){
		return null;
		
	}
	public Location goBack(){
		Location where = getLocation();
		if(past.size()> 0){
		 where = past.get(past.size() - 1);
		past.remove(past.size()-1);
		}
		
			
		
		return where;
	}
	public boolean otherTeam(Location now){
		Grid grid = this.getGrid();
		Team team = this.getTeam();
		Team oteam = team.getOpposingTeam();
		ArrayList<Actor> caution = grid.getNeighbors(now);
		
				for(Actor seek: caution){
					if(!(seek instanceof Rock || seek instanceof muori || seek instanceof aStar)){
						
						return true;
					}
				}
		return false;
		
	
	}
	

}