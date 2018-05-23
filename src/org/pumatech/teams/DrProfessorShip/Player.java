package org.pumatech.teams.DrProfessorShip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;

//import com.sun.javafx.scene.traversal.Direction;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Player extends AbstractPlayer {
	public ArrayList<Location> hasbeen = new ArrayList<Location>();
	public static ArrayList<Boolean> flagon = new ArrayList<Boolean>();
	public static int index = 0;
	public Location last = new Location(0, 0);
	public ArrayList<Location> lastfew = new ArrayList<Location>();
	public static ArrayList<Location> where = new ArrayList<Location>();
	public boolean evade = false;
	public boolean jkrowling;
	private int[][] pathMap;
	public static ArrayList<Location> badloc = new ArrayList<Location>();
	private int plays;

	public Player(Location startLocation) {
		super(startLocation);
		hasbeen.clear();
		flagon.clear();
		lastfew.clear();
		where.clear();
		badloc.clear();
		jkrowling = false;
		last = new Location(0, 0);
		for (int i = 0; i < 8; i++) {
			flagon.add(false);
			lastfew.add(last);
			where.add(last);
		}
		pathMap = new int[50][100];

	}

	public Location getMoveLocation() {
		if (hasFlag() || friendlyFlag()) {
			Location wallSpot = getWallSpot();

			return fix(wallSpot);
		}

		Location got = getTeam().getOpposingTeam().getFlag().getLocation();

		return fix(got);
	}
/**
 * An alternate means for fix.
 * @param there
 * @return
 */
	public Location getOnOut(Location there) {

		Location got = there;

		return fix(got);
	}
/**
 * determines if static variables need to be reset. 
 * @return
 */
	public boolean nedRed() {
		Location flag = flagIt();
		int c = flag.getCol();
		Location oFlag = ourFlag();
		int col = oFlag.getCol();
		if (c == 50) {
			emote(true);
			return true;
		}
		if (col == 50) {
			emote(false);
			return true;
		}
		// if (plays>1000) {
		// plays = 0;
		// return true;
		// }
		return false;
	}
/**
 * Tested feature to emote upon victory or loss, not later implemented. 
 * @param brag
 */
	public void emote(boolean brag) {
		if (DrProfessorShipTheTeam.emotable) {
			if (brag) {
				System.out.println(
						"          _____                   _____                   _____                   _____                   _____                   _____                   _____                  \r\n"
								+ "         /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 \r\n"
								+ "        /::\\    \\               /::\\    \\               /::\\____\\               /::\\    \\               /::\\    \\               /::\\    \\               /::\\    \\                \r\n"
								+ "       /::::\\    \\             /::::\\    \\             /::::|   |              /::::\\    \\             /::::\\    \\              \\:::\\    \\             /::::\\    \\               \r\n"
								+ "      /::::::\\    \\           /::::::\\    \\           /:::::|   |             /::::::\\    \\           /::::::\\    \\              \\:::\\    \\           /::::::\\    \\              \r\n"
								+ "     /:::/\\:::\\    \\         /:::/\\:::\\    \\         /::::::|   |            /:::/\\:::\\    \\         /:::/\\:::\\    \\              \\:::\\    \\         /:::/\\:::\\    \\             \r\n"
								+ "    /:::/  \\:::\\    \\       /:::/__\\:::\\    \\       /:::/|::|   |           /:::/__\\:::\\    \\       /:::/__\\:::\\    \\              \\:::\\    \\       /:::/  \\:::\\    \\            \r\n"
								+ "   /:::/    \\:::\\    \\     /::::\\   \\:::\\    \\     /:::/ |::|   |          /::::\\   \\:::\\    \\     /::::\\   \\:::\\    \\             /::::\\    \\     /:::/    \\:::\\    \\           \r\n"
								+ "  /:::/    / \\:::\\    \\   /::::::\\   \\:::\\    \\   /:::/  |::|   | _____   /::::::\\   \\:::\\    \\   /::::::\\   \\:::\\    \\   ____    /::::::\\    \\   /:::/    / \\:::\\    \\          \r\n"
								+ " /:::/    /   \\:::\\ ___\\ /:::/\\:::\\   \\:::\\    \\ /:::/   |::|   |/\\    \\ /:::/\\:::\\   \\:::\\    \\ /:::/\\:::\\   \\:::\\____\\ /\\   \\  /:::/\\:::\\    \\ /:::/    /   \\:::\\    \\         \r\n"
								+ "/:::/____/  ___\\:::|    /:::/__\\:::\\   \\:::\\____/:: /    |::|   /::\\____/:::/__\\:::\\   \\:::\\____/:::/  \\:::\\   \\:::|    /::\\   \\/:::/  \\:::\\____/:::/____/     \\:::\\____\\        \r\n"
								+ "\\:::\\    \\ /\\  /:::|____\\:::\\   \\:::\\   \\::/    \\::/    /|::|  /:::/    \\:::\\   \\:::\\   \\::/    \\::/   |::::\\  /:::|____\\:::\\  /:::/    \\::/    \\:::\\    \\      \\::/    /        \r\n"
								+ " \\:::\\    /::\\ \\::/    / \\:::\\   \\:::\\   \\/____/ \\/____/ |::| /:::/    / \\:::\\   \\:::\\   \\/____/ \\/____|:::::\\/:::/    / \\:::\\/:::/    / \\/____/ \\:::\\    \\      \\/____/         \r\n"
								+ "  \\:::\\   \\:::\\ \\/____/   \\:::\\   \\:::\\    \\             |::|/:::/    /   \\:::\\   \\:::\\    \\           |:::::::::/    /   \\::::::/    /           \\:::\\    \\                     \r\n"
								+ "   \\:::\\   \\:::\\____\\      \\:::\\   \\:::\\____\\            |::::::/    /     \\:::\\   \\:::\\____\\          |::|\\::::/    /     \\::::/____/             \\:::\\    \\                    \r\n"
								+ "    \\:::\\  /:::/    /       \\:::\\   \\::/    /            |:::::/    /       \\:::\\   \\::/    /          |::| \\::/____/       \\:::\\    \\              \\:::\\    \\                   \r\n"
								+ "     \\:::\\/:::/    /         \\:::\\   \\/____/             |::::/    /         \\:::\\   \\/____/           |::|  ~|              \\:::\\    \\              \\:::\\    \\                  \r\n"
								+ "      \\::::::/    /           \\:::\\    \\                 /:::/    /           \\:::\\    \\               |::|   |               \\:::\\    \\              \\:::\\    \\                 \r\n"
								+ "       \\::::/    /             \\:::\\____\\               /:::/    /             \\:::\\____\\              \\::|   |                \\:::\\____\\              \\:::\\____\\                \r\n"
								+ "        \\::/____/               \\::/    /               \\::/    /               \\::/    /               \\:|   |                 \\::/    /               \\::/    /                \r\n"
								+ "          _____                  \\_____/                 \\_____/                 \\_____/                 \\_____                  \\_____/                 \\_____/                 \r\n"
								+ "         /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 /\\    \\                 \r\n"
								+ "        /::\\    \\               /::\\    \\               /::\\    \\               /::\\    \\               /::\\    \\               /::\\____\\               /::\\    \\                \r\n"
								+ "       /::::\\    \\             /::::\\    \\             /::::\\    \\             /::::\\    \\              \\:::\\    \\             /::::|   |              /::::\\    \\               \r\n"
								+ "      /::::::\\    \\           /::::::\\    \\           /::::::\\    \\           /::::::\\    \\              \\:::\\    \\           /:::::|   |             /::::::\\    \\              \r\n"
								+ "     /:::/\\:::\\    \\         /:::/\\:::\\    \\         /:::/\\:::\\    \\         /:::/\\:::\\    \\              \\:::\\    \\         /::::::|   |            /:::/\\:::\\    \\             \r\n"
								+ "    /:::/__\\:::\\    \\       /:::/__\\:::\\    \\       /:::/__\\:::\\    \\       /:::/  \\:::\\    \\              \\:::\\    \\       /:::/|::|   |           /:::/  \\:::\\    \\            \r\n"
								+ "   /::::\\   \\:::\\    \\     /::::\\   \\:::\\    \\     /::::\\   \\:::\\    \\     /:::/    \\:::\\    \\             /::::\\    \\     /:::/ |::|   |          /:::/    \\:::\\    \\           \r\n"
								+ "  /::::::\\   \\:::\\    \\   /::::::\\   \\:::\\    \\   /::::::\\   \\:::\\    \\   /:::/    / \\:::\\    \\   ____    /::::::\\    \\   /:::/  |::|   | _____   /:::/    / \\:::\\    \\          \r\n"
								+ " /:::/\\:::\\   \\:::\\ ___\\ /:::/\\:::\\   \\:::\\____\\ /:::/\\:::\\   \\:::\\    \\ /:::/    /   \\:::\\ ___\\ /\\   \\  /:::/\\:::\\    \\ /:::/   |::|   |/\\    \\ /:::/    /   \\:::\\ ___\\         \r\n"
								+ "/:::/__\\:::\\   \\:::|    /:::/  \\:::\\   \\:::|    /:::/  \\:::\\   \\:::\\____/:::/____/  ___\\:::|    /::\\   \\/:::/  \\:::\\____/:: /    |::|   /::\\____/:::/____/  ___\\:::|    |        \r\n"
								+ "\\:::\\   \\:::\\  /:::|____\\::/   |::::\\  /:::|____\\::/    \\:::\\  /:::/    \\:::\\    \\ /\\  /:::|____\\:::\\  /:::/    \\::/    \\::/    /|::|  /:::/    \\:::\\    \\ /\\  /:::|____|        \r\n"
								+ " \\:::\\   \\:::\\/:::/    / \\/____|:::::\\/:::/    / \\/____/ \\:::\\/:::/    / \\:::\\    /::\\ \\::/    / \\:::\\/:::/    / \\/____/ \\/____/ |::| /:::/    / \\:::\\    /::\\ \\::/    /         \r\n"
								+ "  \\:::\\   \\::::::/    /        |:::::::::/    /           \\::::::/    /   \\:::\\   \\:::\\ \\/____/   \\::::::/    /                  |::|/:::/    /   \\:::\\   \\:::\\ \\/____/          \r\n"
								+ "   \\:::\\   \\::::/    /         |::|\\::::/    /             \\::::/    /     \\:::\\   \\:::\\____\\      \\::::/____/                   |::::::/    /     \\:::\\   \\:::\\____\\            \r\n"
								+ "    \\:::\\  /:::/    /          |::| \\::/____/              /:::/    /       \\:::\\  /:::/    /       \\:::\\    \\                   |:::::/    /       \\:::\\  /:::/    /            \r\n"
								+ "     \\:::\\/:::/    /           |::|  ~|                   /:::/    /         \\:::\\/:::/    /         \\:::\\    \\                  |::::/    /         \\:::\\/:::/    /             \r\n"
								+ "      \\::::::/    /            |::|   |                  /:::/    /           \\::::::/    /           \\:::\\    \\                 /:::/    /           \\::::::/    /              \r\n"
								+ "       \\::::/    /             \\::|   |                 /:::/    /             \\::::/    /             \\:::\\____\\               /:::/    /             \\::::/    /               \r\n"
								+ "        \\::/____/               \\:|   |                 \\::/    /               \\::/____/               \\::/    /               \\::/    /               \\::/____/                \r\n"
								+ "         ~~                      \\|___|                  \\/____/                                         \\/____/                 \\/____/                                         \r\n"
								+ "                                                                                                                                                                                 ");
			} else {
				System.err.println("          _____                   _____                   _____          \r\n"
						+ "         /\\    \\                 /\\    \\                 /\\    \\         \r\n"
						+ "        /::\\    \\               /::\\    \\               /::\\    \\        \r\n"
						+ "       /::::\\    \\             /::::\\    \\             /::::\\    \\       \r\n"
						+ "      /::::::\\    \\           /::::::\\    \\           /::::::\\    \\      \r\n"
						+ "     /:::/\\:::\\    \\         /:::/\\:::\\    \\         /:::/\\:::\\    \\     \r\n"
						+ "    /:::/__\\:::\\    \\       /:::/__\\:::\\    \\       /:::/  \\:::\\    \\    \r\n"
						+ "    \\:::\\   \\:::\\    \\     /::::\\   \\:::\\    \\     /:::/    \\:::\\    \\   \r\n"
						+ "  ___\\:::\\   \\:::\\    \\   /::::::\\   \\:::\\    \\   /:::/    / \\:::\\    \\  \r\n"
						+ " /\\   \\:::\\   \\:::\\    \\ /:::/\\:::\\   \\:::\\    \\ /:::/    /   \\:::\\ ___\\ \r\n"
						+ "/::\\   \\:::\\   \\:::\\____/:::/  \\:::\\   \\:::\\____/:::/____/     \\:::|    |\r\n"
						+ "\\:::\\   \\:::\\   \\::/    \\::/    \\:::\\  /:::/    \\:::\\    \\     /:::|____|\r\n"
						+ " \\:::\\   \\:::\\   \\/____/ \\/____/ \\:::\\/:::/    / \\:::\\    \\   /:::/    / \r\n"
						+ "  \\:::\\   \\:::\\    \\              \\::::::/    /   \\:::\\    \\ /:::/    /  \r\n"
						+ "   \\:::\\   \\:::\\____\\              \\::::/    /     \\:::\\    /:::/    /   \r\n"
						+ "    \\:::\\  /:::/    /              /:::/    /       \\:::\\  /:::/    /    \r\n"
						+ "     \\:::\\/:::/    /              /:::/    /         \\:::\\/:::/    /     \r\n"
						+ "      \\::::::/    /              /:::/    /           \\::::::/    /      \r\n"
						+ "       \\::::/    /              /:::/    /             \\::::/    /       \r\n"
						+ "        \\::/    /               \\::/    /               \\::/____/        \r\n"
						+ "         \\_____/                 \\_____/           _____ ~~              \r\n"
						+ "         /\\    \\                 /\\    \\          |\\    \\                \r\n"
						+ "        /::\\    \\               /::\\    \\         |:\\____\\               \r\n"
						+ "       /::::\\    \\             /::::\\    \\        |::|   |               \r\n"
						+ "      /::::::\\    \\           /::::::\\    \\       |::|   |               \r\n"
						+ "     /:::/\\:::\\    \\         /:::/\\:::\\    \\      |::|   |               \r\n"
						+ "    /:::/  \\:::\\    \\       /:::/__\\:::\\    \\     |::|   |               \r\n"
						+ "   /:::/    \\:::\\    \\     /::::\\   \\:::\\    \\    |::|   |               \r\n"
						+ "  /:::/    / \\:::\\    \\   /::::::\\   \\:::\\    \\   |::|___|______         \r\n"
						+ " /:::/    /   \\:::\\ ___\\ /:::/\\:::\\   \\:::\\    \\  /::::::::\\    \\        \r\n"
						+ "/:::/____/     \\:::|    /:::/  \\:::\\   \\:::\\____\\/::::::::::\\____\\       \r\n"
						+ "\\:::\\    \\     /:::|____\\::/    \\:::\\  /:::/    /:::/~~~~/~~             \r\n"
						+ " \\:::\\    \\   /:::/    / \\/____/ \\:::\\/:::/    /:::/    /                \r\n"
						+ "  \\:::\\    \\ /:::/    /           \\::::::/    /:::/    /                 \r\n"
						+ "   \\:::\\    /:::/    /             \\::::/    /:::/    /                  \r\n"
						+ "    \\:::\\  /:::/    /              /:::/    /\\::/    /                   \r\n"
						+ "     \\:::\\/:::/    /              /:::/    /  \\/____/                    \r\n"
						+ "      \\::::::/    /              /:::/    /                              \r\n"
						+ "       \\::::/    /              /:::/    /                               \r\n"
						+ "        \\::/____/               \\::/    /                                \r\n"
						+ "         ~~                      \\/____/                                 \r\n"
						+ "                                                                         ");
			}
		}
	}
	/**
	 * This prevents static variables from being carried over.
	 */
	public void betterSafeThanSomething() {
		boolean jim = nedRed();
		if (jim) {
			hasbeen.clear();
			flagon.clear();
			lastfew.clear();
			where.clear();
			badloc.clear();
			jkrowling = false;
			last = new Location(0, 0);
			for (int i = 0; i < 8; i++) {
				flagon.add(false);
				lastfew.add(last);
				where.add(last);
			}
			pathMap = new int[50][100];
			reset();
		}
	}
/**
 * a method to be extended that is called at the end of every game.
 */
	public void reset() {

	}
/**
 * generates a presice path map to a given location.
 * @param spot
 */
	public void mapIt(Location spot) {
		Location flag = spot;
		List<AbstractPlayer> opposing = getTeam().getOpposingTeam().getPlayers();
		ArrayList<Location> rocks = getGrid().getOccupiedLocations();
		int sqLen = flag.getRow();
		int fX = flag.getCol();
		int fY = flag.getRow();
		Grid<Actor> g = getGrid();
		ArrayList<Location> contains = g.getOccupiedLocations();
		for (int i = 0; i < contains.size(); i++) {
			Location l = contains.get(i);
			if (g.get(l) instanceof Rock) {
				pathMap[l.getRow()][l.getCol()] = -1;
			}
		}
		ArrayList<ValuedLocation> locs = new ArrayList<ValuedLocation>();
		locs.add(new ValuedLocation(flag.getCol(), flag.getRow(), 1));
		while (locs.size() > 0) {
			ArrayList<ValuedLocation> tempLocs = new ArrayList<ValuedLocation>();
			for (int i = 0; i < locs.size(); i++) {
				ValuedLocation t = locs.get(i);
				int tempX = t.getX();
				int tempY = t.getY();
				int tempV = t.getV();
				if (tempX > 0) {
					if (pathMap[tempY][tempX - 1] == 0) {
						tempLocs.add(new ValuedLocation(tempX - 1, tempY, tempV + 1));
						pathMap[tempY][tempX - 1] = tempV + 1;
					}
				}
				if (tempX < 99) {
					if (pathMap[tempY][tempX + 1] == 0) {
						tempLocs.add(new ValuedLocation(tempX + 1, tempY, tempV + 1));
						pathMap[tempY][tempX + 1] = tempV + 1;
					}
				}
				if (tempY > 0) {
					if (pathMap[tempY - 1][tempX] == 0) {
						tempLocs.add(new ValuedLocation(tempX, tempY - 1, tempV + 1));
						pathMap[tempY - 1][tempX] = tempV + 1;
					}
				}
				if (tempY < 49) {
					if (pathMap[tempY + 1][tempX] == 0) {
						tempLocs.add(new ValuedLocation(tempX, tempY + 1, tempV + 1));
						pathMap[tempY + 1][tempX] = tempV + 1;
					}
				}
				if (tempX > 0 && tempY > 0) {
					if (pathMap[tempY - 1][tempX - 1] == 0) {
						tempLocs.add(new ValuedLocation(tempX - 1, tempY - 1, tempV + 1));
						pathMap[tempY - 1][tempX - 1] = tempV + 1;
					}
				}
				if (tempX > 0 && tempY < 49) {
					if (pathMap[tempY + 1][tempX - 1] == 0) {
						tempLocs.add(new ValuedLocation(tempX - 1, tempY + 1, tempV + 1));
						pathMap[tempY + 1][tempX - 1] = tempV + 1;
					}
				}
				if (tempX < 99 && tempY < 49) {
					if (pathMap[tempY + 1][tempX + 1] == 0) {
						tempLocs.add(new ValuedLocation(tempX + 1, tempY + 1, tempV + 1));
						pathMap[tempY + 1][tempX + 1] = tempV + 1;
					}
				}
				if (tempX < 99 && tempY > 0) {
					if (pathMap[tempY - 1][tempX + 1] == 0) {
						tempLocs.add(new ValuedLocation(tempX + 1, tempY - 1, tempV + 1));
						pathMap[tempY - 1][tempX + 1] = tempV + 1;
					}
				}
			}
			locs = new ArrayList<ValuedLocation>();
			locs = (ArrayList<ValuedLocation>) tempLocs.clone();
		}
		for (int i = 0; i < opposing.size(); i++) {
			Location temp = opposing.get(i).getLocation();
			pathMap[temp.getRow()][temp.getCol()] = 1000;
		}
		pathMap[flag.getRow()][flag.getCol()] = 1;
	}
/**
 * finds the path map to the flag.
 * @return
 */
	public Location pathMapFlag() {
		return findSpecialMap(flagIt());
	}
/**
 * Determines if an attacker is close enough to attack
 * @return
 */
	public boolean nearme() {
		badloc.clear();
		ArrayList<Location> noFriend = enemies();
		boolean near = false;
		for (Location ths : noFriend) {
			int tr = ths.getRow();
			int tc = ths.getCol();
			int mr = this.getLocation().getRow();
			int mc = this.getLocation().getCol();
			int r = Math.abs(tr - mr);
			int c = Math.abs(tc - mc);
			boolean one = r < 10;
			boolean two = c < 10;
			double diag = Math.sqrt((r * r) + (c * c));
			boolean thr = diag < 10;
			if ((one && two) || thr) {
				near = true;
				badloc.add(ths);
			}
		}
		return near;

	}
/**
 * this is a primairy defense target
 * @return
 */
	public Location target() {
		boolean worthit = nearme();
		Location prior = new Location(0, 0);
		int sr = 100;
		int sc = 100;
		int sd = 100;
		int st = 10000;
		if (worthit) {
			for (Location ths : badloc) {
				int tr = ths.getRow();
				int tc = ths.getCol();
				int mr = this.getLocation().getRow();
				int mc = this.getLocation().getCol();
				int r = Math.abs(tr - mr);
				int c = Math.abs(tc - mc);
				double diag = Math.sqrt((r * r) + (c * c));
				int d = (int) diag;
				int t = r + c + d;
				if (t < st) {
					st = t;
					prior = ths;
				}
			}
		}
		return prior;
	}
/**
 * This is a secondary deffense target
 * @return
 */
	public Location sectarget() {
		ArrayList<Location> jope = badloc;
		jope.remove(target());
		boolean worthit = nearme();
		Location prior = new Location(0, 0);
		int sr = 100;
		int sc = 100;
		int sd = 100;
		int st = 10000;
		if (worthit) {
			for (Location ths : jope) {
				int tr = ths.getRow();
				int tc = ths.getCol();
				int mr = this.getLocation().getRow();
				int mc = this.getLocation().getCol();
				int r = Math.abs(tr - mr);
				int c = Math.abs(tc - mc);
				double diag = Math.sqrt((r * r) + (c * c));
				int d = (int) diag;
				int t = r + c + d;
				if (t < st) {
					st = t;
					prior = ths;
				}
			}
		}
		return prior;
	}
	/**
	 * this generates an advanced path map to a given Location
	 * @param locationz
	 * @return
	 */
	public Location findSpecialMap(Location locationz) {
		Location l = this.getLocation();
		mapIt(locationz);
		int x = l.getCol();
		int y = l.getRow();
		Location loc = new Location(x, y);
		int num = pathMap[y][x];
		Grid<Actor> g = getGrid();
		ArrayList<Location> spots = g.getEmptyAdjacentLocations(l);
		for (int i = 0; i < spots.size(); i++) {
			if (this.hasFlag()) {
			} else {
				if (pathMap[spots.get(i).getRow()][spots.get(i).getCol()] <= num) {
					loc = spots.get(i);
					num = pathMap[spots.get(i).getRow()][spots.get(i).getCol()];
				}
			}
		}
		return loc;
	}
	/**
	 * This fixes a location as to avoid rocks and runs a simple update. 
	 * @param place
	 * @return
	 */
	public Location fix(Location place) {
		Location find = avoidRocks(place);
		runit(find);
		return find;
	}
	/**
	 * This finds the Location inbetween a target location and an actor.
	 * @param there
	 * @return
	 */
	public Location inBet(Location there) {
		int boid = this.getLocation().getDirectionToward(there);
		Location choice = this.getLocation().getAdjacentLocation(boid);
		return choice;
	}

	/**
	 * ALWAYS RUN THIS, IT KEEPS THE CODE HAPPY AND HEALTHY! JUST RUN IT IN
	 * getMoveLocation!!
	 * 
	 * @param there
	 */
	public void runit(Location there) {
		if (DrProfessorShipTheTeam.lickthesoap) {
			jkrowling = false;
			DrProfessorShipTheTeam.lickthesoap = false;
		}
		hasbeen.add(there);
		last = this.getLocation();
		flagUpdate();
		indexUpdate();
		prevTrap(there);
		where.set(index, last);
		if (jkrowling == false) {
			instate();
			jkrowling = true;
		}
		plays++;
		betterSafeThanSomething();
	}
/**
 * returns the location of the flag.
 * @return
 */
	public Location flagIt() {
		Location got = getTeam().getOpposingTeam().getFlag().getLocation();
		return got;
	}
/**
 * 
 * @return an ArrayList of all the locations containing enemy players. 
 */
	public ArrayList<Location> enemies() {
		ArrayList<Location> mark = new ArrayList<Location>();
		for (int r = 0; r < 50; r++) {
			for (int c = 0; c < 100; c++) {
				Location loc = new Location(r, c);
				Actor poten = getGrid().get(loc);
				boolean ab = poten instanceof Rock;
				boolean bb = poten instanceof GreenBoi;
				boolean cb = poten instanceof MulletMan;
				boolean db = poten instanceof Pinky;
				boolean eb = poten instanceof RandoDude;

				boolean chk = ab || bb || cb || db || eb;
				boolean atall = poten instanceof AbstractPlayer;
				if (!chk && atall) {
					mark.add(loc);
				}
			}
		}
		return mark;
	}
/**
 * 
 * @return the location of our flag. 
 */
	public Location ourFlag() {
		Location got = getTeam().getFlag().getLocation();
		return got;
	}
	/**
	 * keeps a list of all the past few locations updated. 
	 */
	public void lasties() {
		lastfew.remove(0);
		lastfew.add(last);
	}
/**
 * Prevents traps from occuring. 
 * @param select
 */
	public void prevTrap(Location select) {
		if (timeLast(select) > 3) {
			hasbeen.clear();
			evade = true;
		}
	}

	// public Location evade(Location place) {
	// if (evade) {
	// double rad = Math.random();
	// } else {
	// return place;
	// }
	// }
	/**
	 * updates the index of a specific player.
	 */
	public void indexUpdate() {
		if (index < 7) {
			index++;
		} else {
			index = 0;
		}
	}
	/**
	 * to be extended later as an instating method
	 */
	public void instate() {

	}
/**
 * updates players who have the flag. 
 */
	public void flagUpdate() {
		flagon.set(index, hasFlag());
	}
/**
 * checks if a location occurs in a list of recent locations. 
 * @param sel
 * @return
 */
	public boolean inLast(Location sel) {
		for (Location tmp : lastfew) {
			boolean use = tmp.equals(sel);
			if (use) {
				return true;
			}
		}
		return false;
	}
/**
 * returns the number of times a location has been visited in the last few list. 
 * @param sel
 * @return
 */
	public int timeLast(Location sel) {
		int i = 0;
		for (Location tmp : lastfew) {
			boolean use = tmp.equals(sel);
			if (use) {
				i++;
			}
		}
		return i;
	}
/**
 * determines if a friend has a flag.
 * @return
 */
	public boolean friendlyFlag() {
		for (Boolean tmp : flagon) {
			boolean use = (boolean) tmp;
			if (use) {
				return true;
			}
		}
		return false;
	}
/**
 * finds the location directly on the wall closest to a player. 
 * @return
 */
	public Location getWallSpot() {
		Location here = this.getLocation();
		int row = here.getRow();
		int r = row;
		int c = 50 + account();
		Location there = new Location(r, c);
		return there;
	}
/**
 * checks to see if a rock is in a location
 * @param spot
 * @return
 */
	public boolean checkRock(Location spot) {
		Actor poten = getGrid().get(spot);
		ArrayList<Location> lst = getGrid().getOccupiedAdjacentLocations(this.getLocation());
		ArrayList<Location> list = getGrid().getEmptyAdjacentLocations(this.getLocation());
		boolean ne = poten instanceof Rock;
		// boolean pie = poten instanceof Player;
		boolean jim = false;
		for (Location tmp : list) {
			if (jim == false) {
				jim = tmp.equals(spot);
			}
		}
		return ne;
	}
/**
 * sees if a location is in a list of locations. 
 * @param lst
 * @param boi
 * @return
 */
	public boolean Locin(ArrayList<Location> lst, Location boi) {
		boolean jim = false;
		for (Location tmp : lst) {
			if (jim == false) {
				jim = tmp.equals(boi);
			}
		}
		return jim;
	}
/**
 * tests a location to see if its valid based on row and column 
 * @param r
 * @param c
 * @return
 */
	public boolean loctst(int r, int c) {
		ArrayList<Location> list = getGrid().getEmptyAdjacentLocations(this.getLocation());
		Location spt = new Location(r, c);
		boolean jim = this.getGrid().isValid(spt);
		return Locin(list, spt) && !checkRock(spt) && jim && !hasBeen(spt);
	}
/**
 * tests a location to see if its valid based on the Location. 
 * @param loc
 * @return
 */
	public boolean loctst(Location loc) {
		int r = loc.getRow();
		int c = loc.getCol();
		ArrayList<Location> list = getGrid().getEmptyAdjacentLocations(this.getLocation());
		Location spt = new Location(r, c);
		boolean jim = this.getGrid().isValid(spt);
		return Locin(list, spt) && !checkRock(spt) && jim && !hasBeen(spt);
	}
/**
 * This method accounts for which side one is on. 
 * @return
 */
	public int account() {
		Location here = this.getLocation();
		boolean flag = hasFlag();
		int row = here.getRow();
		int col = here.getCol();
		Location got = this.getTeam().getOpposingTeam().getFlag().getLocation();
		int coll = got.getCol();
		int l;
		boolean twards = hasFlag();
		if (coll > col) {
			l = 1;
		} else {
			l = -1;
		}
		if (twards == true) {
			l = l;
		} else {
			l = l * -1;
		}
		return l;
	}
/**
 * A second method to account for sides in particular situations. 
 * @return
 */
	public int accounted() {
		Location here = this.getLocation();
		int col = here.getCol();
		Location got = this.getTeam().getOpposingTeam().getFlag().getLocation();
		int coll = got.getCol();
		int l;
		if (coll > col) {
			l = 1;
		} else {
			l = -1;
		}
		return l;
	}
/**
 * determines if a player has previously visited a location. 
 * @param there
 * @return
 */
	public boolean hasBeen(Location there) {
		for (Location here : hasbeen) {
			if (here.equals(there)) {
				return true;
			}
		}
		return false;
	}
/**
 * avoids rocks...
 * @param chice
 * @return
 */
	public Location avoidRocks(Location chice) {
		int boid = this.getLocation().getDirectionToward(chice);
		Location choice = this.getLocation().getAdjacentLocation(boid);
		Location find = choice;
		boolean jim = checkRock(choice);
		if (jim == true) {
			// System.out.println("Rocks.io");
			Location here = this.getLocation();
			int row = here.getRow();
			int col = here.getCol();
			Location spot;
			// Location got = getTeam().getOpposingTeam().getFlag().getLocation();
			// int coll = got.getCol();
			// int l;
			// if (coll>col) {
			// l = 1;
			// } else {
			// l = -1;
			// }
			int l;
			l = account();
			Location ri = new Location(row, col + l);
			Location br = new Location(row + l, col + l);
			Location bo = new Location(row + l, col);
			Location bl = new Location(row + l, col - l);
			Location lf = new Location(row, col - l);
			Location tl = new Location(row - l, col - l);
			Location to = new Location(row - l, col);
			Location tr = new Location(row - l, col + l);

			// APArrayList joe = new APArrayList();
			Location one = ri;
			Location two = br;
			Location thr = tr;
			Location fou = bo;
			Location fiv = to;
			Location six = bl;
			Location sev = tl;
			Location eig = lf;

			boolean tru1 = loctst(one);
			if (tru1 == false) {
				boolean tru2 = loctst(two);
				if (tru2 == false) {
					boolean tru3 = loctst(thr);
					if (tru3 == false) {
						boolean tru4 = loctst(fou);
						if (tru4 == false) {
							boolean tru5 = loctst(fiv);
							if (tru5 == false) {
								boolean tru6 = loctst(six);
								if (tru6 == false) {
									boolean tru7 = loctst(sev);
									if (tru7 == false) {
										boolean tru8 = loctst(eig);
										if (tru8 == false) {
											// System.out.println("WHOMNST??");
											hasbeen.clear();
											spot = new Location(row, col);

										} else {
											spot = eig;
										}
									} else {
										spot = sev;
									}
								} else {
									spot = six;
								}
							} else {
								spot = fiv;
							}
						} else {
							spot = fou;
						}
					} else {
						spot = thr;
					}
				} else {
					spot = two;
				}
			} else {
				spot = one;
			}
			find = spot;
		}

		return find;
	}
}
