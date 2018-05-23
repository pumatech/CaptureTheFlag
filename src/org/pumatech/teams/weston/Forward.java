package org.pumatech.teams.weston;


import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public class Forward extends AbstractPlayer {
	public static boolean runnerHasFlag = false;
	
	private boolean carryingFlag;
	private PathFinder pathFinder;
	
	public Forward(Location startLocation) {
		super(startLocation);
		pathFinder = new PathFinder(this);
	}

	public Location getMoveLocation() {
		if (hasFlag() && !carryingFlag) {
			carryingFlag = true;
			runnerHasFlag = true;
		} else if (!hasFlag() && carryingFlag) {
			carryingFlag = false;
			runnerHasFlag = false;
		}
		
		final int teamNum = getTeam().getSide();
		if (runnerHasFlag && !getTeam().onSide(getLocation())) {
			return pathFinder.getNextLocation(getLocation(), new HeuristicCalculator() {
				public int getValue(Location loc) {
					if (teamNum == 0)
						return loc.getCol() - getGrid().getNumCols() / 2 + 1;
					else
						return getGrid().getNumCols() / 2 - loc.getCol() - 1;
				}
				
				public boolean destIsFlag() {
					return false;
				}
			});
		} else {
			HeuristicCalculator distanceOnOpponentSide = new HeuristicCalculator() {
				public int getValue(Location loc) {
					if (teamNum == 0)
						return getGrid().getNumCols() - loc.getCol() - 1;
					else
						return loc.getCol();
				}

				public boolean destIsFlag() {
					return false;
				}
			};
			if (distanceOnOpponentSide.getValue(getLocation()) > distanceOnOpponentSide.getValue(getTeam().getOpposingTeam().getFlag().getLocation()) + 10) {
				return pathFinder.getNextLocation(getLocation(), distanceOnOpponentSide);
			} else {
				return pathFinder.getNextLocation(getLocation(), getTeam().getOpposingTeam().getFlag().getLocation());
			}
		}
	}
}