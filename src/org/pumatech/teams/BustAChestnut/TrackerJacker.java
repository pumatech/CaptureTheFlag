package org.pumatech.teams.BustAChestnut;

import java.util.List;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public class TrackerJacker extends AbstractPlayer {

	protected List<AbstractPlayer> defenseTeam;
	protected int[][] blackList;

	public TrackerJacker(Location startLocation) {
		super(startLocation);
		blackList = new int[50][100];
	}

	@Override
	public Location getMoveLocation() {
		AbstractPlayer target = getTarget();
		Location targetLoc;
		if (target == null) {
			targetLoc = this.getTeam().getFlag().getLocation();
		} else {
			targetLoc = target.getLocation();
		}
		Location moveLoc = getBestMove(targetLoc);
		blackList[moveLoc.getRow()][moveLoc.getCol()]++;
		return moveLoc;
	}

	public AbstractPlayer getTarget() {
		double minDistance = 10000;
		AbstractPlayer closestEnemy = null;
		Location flagLoc = this.getTeam().getFlag().getLocation();

		for (AbstractPlayer enemy : this.getTeam().getOpposingTeam().getPlayers()) {
			if (onMySide(enemy.getLocation())) {
				int enemyRow = enemy.getLocation().getRow();
				int enemyCol = enemy.getLocation().getCol();
				int rowDiff = enemyRow - flagLoc.getRow();
				int colDiff = enemyCol - flagLoc.getCol();
				double dist = Math.sqrt(Math.pow(rowDiff, 2) + Math.pow(colDiff, 2));
				if (dist < minDistance) {
					dist = minDistance;
					closestEnemy = enemy;
				}
			}
		}
		return closestEnemy;
	}

	public boolean onMySide(Location loc) {
		if (this.getTeam().getSide() == 0) {
			return loc.getCol() < 50;
		} else {
			return loc.getCol() > 50;
		}
	}

	public Location getBestMove(Location goalLoc) {
		List<Location> emptyAdjacent = this.getGrid().getEmptyAdjacentLocations(this.getLocation());
		Location bestLoc = null;
		double highScore = -10000;
		for (Location location : emptyAdjacent) {
			double score = getScore(location, goalLoc);
			if (score > highScore) {
				highScore = score;
				bestLoc = location;
			}
		}
		return bestLoc;
	}

	public double getScore(Location loc, Location goalLoc) {
		double distance = Math.hypot(loc.getRow() - goalLoc.getRow(), loc.getCol() - goalLoc.getCol());
		double distanceScore = 1 / distance;
		int numVisits = blackList[loc.getRow()][loc.getCol()];
		double visitBadness = 1;
		if (numVisits != 0) {
			visitBadness = 1.0 / blackList[loc.getRow()][loc.getCol()];
		}
		return distanceScore * visitBadness;
	}
}