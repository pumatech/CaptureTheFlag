package org.pumatech.teams.BestTeam;

import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Dfence extends AbstractPlayer {
	private static boolean top = false;
	private static boolean left = false;
	private int where;
	private Grid<Actor> gr;

	public Dfence(Location startLocation) {
		super(startLocation);
		if (!top) {
			top = true;
			where = 0;
		} else if (!left) {
			left = true;
			where = 1;
		} else {
			where = -1;
		}
	}

	public Location getMoveLocation() {
		gr = this.getGrid();
		Location flag = this.getTeam().getFlag().getLocation();
		if (gr.get(flag) instanceof AbstractPlayer) {
			Pathfind p = new Pathfind(flag, this.getLocation(), this);
			return p.nextMove();
		} else if (where == 0 && (this.getLocation().getRow() - flag.getRow() > 2
				|| this.getLocation().getRow() - flag.getRow() < -2)) {
			Pathfind p = new Pathfind(new Location(flag.getRow(), this.getLocation().getCol()), this.getLocation(), this);
			return p.nextMove();
		} else if (where == 0) {
			Pathfind p = new Pathfind(flag, this.getLocation(), this);
			return p.nextMove();
		} else if (where == 1 && (this.getLocation().getCol() - flag.getCol() > 2
				|| this.getLocation().getCol() - flag.getCol() < -2)) {
			Pathfind p = new Pathfind(new Location(this.getLocation().getRow(), flag.getCol()), this.getLocation(), this);
			return p.nextMove();
		} else if (where == 1) {
			Pathfind p = new Pathfind(flag, this.getLocation(), this);
			return p.nextMove();
		} else if (this.getLocation().getRow() - flag.getRow() < -2) {
			Pathfind p = new Pathfind(new Location(flag.getRow(), this.getLocation().getCol()), this.getLocation(), this);
			return p.nextMove();
		} else if (flag.getCol() >= 50 && this.getLocation().getCol() < 50) {
			Pathfind p = new Pathfind(new Location(this.getLocation().getRow(), flag.getCol()), this.getLocation(), this);
			return p.nextMove();
		} else if (flag.getCol() < 50 && this.getLocation().getCol() >= 50) {
			Pathfind p = new Pathfind(new Location(this.getLocation().getRow(), flag.getCol()), this.getLocation(), this);
			return p.nextMove();
		} else {
			return this.shake();
		}

	}

	public Location shake() {
		List<Location> possibleMoveLocations = gr.getEmptyAdjacentLocations(this.getLocation());
		if (possibleMoveLocations.size() == 0)
			return null;
		return possibleMoveLocations.get((int) (Math.random() * possibleMoveLocations.size()));
	}

}
