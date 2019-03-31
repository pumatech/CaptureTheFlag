package org.pumatech.teams.Skynet;

import java.util.ArrayList;
import java.util.List;

import org.pumatech.CTF2018.AbstractPlayer;

import info.gridworld.grid.Location;

public class SkynetDupe extends AbstractPlayer {

	private Location T850Post = new Location(0, 0);
	private Location T1K1Post = new Location(0, 0);
	private Location T1K2Post = new Location(0, 0);

	public SkynetDupe(Location startLocation) {
		super(startLocation);
	}
	
	// Updates static variables of other players and defends the flag against
	// attackers
	int eo = 0;

	public Location getMoveLocation() {

		// set defense posts
		if (this.getTeam().getSide() == 0) {
			T850Post.setCol(this.getTeam().getFlag().getLocation().getCol() + 3);
			T850Post.setRow(this.getTeam().getFlag().getLocation().getRow());

			T1K1Post.setCol(this.getTeam().getFlag().getLocation().getCol() + 3);
			T1K1Post.setRow(this.getTeam().getFlag().getLocation().getRow() + 3);

			T1K2Post.setCol(this.getTeam().getFlag().getLocation().getCol() + 3);
			T1K2Post.setRow(this.getTeam().getFlag().getLocation().getRow() - 3);
		} else if (this.getTeam().getSide() == 1) {
			T850Post.setCol(this.getTeam().getFlag().getLocation().getCol() - 3);
			T850Post.setRow(this.getTeam().getFlag().getLocation().getRow());

			T1K1Post.setCol(this.getTeam().getFlag().getLocation().getCol() - 3);
			T1K1Post.setRow(this.getTeam().getFlag().getLocation().getRow() + 3);

			T1K2Post.setCol(this.getTeam().getFlag().getLocation().getCol() - 3);
			T1K2Post.setRow(this.getTeam().getFlag().getLocation().getRow() - 3);
		}

		// getting the indices of defensive players
		List<AbstractPlayer> b = this.getTeam().getPlayers();
		AbstractPlayer T1K1 = null;
		AbstractPlayer T1K2 = null;
		AbstractPlayer Arnold = null;
		for (AbstractPlayer p : b) {
			if (p instanceof T1K) {
				if (T1K1 != null) {
					T1K2 = p;
				} else {
					T1K1 = p;
				}
			}
			if (p instanceof T850) {
				Arnold = p;
			}
		}

		// importing enemy players into an ArrayList
		List<AbstractPlayer> enemyPlayers = this.getTeam().getOpposingTeam().getPlayers();

		// creating a array of distances from enemy players to flag
		// (and removing players not on our side)
		ArrayList<Integer> distances = new ArrayList<Integer>();

		ArrayList<AbstractPlayer> temp = new ArrayList<AbstractPlayer>(enemyPlayers);
		int flagc = this.getTeam().getFlag().getLocation().getCol();
		int flagr = this.getTeam().getFlag().getLocation().getRow();
		for (AbstractPlayer enemy : enemyPlayers) {
			if (this.getTeam().onSide(enemy.getLocation())) {
				int tem = (int) Math.sqrt(Math.pow(Math.abs(enemy.getLocation().getCol() - flagc), 2)
						+ Math.pow(Math.abs(enemy.getLocation().getRow() - flagr), 2));
				distances.add(tem);
			} else {
				temp.remove(enemy);
			}
		}
		enemyPlayers = temp;

		for (int i = 0; i < distances.size(); i++) {
			// Give T850 targets outside of 24 units from flag
			if (distances.get(i) > 24) {
				((T850) Arnold).addTarget(enemyPlayers.get(i));
				distances.remove(i);
			}
			// Give T1Ks targets within 24 units from flag according to proximity
			else {
				int d1 = (int) Math.sqrt(Math
						.pow(Math.abs(enemyPlayers.get(i).getLocation().getCol() - T1K1.getLocation().getCol()), 2)
						+ Math.pow(Math.abs(enemyPlayers.get(i).getLocation().getRow() - T1K1.getLocation().getCol()),
								2));
				int d2 = (int) Math.sqrt(Math
						.pow(Math.abs(enemyPlayers.get(i).getLocation().getCol() - T1K2.getLocation().getCol()), 2)
						+ Math.pow(Math.abs(enemyPlayers.get(i).getLocation().getRow() - T1K2.getLocation().getCol()),
								2));
				if (d1 <= d2) {
					((T1K) T1K1).addTarget(enemyPlayers.get(i));
					distances.remove(i);
				} else {
					((T1K) T1K2).addTarget(enemyPlayers.get(i));
					distances.remove(i);
				}
			}
		}

		// Give defenders a 'defense post' to return to when they have no more targets
		((T850) Arnold).setPost(T850Post);
		((T1K) T1K1).setPost(T1K1Post);
		((T1K) T1K2).setPost(T1K2Post);

		// Move back and forth
		Location a1 = new Location(0, 0);
		Location a2 = new Location(90, 40);
		if (eo % 2 <= 0) {
			eo++;
			return a1;
		} else {
			eo++;
		}
		return a2;
	}
}