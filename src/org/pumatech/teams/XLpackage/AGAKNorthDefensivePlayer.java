package org.pumatech.teams.XLpackage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;
import org.pumatech.ctf.Flag;
import org.pumatech.ctf.Team;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class AGAKNorthDefensivePlayer extends AbstractPlayer {
	private int i = 0;

	public AGAKNorthDefensivePlayer(Location startLocation) {
		super(startLocation);
		this.setColor(Color.WHITE);
	}

	private boolean theyHaveFlag() {
		Team myTeam = this.getTeam();
		Team theirTeam = myTeam.getOpposingTeam();
		List<AbstractPlayer> theirPlayers = theirTeam.getPlayers();

		for (int i = 0; i < theirPlayers.size(); i++) {
			if (theirPlayers.get(i).hasFlag()) {
				return true;
			}
		}
		return false;
	}

	private AbstractPlayer whoHasFlag() {
		Team myTeam = this.getTeam();
		Team theirTeam = myTeam.getOpposingTeam();
		List<AbstractPlayer> theirPlayers = theirTeam.getPlayers();

		for (int i = 0; i < theirPlayers.size(); i++) {
			if (theirPlayers.get(i).hasFlag()) {
				return theirPlayers.get(i);
			}
		}
		return null;
	}

	private boolean onOurSide() {
		Team myTeam = this.getTeam();
		Team theirTeam = myTeam.getOpposingTeam();
		List<AbstractPlayer> theirPlayers = theirTeam.getPlayers();

		for (int i = 0; i < theirPlayers.size(); i++) {
			if (myTeam.onSide(theirPlayers.get(i).getLocation())
					&& theirPlayers.get(i).getLocation().getRow() < getGrid().getNumRows() / 2) {
				return true;
			}
		}
		return false;
	}

	private AbstractPlayer whoOnNorthSide() {
		Team myTeam = this.getTeam();
		Team theirTeam = myTeam.getOpposingTeam();
		List<AbstractPlayer> theirPlayers = theirTeam.getPlayers();

		for (int i = 0; i < theirPlayers.size(); i++) {
			if (myTeam.onSide(theirPlayers.get(i).getLocation())
					&& theirPlayers.get(i).getLocation().getRow() < getGrid().getNumRows() / 2) {
				return theirPlayers.get(i);
			}
		}
		return null;
	}

	public Location getMoveLocation() {
		long startTime = System.currentTimeMillis();
		Location loc = this.getLocation();
		Grid myGrid = this.getGrid();
		Team myTeam = this.getTeam();
		Team theirTeam = myTeam.getOpposingTeam();
		List<AbstractPlayer> theirPlayers = theirTeam.getPlayers();
		Flag theirFlag = theirTeam.getFlag();
		List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());

		Location opp;
		if (theyHaveFlag()) {
			this.setDirection(loc.getDirectionToward(whoHasFlag().getLocation()));
			opp = loc.getAdjacentLocation(loc.getDirectionToward(whoHasFlag().getLocation()));
			if (possibleMoveLocations.indexOf(opp) >= 0) {
				return opp;
			}
		}
		if (onOurSide()) {
			this.setDirection(loc.getDirectionToward(whoOnNorthSide().getLocation()));
			opp = loc.getAdjacentLocation(loc.getDirectionToward(whoOnNorthSide().getLocation()));
			if (possibleMoveLocations.indexOf(opp) >= 0) {
				return opp;
			}
		}
		return upDown();
	}

	private boolean southValid() {
		Location south = this.getLocation().getAdjacentLocation(Location.SOUTH);
		if ((this.getGrid().isValid(south))) {
			if ((this.getGrid().get(south) == null)) {
				return true;
			}
		}
		return false;
	}

	private boolean northValid() {
		Location north = this.getLocation().getAdjacentLocation(Location.NORTH);
		if ((this.getGrid().isValid(north))) {
			if ((this.getGrid().get(north) == null)) {
				return true;
			}
		}
		return false;
	}

	public Location upDown() {
		Location south = this.getLocation().getAdjacentLocation(Location.SOUTH);
		Location north = this.getLocation().getAdjacentLocation(Location.NORTH);

		if (i % 2 == 0) {
			i++;
			if (southValid()) {
				return south;
			}
		} else {
			i++;
			if (northValid()) {
				return north;
			}
		}
		List<Location> possibleMoveLocations = getGrid().getEmptyAdjacentLocations(getLocation());
		if (possibleMoveLocations.size() == 0)
			return null;
		return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));

	}

}
