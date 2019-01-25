package org.pumatech.teams.SquadTeamTeamSquad;

import java.util.List;

import org.pumatech.newCTF.AbstractPlayer;
import org.pumatech.newCTF.Flag;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class STTS_defPlayer extends AbstractPlayer {
	private Location prevLocation;

	public STTS_defPlayer(Location startLocation) {
		super(startLocation);
	}

	@Override
	public Location getMoveLocation() {
		if(prevLocation==null) {
			prevLocation = this.getLocation();
		}
		List<AbstractPlayer> oppTeam = getTeam().getOpposingTeam().getPlayers();
		if (nearFlag(this.getLocation(), this.getTeam().getFlag()) <= 10
				&& nearFlag(this.getLocation(), this.getTeam().getFlag()) >= 4
				&& this.nearPlayer(this.getLocation(), this.getClosest(oppTeam)) < 5 && isRock(this.getLocation(),
						this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation())) == false) {
			return this.getLocation()
					.getAdjacentLocation(this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()));
		}
		// Moving towards the flag
		else if (isRock(this.getLocation(),
				this.getLocation().getDirectionToward(this.getTeam().getFlag().getLocation())) == false
				&& getGrid().isValid(getTeam().getFlag().getLocation())
				&& nearFlag(this.getLocation(), this.getTeam().getFlag()) >= 6) {
			return getTeam().getFlag().getLocation();
		}
		//Rotating Code VVV
		else if (isRock(this.getLocation(),
				this.getLocation().getDirectionToward(getTeam().getFlag().getLocation()) + 90) == false) {
			return this.getLocation()
					.getAdjacentLocation(this.getLocation().getDirectionToward(getTeam().getFlag().getLocation()) + 90);
		}
		else if (isRock(this.getLocation(), Location.NORTH) == false
				&& this.getLocation().getAdjacentLocation(Location.NORTH).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.NORTH);
		} else if (isRock(this.getLocation(), Location.NORTHEAST) == false
				&& this.getLocation().getAdjacentLocation(Location.NORTHEAST).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.NORTHEAST);
		} else if (isRock(this.getLocation(), Location.NORTHWEST) == false
				&& this.getLocation().getAdjacentLocation(Location.NORTHWEST).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.NORTHWEST);
		} else if (isRock(this.getLocation(), Location.WEST) == false
				&& this.getLocation().getAdjacentLocation(Location.WEST).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.WEST);
		} else if (isRock(this.getLocation(), Location.EAST) == false
				&& this.getLocation().getAdjacentLocation(Location.EAST).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.EAST);
		} else if (isRock(this.getLocation(), Location.SOUTH) == false
				&& this.getLocation().getAdjacentLocation(Location.SOUTH).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.SOUTH);
		} else if (isRock(this.getLocation(), Location.SOUTHEAST) == false
				&& this.getLocation().getAdjacentLocation(Location.SOUTHEAST).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.SOUTHEAST);
		} else if (isRock(this.getLocation(), Location.SOUTHWEST) == false
				&& this.getLocation().getAdjacentLocation(Location.SOUTHWEST).equals(getPrevLoc()) == false) {
			setPrevLoc(this.getLocation());
			return this.getLocation().getAdjacentLocation(Location.SOUTHWEST);
		} else {
			return null;
		}
	}
	/*
	 * Returns false if given player is on the other side
	 */
	// private boolean isOnSide(AbstractPlayer p) {
	// if (p.getTeam().onSide(p.getLocation()) == false) {
	// return false;
	// } else {
	// return true;
	// }
	// }

	/*
	 * Returns false if there is NOT a rock
	 */
	private boolean isRock(Location l, int dir) {
		Grid<Actor> gr = getGrid();
		Location next = l.getAdjacentLocation(dir);
		if (gr.isValid(next) == false) {
			return true;
		}
		Actor neighbor = gr.get(next);
		if (neighbor instanceof Rock || neighbor instanceof AbstractPlayer) {
			return true;
		} else {
			return false;
		}

	}

	public Location getPrevLoc() {
		return prevLocation;
	}

	public void setPrevLoc(Location l) {
		this.prevLocation = l;
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

	public int nearFlag(Location loc, Flag nerd) {
		return (int) Math.sqrt(Math.pow(loc.getRow() - nerd.getLocation().getRow(), 2)
				+ Math.pow(loc.getCol() - nerd.getLocation().getCol(), 2));
	}
}