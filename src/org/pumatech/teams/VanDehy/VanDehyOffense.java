package org.pumatech.teams.VanDehy;

import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.grid.Location;

public class VanDehyOffense extends VanDehyPlayer {
	public VanDehyOffense(Location startLocation) {
		super(startLocation);
	}
	@Override
	public Location getMoveLocation() {
		Location gol = getTeam().getOpposingTeam().getFlag().getLocation();
		if(getTeam().onSide(getLocation()) && getGrid().get(getTeam().getFlag().getLocation()) instanceof AbstractPlayer){
			gol = getTeam().getFlag().getLocation();
		}
		Node goal = new Node(gol.getRow(), gol.getCol());
		Node start = new Node();
		start.setLocation(this.getLocation());
		start.setH(start.findH(goal));
		start.setG(0);
		start.setF(start.findF());
		if (hasFlag()) {
			gol = getTeam().getFlag().getLocation();
			goal.setRow(gol.getRow());
			goal.setCol(gol.getCol());
		}
		Node a = aStar(start, goal);
		Location loc = start.getLocation();
		try{
			loc = recurParent(a).getLocation();
		}
		catch(NullPointerException e){
		}
		return loc;
	}
	@Override
	public int calculateMovementCost(Node to) {
		int sum = 1;
		Location end = to.getLocation();
		List<Location> enemyLocs = getEnemyLocations();
		for (int i = 0; i < enemyLocs.size(); i++) {
			if (!this.getTeam().onSide(enemyLocs.get(i))) {
				// the minimum steps in a straight line from enemyLoc to end
				sum += 8.0/(Integer.max(Math.abs(end.getCol() - enemyLocs.get(i).getCol()),
						Math.abs(end.getRow() - enemyLocs.get(i).getRow())));
			}
		}
		return sum;
	}
}
