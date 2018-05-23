package org.pumatech.teams.BustAChestnut;
import java.util.List;
import org.pumatech.ctf.*;
import info.gridworld.grid.*;
public class MiscMethods {

	public Location getBestMove (List <Location> locs, Location flag) {
		double bestScore = 999;
		Location bestMove = null;
		int flagRow = flag.getRow();
		int flagCol = flag.getCol();
		for (int i = 0; i < locs.size(); i++) {
			int rowDiff = locs.get(i).getRow() - flagRow;
			int colDiff = locs.get(i).getCol() - flagCol;
			double dist = Math.sqrt(Math.pow(rowDiff, 2) + Math.pow(colDiff, 2));
			// Lower scores are better
			if (dist < bestScore) {
				bestScore = dist;
				bestMove = locs.get(i);
			}
		}
		
		return bestMove;
	}

	
}
