package ctf.gen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.pumatech.ctf.Bracket;
import org.pumatech.ctf.Team;

import ctf.teams.ConnorTeam.Quadrigis;
import weston.WestonTeam;

public class GenRunner {
	public static void main(String[] args) {
		int inSize=5;
		int nodesSize=5;
		int levels=2;
		double[][][] ioa=new double[levels][nodesSize][inSize];
		double[][][] iob=new double[levels][nodesSize][inSize];
		double[][][] ioc=new double[levels][nodesSize][inSize];
		double[][][] iod=new double[levels][nodesSize][inSize];
		
		generate(ioa);
		generate(iob);
		generate(ioc);
		generate(iod);
		
		GenTeam winner=new GenTeam("winner",Color.BLUE,ioa);
		GenTeam mutate=new GenTeam("mutate",Color.RED,iob);
		GenTeam pastWinner=new GenTeam("pastWinner",Color.GREEN,ioa);
		GenTeam random=new GenTeam("random",Color.YELLOW,iob);
		
		for(int i=0;i<20;i++) {
			List<Team> teams = new ArrayList<Team>();
			teams.add(winner);
			teams.add(mutate);
			Bracket bracket = new Bracket(teams);
			
			GenTeam temp=(GenTeam)bracket.getWinner();
			
			List<Team> teams2 = new ArrayList<Team>();
			teams2.add(pastWinner);
			teams2.add(random);
			Bracket bracket2 = new Bracket(teams2);
			
			List<Team> teams3 = new ArrayList<Team>();
			teams3.add(temp);
			teams3.add((GenTeam)bracket2.getWinner());
			Bracket bracket3 = new Bracket(teams3);
			
			double[][][] winIO=((GenTeam)bracket3.getWinner()).getIo();
			
			pastWinner=new GenTeam("pastWinner",Color.GREEN,winner.getIo());
			winner=new GenTeam("winner",Color.BLUE,winIO);
			mutate=new GenTeam("mutate",Color.RED,randomize(winIO));
			double[][][] io=new double[levels][nodesSize][inSize];
			generate(io);
			random=new GenTeam("random",Color.YELLOW,io);
		}
//		List<Team> teams = new ArrayList<Team>();
//		teams.add(winner);
//		teams.add(new ConnorTeam(Color.BLUE));
//		Bracket bracket = new Bracket(teams);
//		
//		bracket.getWinner();

	}
	
	public static void generate(double[][][] io) {
		for(int i=0;i<io.length;i++) {
			for(int j=0;j<io[i].length;j++) {
				for(int k=0;k<io[i][j].length;k++) {
					io[i][j][k]=Math.random()*20-10;
				}
				
			}
		}
	}
	
	public static double[][][] randomize(double[][][] io) {
		double[][][] o=new double[io.length][io[0].length][io[0][0].length];
		for(int i=0;i<io.length;i++) {
			for(int j=0;j<io[i].length;j++) {
				for(int k=0;k<io[i][j].length;k++) {
					o[i][j][k]=io[i][j][k]*(Math.random()*4-2);
					if(o[i][j][k]>10) {
						o[i][j][k]=10;
					}else if(o[i][j][k]<-10) {
						o[i][j][k]=-10;
					}
				}
			}
		}
		return o;
	}

}
