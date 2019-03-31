package org.pumatech.teams.PhantomTeam;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;
import org.pumatech.CTF2018.Flag;
import org.pumatech.CTF2018.Team;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/**
 * @author Makenna Turner
 * Mezzanotte was written by Makenna Turner, 2018 (c).
 */
public class mezzanotte extends AbstractPlayer {
	private ArrayList<Location> past = new ArrayList<Location>();
	protected static boolean gottem = false;
	private boolean me = false;
/**
 * Initiates the player [mezzanotte], sets it to the team color and gives it a beginning location.
 * @param startLocation
 */
	public mezzanotte(Location startLocation) {
		super(startLocation);
		past.add(startLocation);
		setColor(Color.MAGENTA);
	}
/**
 * Defines the field i.e. gird and teams, flag locations, and current location of player [mezzanotte]
 * Uses proximity rules to determine optimal next location to move to.
 * @return next location to move to for player [mezzanotte]
 */
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
		if (gottem == true && me == true) {
			if (hasFlag() == false) {
				gottem = false;
			}
		} else if (gottem == true) {
			return goBack();
		} else if (hasFlag()) {
			me = true;
			return goBack();
		}

		else {
// Defines optimal next Location
			Location moneymove = cation.getAdjacentLocation(next);
//Checks for empty & valid Location, being the next.
			if (grid.isValid(moneymove) && otherTeam(moneymove) == false && !(grid.get(moneymove) instanceof Rock)
					&& !(grid.get(moneymove) instanceof mezzanotte) && !(grid.get(moneymove) instanceof aStar)) {
//Checks if NOT onSide
				if (!(team.onSide(moneymove))) {
					for (Location meek : tion) {
						ArrayList<Location> catty = grid.getOccupiedAdjacentLocations(cation);
						for (Location mill : catty) {
							if (!((grid.get(mill) instanceof mezzanotte) || (grid.get(mill) instanceof Rock)
									|| (grid.get(mill) instanceof aStar)))
//Eject self from situation if in proximity of other team on their side.
								return cation.getAdjacentLocation(cation.getDirectionToward(mill) + 45);
						}
					}
				}
				past.add(moneymove);
				return moneymove;

			} 
//If optimal location is invalid, choose another adjacent and empty location to move to.	
			else {
				if (tion.size() == 0)
					return getLocation();
				Location newt = tion.get((int) (Math.random() * tion.size()));
				past.add(newt);
				return newt;

			}

		}
		return cation;

	}


/**
 * 
 * @return the location before its current position
 */
	public Location goBack() {
		Location where = getLocation();
		if (past.size() > 0) {
			where = past.get(past.size() - 1);
			past.remove(past.size() - 1);
		}

		return where;
	}
/**
 * Given the current of following location, return false if safe.
 * @param now
 * @return if TRUE-- location is near other team, if false, location is safe.
 */
	public boolean otherTeam(Location now) {
		Grid grid = this.getGrid();
		Team team = this.getTeam();
		Team oteam = team.getOpposingTeam();
		ArrayList<Actor> caution = grid.getNeighbors(now);

		for (Actor seek : caution) {
//If not a harmless entity, seek caution.
			if (!(seek instanceof Rock || seek instanceof mezzanotte || seek instanceof aStar)) {

				return true;
			}
		}
		return false;

	}

}