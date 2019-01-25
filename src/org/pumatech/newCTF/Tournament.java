package org.pumatech.newCTF;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Tournament {
	ArrayList<Team> teams;
	private Team winner;
	CtfWorld world;
	
	public Tournament(ArrayList<Team> t,CtfWorld w) {
		this.teams = t;
		world=w;
	}
	
	public void start() {
		ArrayList<Team> teamsToPlay = new ArrayList<>();
		teamsToPlay.addAll(teams);
		ArrayList<Team> winningTeams = new ArrayList<>();
		boolean first = false;
		
		
		Bracket bracket=new Bracket(teams);
		JFrame bracketViewer = new JFrame("Capture The Flag 2019 Bracket");
		bracketViewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bracketViewer.setResizable(true);
		bracketViewer.add(bracket);
		bracketViewer.pack();
		bracketViewer.setLocationRelativeTo(null);
		bracketViewer.setVisible(true);
		for(Team t:teamsToPlay){
			System.out.println("try");
			bracket.addWinner(t.getName());
		}
		
		while (teamsToPlay.size() > 1) {
			boolean odd=teamsToPlay.size() % 2 == 1;
			if (odd&&first) {
				bracket.addWinner(teamsToPlay.get(0).getName());
				winningTeams.add(teamsToPlay.get(0));
				
				System.out.println(teamsToPlay.get(0).getColor()+" got a bye");
				teamsToPlay.remove(0);
				first=false;
				odd=false;
			}
			for (int i = 0; i < teamsToPlay.size()-1; i += 2) {
				System.out.println(teamsToPlay.get(i).getColor()+" "+teamsToPlay.get(i+1).getColor());
				world.setTeams(teamsToPlay.get(i), teamsToPlay.get(i+1));
				Match match=new Match(teamsToPlay.get(i),teamsToPlay.get(i+1),world);
				match.start();
				winningTeams.add(match.getWinner());
				bracket.addWinner(match.getWinner().getName());
				bracketViewer.setVisible(false);
				bracketViewer.setVisible(true);
				System.out.println(match.getWinner().getName()+" won");
			}
			if(odd&&!first) {
				bracket.addWinner(teamsToPlay.get(teamsToPlay.size()-1).getName());
				bracketViewer.setVisible(false);
				bracketViewer.setVisible(true);
				winningTeams.add(teamsToPlay.get(teamsToPlay.size()-1));
				System.out.println(teamsToPlay.get(teamsToPlay.size()-1).getColor()+" got a bye");
				first=true;
			}
			teamsToPlay.clear();
			teamsToPlay.addAll(winningTeams);
			winningTeams.clear();
		}
		winner=teamsToPlay.get(0);
	}
	
	public Team getWinner() {
		return winner;
	}
	
}
