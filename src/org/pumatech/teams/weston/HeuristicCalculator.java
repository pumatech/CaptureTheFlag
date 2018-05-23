package org.pumatech.teams.weston;

import info.gridworld.grid.Location;

public interface HeuristicCalculator {
	public int getValue(Location loc);
	public boolean destIsFlag();
}
