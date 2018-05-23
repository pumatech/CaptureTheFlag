package org.pumatech.teams.BestTeam;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.pumatech.ctf.AbstractPlayer;
import org.pumatech.ctf.Flag;
import org.pumatech.ctf.Team;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Ofence extends AbstractPlayer
{
	private Grid<Actor> myGrid;
	private Team myTeam;
	private Team theirTeam;
	private Flag myFlag;
	private Flag theirFlag;
	private Location myLoc;
	private ArrayList<Location> storedPath;
	private int interval;
//	static boolean anyoneFlag;
	
	public Ofence(Location startLocation, int interval)
	{
	super(startLocation);
	this.interval = interval;
	this.setColor(Color.GREEN);;
//	anyoneFlag = false;
	}
	
	public ArrayList<Location> Astar(Location start, Location goal)
	{
	// The set of nodes already evaluated
	// closedSet := {}
	ArrayList<Location> closedSet = new ArrayList<Location>();

	// The set of currently discovered nodes that are not evaluated yet.
	// Initially, only the start node is known.
	// openSet := {start}
	ArrayList<Location> openSet = new ArrayList<Location>();
	openSet.add(start);

	// For each node, which node it can most efficiently be reached from.
	// If a node can be reached from many nodes, cameFrom will eventually contain
	// the
	// most efficient previous step.
	// cameFrom := an empty map
	Location[][] cameFrom = new Location[myGrid.getNumRows()][myGrid.getNumCols()];

	// For each node, the cost of getting from the start node to that node.
	// gScore := map with default value of Infinity
	int[][] gScore = new int[myGrid.getNumRows()][myGrid.getNumCols()];
	for (int r = 0; r < gScore.length; r++)
	{
	for (int c = 0; c < gScore[0].length; c++)
	{
	gScore[r][c] = Integer.MAX_VALUE;
	}
	}

	
	// The cost of going from start to start is zero.
	// gScore[start] := 0
	gScore[start.getRow()][start.getCol()] = 0;

	// For each node, the total cost of getting from the start node to the goal
	// by passing by that node. That value is partly known, partly heuristic.
	// fScore := map with default value of Infinity
	int[][] fScore = new int[myGrid.getNumRows()][myGrid.getNumCols()];
	for (int r = 0; r < fScore.length; r++)
	{
	for (int c = 0; c < fScore[0].length; c++)
	{
	fScore[r][c] = Integer.MAX_VALUE;
	}
	}

	// For the first node, that value is completely heuristic.
	// fScore[start] := heuristic_cost_estimate(start, goal)
	fScore[start.getRow()][start.getCol()] = heuristic_cost_estimate(start, goal);

	while (!openSet.isEmpty())
	{
	Location current = openSet.get(0);
	for (Location node : openSet)
	{
	if (fScore[node.getRow()][node.getCol()] < fScore[current.getRow()][current.getCol()])
	{
	current = node;
	}
	}
	if (current.equals(goal))
	{
	return reconstruct_path(cameFrom, current);
	}
	openSet.remove(current);
	closedSet.add(current);
	
	ArrayList<Location> neighbors = myGrid.getValidAdjacentLocations(current);
	for(int i = neighbors.size()-1; i >= 0; i--)
	{
	if(myGrid.get(neighbors.get(i)) instanceof Rock || myGrid.get(neighbors.get(i)) instanceof AbstractPlayer)
	{
	neighbors.remove(i);
	}
	}
	for (Location neighbor: neighbors)
	{
	if (closedSet.contains(neighbor))
	{
	continue;
	}
	if (!openSet.contains(neighbor))
	{
	openSet.add(neighbor);
	}

	int tentative_gScore = gScore[current.getRow()][current.getCol()] + 1;
	if (tentative_gScore >= gScore[neighbor.getRow()][neighbor.getCol()])
	{
	continue;
	}

	cameFrom[neighbor.getRow()][neighbor.getCol()] = current;
	gScore[neighbor.getRow()][neighbor.getCol()] = tentative_gScore;
	fScore[neighbor.getRow()][neighbor.getCol()] = gScore[neighbor.getRow()][neighbor.getCol()]
	+ heuristic_cost_estimate(neighbor, goal);

	}
	}
	return null;
	}

	public int heuristic_cost_estimate(Location start, Location goal)
	{
	int deltax = start.getRow() - goal.getRow();
	int deltay = start.getCol() - goal.getCol();
	if (deltax > deltay)
	{
	return deltax;
	}
	return deltay;
	}

	public ArrayList<Location> reconstruct_path(Location[][] cameFrom, Location current)
	{
	ArrayList<Location> total_path = new ArrayList<Location>();
	boolean foundStart = false;
	while (!foundStart)
	{
	total_path.add(0, current);
	current = cameFrom[current.getRow()][current.getCol()];
	if (current == null)
	{
	foundStart = true;
	}
	}
	return total_path;
	}

	public static ArrayList<Location> FindPath(int[][] rockLocations, Location start, Location end)
	{

	int[][] distance = new int[rockLocations.length][rockLocations[0].length];
	boolean[][] visited = new boolean[rockLocations.length][rockLocations[0].length];
	Location[][] previous = new Location[rockLocations.length][rockLocations[0].length];
	for (int r = 0; r < distance.length; r++)
	{
	for (int c = 0; c < distance[0].length; c++)
	{
	distance[r][c] = Integer.MAX_VALUE;
	visited[r][c] = false;
	}
	}
	distance[start.getRow()][start.getCol()] = 0;

	Location currentLocation = new Location(start.getRow(), start.getCol());
	boolean finished = false;
	while (!finished)
	{
	int r, c;
	r = currentLocation.getRow() - 1;
	c = currentLocation.getCol();
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	r = currentLocation.getRow() + 1;
	c = currentLocation.getCol();
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	r = currentLocation.getRow();
	c = currentLocation.getCol() + 1;
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	r = currentLocation.getRow();
	c = currentLocation.getCol() - 1;
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	r = currentLocation.getRow() - 1;
	c = currentLocation.getCol() - 1;
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	r = currentLocation.getRow() + 1;
	c = currentLocation.getCol() + 1;
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	r = currentLocation.getRow() - 1;
	c = currentLocation.getCol() + 1;
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	r = currentLocation.getRow() + 1;
	c = currentLocation.getCol() - 1;
	CalculateTentativeDistanceForLocation(r, c, distance, rockLocations, currentLocation, previous);

	visited[currentLocation.getRow()][currentLocation.getCol()] = true;
	if (currentLocation.equals(end))
	finished = true;

	finished = true;
	int smallestDistance = Integer.MAX_VALUE;
	for (r = 0; r < visited.length; r++)
	{
	for (c = 0; c < visited[0].length; c++)
	{
	if (!visited[r][c])
	{
	if (smallestDistance > distance[r][c])
	{
	smallestDistance = distance[r][c];
	currentLocation = new Location(r, c);
	finished = false;
	}
	}
	}
	}
	}

	boolean foundStart = false;
	Location l = end;
	ArrayList<Location> path = new ArrayList<>();
	while (!foundStart)
	{
	path.add(l);
	if (l.equals(start))
	foundStart = true;
	l = previous[l.getRow()][l.getCol()];
	}

	ArrayList<Location> reversepath = new ArrayList<>();
	for (int i = path.size() - 1; i >= 0; i--)
	{
	reversepath.add(path.get(i));
	}
	return reversepath;
	}

	private static void CalculateTentativeDistanceForLocation(int r, int c, int[][] distance, int[][] rockLocations,
	Location currentLocation, Location[][] previous)
	{
	if (!(r < 0 || c < 0 || r >= rockLocations.length || c >= rockLocations[0].length))
	{
	if (distance[r][c] > distance[currentLocation.getRow()][currentLocation.getCol()] + rockLocations[r][c])
	{
	distance[r][c] = distance[currentLocation.getRow()][currentLocation.getCol()] + rockLocations[r][c];
	previous[r][c] = new Location(currentLocation.getRow(), currentLocation.getCol());
	}
	}
	}

	public Location getMoveLocation()
	{
	myTeam = this.getTeam();
	theirTeam = myTeam.getOpposingTeam();
	myLoc = this.getLocation();
	myGrid = this.getGrid();
	myFlag = myTeam.getFlag();
	theirFlag = theirTeam.getFlag();

//	int[][] rockLocations = new int[myGrid.getNumRows()][myGrid.getNumCols()];
//
//	for (int r = 0; r < rockLocations.length; r++)
//	{
//	for (int c = 0; c < rockLocations[0].length; c++)
//	{
//	rockLocations[r][c] = 1;
//
//	if (myGrid.get(new Location(r, c)) instanceof Rock)
//	{
//	rockLocations[r][c] = 100;
//	}
//	}
//	}
	if(interval == 0)
	{
	ArrayList<Location> path = null;
	if(this.hasFlag())
	{
//	anyoneFlag = true;
	if(myLoc.getCol() >= 50)
	{
	path = Astar(myLoc, new Location(15, 48));
	}
	else
	{
	path = Astar(myLoc, new Location(35, 51));
	}
	}
	else
	{
//	if(anyoneFlag == true)
//	{
//	for(AbstractPlayer teamMember: myTeam.getPlayers())
//	{
//	if(teamMember.hasFlag())
//	{
//	path = Astar(myLoc, new Location(20, 51));
//	}
//	}
//	}
//	else
//	{
	path = Astar(myLoc, theirFlag.getLocation());
//	}
	}
	storedPath = path;
	interval = 4;
	}
	else
	{
	interval--;
	}
	if(storedPath != null && storedPath.size() > 2)
	{
	storedPath.remove(0);
	return storedPath.get(0);
	}
	return getTeam().getOpposingTeam().getFlag().getLocation();
	
	// ArrayList<Location> locations = myGrid.getValidAdjacentLocations(myLoc);
	//
	// List<AbstractPlayer> theirPlayers = theirTeam.getPlayers();
	//
	// for(int i = locations.size()-1; i >= 0; i--)
	// {
	// Location a = locations.get(i);
	//
	// if(theirPlayers.contains(myGrid.get(a)) && a.getCol() <
	// myGrid.getNumCols()/2)
	// {
	// return a;
	// }
	// if(myGrid.get(a) instanceof Rock)
	// {
	// checked.add(locations.remove(i));
	// }
	// if(myLoc.getCol() > myGrid.getNumCols()-1)
	// {
	// for(AbstractPlayer player: theirTeam.getPlayers())
	// {
	// if(myGrid.getValidAdjacentLocations(a).contains(player))
	// {
	// locations.remove(i);
	// }
	// }
	// }
	// }
	//
	//
	// int[] scores = new int[locations.size()];
	//
	// double bestdistance = 100000000;
	// for(int i = locations.size()-1; i >= 0; i--)
	// {
	//
	// int y1 = locations.get(i).getCol();
	// int y2 = theirFlag.getLocation().getCol();
	// int x1 = locations.get(i).getRow();
	// int x2 = theirFlag.getLocation().getRow();
	// double distance = Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));
	//
	// if(distance < bestdistance)
	// {
	// bestdistance = distance;
	// scores[i] += 2;
	// }
	//
	//
	//
	// }
	//
	// int bestscore = 0;
	// for(int i = locations.size()-1; i >= 0; i--)
	// {
	// int score = scores[i];
	// if(score > bestscore)
	// {
	// score = scores[i];
	// bestscore = i;
	// }
	// }
	// return destination;

	// if(theirTeam == null)
	// {
	// myTeam = this.getTeam();
	// theirTeam = myTeam.getOpposingTeam();
	// myLoc = this.getLocation();
	// myGrid = this.getGrid();
	//
	// if(myGrid.get(theirTeam.getFlag().getLocation()) instanceof Actor &&
	// !(myGrid.get(theirTeam.getFlag().getLocation()) instanceof Flag))
	// {
	// flag = myTeam.getFlag();
	// }
	// else
	// {
	// flag = theirTeam.getFlag();
	// }
	// }
	// ArrayList<Location> checked = new ArrayList<Location>();
	// ArrayList<Location> moved = new ArrayList<Location>();
	//
	// return pathfind(checked, moved, this.getLocation());
	}


}