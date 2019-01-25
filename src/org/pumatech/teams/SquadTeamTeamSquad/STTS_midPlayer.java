package org.pumatech.teams.SquadTeamTeamSquad;

import java.util.List;

import org.pumatech.newCTF.AbstractPlayer;
import org.pumatech.newCTF.Flag;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class STTS_midPlayer extends AbstractPlayer {
	private Location prevLoc;

	public STTS_midPlayer(Location startLocation) {
		super(startLocation);
		
	}

	@Override
	public Location getMoveLocation() {
		if(prevLoc==null) {
			prevLoc = this.getLocation();
		}
		List<AbstractPlayer> oppTeam = getTeam().getOpposingTeam().getPlayers();
		for (AbstractPlayer nerd : oppTeam) {
			if (nerd.hasFlag() == true) {
				if (isRock(this.getLocation(), this.getLocation().getDirectionToward(nerd.getLocation())) == false) {
					return this.getLocation()
							.getAdjacentLocation(this.getLocation().getDirectionToward(nerd.getLocation()));
				}
			}
		}
		if (isOnSide(this.getClosest(oppTeam)) == false && isRock(this.getLocation(),
				this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation())) == false) {
			return this.getLocation()
					.getAdjacentLocation(this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()));
		}

		else if (isOnSide(this) == true) {
			List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
			if (possibleMoveLocations.size() == 0) {
				return null;
			}
			return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
		} else {
			if (isRock(this.getLocation(),
					this.getLocation().getDirectionToward(getTeam().getFlag().getLocation())) == false) {
				return getTeam().getFlag().getLocation();
			} else {
				List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
				if (possibleMoveLocations.size() == 0) {
					return null;
				}
				return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
			}
		}
	}

	private boolean isRock(Location loc, int dir) {
		Grid<Actor> gr = getGrid();
		Location next = loc.getAdjacentLocation(dir);
		if (this.getGrid().isValid(next) == false) {
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
			if (winner.hasFlag() == true && this.equals(winner) == false) {
				return true;
			}
		}
		return false;
	}
	
	public int nearFlag(Location loc, Flag nerd) {
		return (int) Math.sqrt(Math.pow(loc.getRow() - nerd.getLocation().getRow(), 2)
				+ Math.pow(loc.getCol() - nerd.getLocation().getCol(), 2));
	}
}