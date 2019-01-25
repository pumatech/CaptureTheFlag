package org.pumatech.teams.SquadTeamTeamSquad;

import java.util.List;

import org.pumatech.newCTF.AbstractPlayer;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class STTS_baitPlayer extends AbstractPlayer {
	private Location prevLoc;

	public STTS_baitPlayer(Location startLocation) {
		super(startLocation);
	}

	@Override
	public Location getMoveLocation() {
		List<AbstractPlayer> oppTeam = getTeam().getOpposingTeam().getPlayers();
		for (AbstractPlayer nerd : oppTeam) {
			if (nerd.hasFlag() == true) {
				if (isRock(this.getLocation(), this.getLocation().getDirectionToward(nerd.getLocation())) == false) {
					return this.getLocation()
							.getAdjacentLocation(this.getLocation().getDirectionToward(nerd.getLocation()));
				}
			}
		}
		//List<AbstractPlayer> myTeam = getTeam().getPlayers();
		if(isOnSide(this)==true){
			if(isRock(this.getLocation(),this.getLocation().getDirectionToward(this.getTeam().getOpposingTeam().getFlag().getLocation()))==false){
				return this.getTeam().getOpposingTeam().getFlag().getLocation();
			}
			else if (isRock(this.getLocation(), Location.SOUTH) == false&& this.getLocation().getAdjacentLocation(Location.SOUTH).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTH);
			} 
			else if (isRock(this.getLocation(), Location.WEST) == false	&& this.getLocation().getAdjacentLocation(Location.WEST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.WEST);
			}
			else if (isRock(this.getLocation(), Location.EAST) == false	&& this.getLocation().getAdjacentLocation(Location.EAST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.EAST);
			}else if (isRock(this.getLocation(), Location.SOUTHEAST) == false&& this.getLocation().getAdjacentLocation(Location.SOUTHEAST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTHEAST);
			} else if (isRock(this.getLocation(), Location.SOUTHWEST) == false&& this.getLocation().getAdjacentLocation(Location.SOUTHWEST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTHWEST);
			} else if (isRock(this.getLocation(), Location.NORTH) == false&& this.getLocation().getAdjacentLocation(Location.NORTH).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTH);
			} else if (isRock(this.getLocation(), Location.NORTHEAST) == false&& this.getLocation().getAdjacentLocation(Location.NORTHEAST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHEAST);
			}

			else if (isRock(this.getLocation(), Location.NORTHWEST) == false && this.getLocation().getAdjacentLocation(Location.NORTHWEST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHWEST);
			}
			else{
				return null;
			}
		}
		else if (isOnSide(this)==false && this.nearPlayer(this.getLocation(), this.getClosest(oppTeam)) < 3) {
			if (isRock(this.getLocation(),this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()) + 180) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(180 + this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()));
			}
			else if (isRock(this.getLocation(),this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()) + 270) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(270 + this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()) );
			}
			else if (isRock(this.getLocation(),	this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()) + 90) == false && this.getLocation().getAdjacentLocation(90 + this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation())).equals(getPrevLoc())==false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(90 + this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()));
			}
			if (isRock(this.getLocation(),	this.getLocation().getDirectionToward(this.getTeam().getFlag().getLocation())) == false) {
				setPrevLoc(this.getLocation());
				return this.getTeam().getFlag().getLocation();
			} else {
				List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
				setPrevLoc(this.getLocation());
				return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
			}
		}
		else{
			setPrevLoc(this.getLocation());
			return this.getTeam().getFlag().getLocation();
		}
	}

	private boolean isRock(Location l, int dir) {
		Grid<Actor> gr = getGrid();
		Location next = l.getAdjacentLocation(dir);
		if(gr.isValid(next)==false){
			return true;
					}
		Actor neighbor = gr.get(next);
		if (neighbor instanceof Rock || neighbor instanceof AbstractPlayer) {
			return true;
		} else {
			return false;
		}
	
	}

	private boolean isOnSide(AbstractPlayer p) {
		if (p.getTeam().onSide(p.getLocation()) == false) {
			return false;
		} else {
			return true;
		}
	}
	
	public Location getPrevLoc() {
		return prevLoc;
	}

	public void setPrevLoc(Location pL) {
		this.prevLoc = pL;
	}

	public AbstractPlayer getClosest(List<AbstractPlayer> nerds) {
		AbstractPlayer closest = null;
		int distance = 1000;
		for (AbstractPlayer idiot : nerds) {
			if (nearPlayer(this.getLocation(), idiot) < distance) {
				distance = nearPlayer(this.getLocation(), idiot);
				closest = idiot;
			}
		}
		return closest;
	}

	public int nearPlayer(Location loc, AbstractPlayer nerd) {
		return (int) Math.sqrt(Math.pow(loc.getRow() - nerd.getLocation().getRow(), 2)
				+ Math.pow(loc.getCol() - nerd.getLocation().getCol(), 2));
	}

	public boolean teamHasFlag(List<AbstractPlayer> champs) {
		for (AbstractPlayer winner : champs) {
			if(winner.hasFlag()==true && this.equals(winner)==false){
				return true;
			}
		}
		return false;
	}
}
