package org.pumatech.newCTF;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Bracket extends JPanel {
	private static final long serialVersionUID = 1L;
	
	Graphics graphic;
	ArrayList<Team> teams;
	ArrayList<Integer> xLocs;
	ArrayList<Integer> yLocs;
	int gameNum;
	
	public Bracket(ArrayList<Team> t) {
		teams = t;
		gameNum = -1;
		xLocs = new ArrayList<Integer>();
		yLocs = new ArrayList<Integer>();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(800, 800);
	}
	
	public Dimension getMinimumSize() {
		return new Dimension(800, 800);
	}
	
	public Dimension getMaximumSize() {
		return new Dimension(800, 800);
	}
	
	public void paintComponent(Graphics g) {
		if (gameNum == -1) {
			gameNum = 0;
			return;
		}
		graphic = g;
		super.paintComponent(g);
		g.setFont(new Font("Consolas", Font.PLAIN, 14));
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 800);
		g.setColor(Color.BLACK);
		int width = 150;
		int height = 40;
		int x = 25;
		int n = teams.size();
		while (n > 1) {
			int y = 400 - ((height + 10) * n) / 2;
			for (int i = 0; i < n; i++) {
				g.fillRect(x, y + i * height + i * 10, width, height);
				xLocs.add(x);
				yLocs.add(y + i * height + i * 10 + height / 2);
			}
			x += width + 20;
			if (n % 2 == 1) {
				n++;
			}
			n = n / 2;
		}
		g.fillRect(x, 400 - height / 2 - 5, width, height);
		xLocs.add(x);
		yLocs.add(400 - height / 2 - 5 + height / 2);
		for (Team t : teams) {
			addWinner(t.getName());
		}
		
	}
	
	public void addWinner(String teamName) {
		if(graphic!=null) {
			graphic.setColor(Color.WHITE);
			graphic.drawString(teamName, xLocs.get(gameNum) + 20, yLocs.get(gameNum));
			gameNum++;
		}
		
	}
	
}
