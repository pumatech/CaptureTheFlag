package org.pumatech.teams.SquadTeamTeamSquad;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2019.AbstractPlayer;
import org.pumatech.CTF2019.Flag;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class STTS_avgJoe extends AbstractPlayer {
	private Location prevLoc;

	public STTS_avgJoe(Location startLocation) {
		super(startLocation);
	}

	@Override
	public Location getMoveLocation() {
		// I STILL NEED TO PREVENT THE PLAYERS FROM GETTING STUCK IN AN ENCLOSED
		// AREA AND I NEED TO ENSURE MY PLAYERS STAY IN GRID

		List<AbstractPlayer> oppTeam = getTeam().getOpposingTeam().getPlayers();
		List<AbstractPlayer> myTeam = getTeam().getPlayers();

		if (hasFlag()) {
			if (this.nearPlayer(this.getLocation(), this.getClosest(oppTeam)) < 3) {
				if (isRock(this.getLocation(),
						this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()) + 180) == false
						&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(180 + this.getLocation()
								.getDirectionToward(this.getClosest(oppTeam).getLocation()))) == true) {
					return this.getLocation().getAdjacentLocation(
							180 + this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()));
				}
			}
			if (isRock(this.getLocation(),
					this.getLocation().getDirectionToward(getTeam().getFlag().getLocation())) == false) {
				return getTeam().getFlag().getLocation();
			} else if (isRock(this.getLocation(), Location.SOUTH) == false
					&& this.getLocation().getAdjacentLocation(Location.SOUTH).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.SOUTH))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTH);
			} else if (isRock(this.getLocation(), Location.SOUTHEAST) == false
					&& this.getLocation().getAdjacentLocation(Location.SOUTHEAST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.SOUTHEAST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTHEAST);
			} else if (isRock(this.getLocation(), Location.SOUTHWEST) == false
					&& this.getLocation().getAdjacentLocation(Location.SOUTHWEST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.SOUTHWEST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTHWEST);
			} else if (isRock(this.getLocation(), Location.NORTH) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTH).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.NORTH))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTH);
			} else if (isRock(this.getLocation(), Location.NORTHEAST) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTHEAST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.NORTHEAST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHEAST);
			}

			else if (isRock(this.getLocation(), Location.NORTHWEST) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTHWEST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.NORTHWEST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHWEST);
			} else {
				return null;
			}
		} else if (this.getLocation().getDirectionToward(getTeam().getFlag().getLocation()) == (this.getLocation()
				.getDirectionToward(getTeam().getOpposingTeam().getFlag().getLocation()))) {
			List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
			return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
		} else if (isOnSide(this) == false && this.nearPlayer(this.getLocation(), this.getClosest(oppTeam)) < 3
				&& this.nearFlag(this.getLocation(), getTeam().getOpposingTeam().getFlag()) > 4) {
			if (isRock(this.getLocation(),
					this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()) + 180) == false) {
				return this.getLocation().getAdjacentLocation(
						180 + this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()));
			} else {
				List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
				return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
			}
		} else if (this.teamHasFlag(myTeam) == true) {
			if (isOnSide(this) == false && this.nearPlayer(this.getLocation(), this.getClosest(oppTeam)) < 3) {
				if (isRock(this.getLocation(),
						this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()) + 180) == false
						&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(180 + this.getLocation()
								.getDirectionToward(this.getClosest(oppTeam).getLocation()))) == true) {
					return this.getLocation().getAdjacentLocation(
							180 + this.getLocation().getDirectionToward(this.getClosest(oppTeam).getLocation()));
				}
			}
			if (isRock(this.getLocation(),
					this.getLocation().getDirectionToward(getTeam().getFlag().getLocation())) == false) {
				return getTeam().getFlag().getLocation();
			} else if (isRock(this.getLocation(), Location.SOUTH) == false
					&& this.getLocation().getAdjacentLocation(Location.SOUTH).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.SOUTH))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTH);
			} else if (isRock(this.getLocation(), Location.SOUTHEAST) == false
					&& this.getLocation().getAdjacentLocation(Location.SOUTHEAST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.SOUTHEAST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTHEAST);
			} else if (isRock(this.getLocation(), Location.SOUTHWEST) == false
					&& this.getLocation().getAdjacentLocation(Location.SOUTHWEST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.SOUTHWEST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTHWEST);
			} else if (isRock(this.getLocation(), Location.NORTH) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTH).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.NORTH))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTH);
			} else if (isRock(this.getLocation(), Location.NORTHEAST) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTHEAST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.NORTHEAST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHEAST);
			}

			else if (isRock(this.getLocation(), Location.NORTHWEST) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTHWEST).equals(getPrevLoc()) == false
					&& this.getGrid().isValid(this.getLocation().getAdjacentLocation(Location.NORTHWEST))) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHWEST);
			} else {
				return null;
			}
		}

		else {

			if (isRock(this.getLocation(), this.getLocation()
					.getDirectionToward(getTeam().getOpposingTeam().getFlag().getLocation())) == false) {
				setPrevLoc(this.getLocation());
				return getTeam().getOpposingTeam().getFlag().getLocation();
			} else if (isRock(this.getLocation(), Location.NORTH) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTH).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTH);
			} else if (isRock(this.getLocation(), Location.NORTHEAST) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTHEAST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHEAST);
			} else if (isRock(this.getLocation(), Location.NORTHWEST) == false
					&& this.getLocation().getAdjacentLocation(Location.NORTHEAST).equals(getPrevLoc()) == false) {
				setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.NORTHWEST);
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
				this.setPrevLoc(this.getLocation());
				return this.getLocation().getAdjacentLocation(Location.SOUTHWEST);
			} else {
				return null;
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