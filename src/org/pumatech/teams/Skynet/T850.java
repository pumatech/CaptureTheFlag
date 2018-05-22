package org.pumatech.teams.Skynet;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;

import info.gridworld.grid.Location;

public class T850 extends MovingPlayer {

	// processes a list of targets and moves to intercept, then returns
	// to the flag if there are no more targets given to it by Skynet

	private ArrayList<AbstractPlayer> targets = new ArrayList<AbstractPlayer>();
	private Location post;
	private Location pastLocation;

	public T850(Location startLocation) {
		super(startLocation);
	}

	public Location getMoveLocation() {
		// eliminate targets not on side
		ArrayList<AbstractPlayer> temp = new ArrayList<AbstractPlayer>(targets);
		for (AbstractPlayer enemy : targets) {
			if (!this.getTeam().onSide(enemy.getLocation())) {
				temp.remove(enemy);
			}
		}
		targets = temp;

		// path finding
		List<Location> possibleMoveLocations = this.getGrid().getEmptyAdjacentLocations(getLocation());
		if (possibleMoveLocations.size() <= 0) {
			return null;
		}
		if (targets.size() > 0) {
			return avoid(possibleMoveLocations, targets.get(0).getLocation());
		} else if (post != null) {
			return avoid(possibleMoveLocations, post);
		} else {
			return this.getLocation();
		}
	}

	public void addTarget(AbstractPlayer targ) {
		targets.add(targ);
	}

	public void setPost(Location def) {
		post = def;
	}

	public Location avoid(List<Location> scan, Location target) {
		// do nothing if there are no empty locations
		if (scan.size() <= 0) {
			return this.getLocation();
		}
		// prevent concurrent modification exceptions
		ArrayList<Location> temp = new ArrayList<Location>(scan);
		for (Location test : scan) {
			// remove blacklisted options
			if (locationBlacklist.contains(test)) {
				temp.remove(test);
			}
		}
		// update scan
		scan = temp;

		// do nothing if there are no safe locations
		if (scan.size() <= 0) {
			return this.getLocation();
		}

		// determine optimal location based on direction
		int minDir = 360;
		Location best = scan.get(0);
		for (Location l : scan) {
			int a = this.getLocation().getDirectionToward(l);
			int t = this.getLocation().getDirectionToward(target);
			if (Math.abs(t - a) < minDir) {
				if (this.getGrid().getEmptyAdjacentLocations(l).size() > 1) {
					if (!locationBlacklist.contains(l) && !l.equals(pastLocation)) {
						best = l;
					}
				}
				minDir = Math.abs(t - a);
			}
		}
		// blacklist unsuitable locations
		if (Math.abs(
				this.getLocation().getDirectionToward(best) - this.getLocation().getDirectionToward(target)) >= 90) {
			if (!locationBlacklist.contains(this.getLocation())) {
				locationBlacklist.add(this.getLocation());
			}
		}
		if (best.equals(pastLocation)) {
			// blacklist to prevent moving back
			if (!locationBlacklist.contains(best)) {
				locationBlacklist.add(best);
			}
			// take a step back
			if (target.getCol() > this.getLocation().getCol()) {
				if (this.getLocation().getCol() > 0) {
					Location stepBack = new Location(this.getLocation().getCol() - 1, this.getLocation().getRow());
					if (scan.contains(stepBack)) {
						best = stepBack;
					}
				}
			}
			if (target.getCol() < this.getLocation().getCol()) {
				if (this.getLocation().getCol() < this.getGrid().getNumCols()) {
					Location stepBack = new Location(this.getLocation().getCol() + 1, this.getLocation().getRow());
					if (scan.contains(stepBack)) {
						best = stepBack;
					}
				}
			}
		}
		// update pastLocation and return
		pastLocation = this.getLocation();
		return best;
	}
}
