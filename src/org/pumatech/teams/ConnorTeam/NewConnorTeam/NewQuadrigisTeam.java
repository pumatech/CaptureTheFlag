package org.pumatech.teams.ConnorTeam.NewConnorTeam;

import info.gridworld.grid.Location;

import java.awt.*;
import java.util.ArrayList;

import org.pumatech.CTF2019.Team;

public class NewQuadrigisTeam extends Team {
    public NewQuadrigisTeam() {
        this("Quadrigis2", Color.BLUE);
    }

    public NewQuadrigisTeam(Color color){
        this("NewQuadrigisTeam", color);
    }

    public NewQuadrigisTeam(String name, Color color) {
        super(name, color);
    }

    public void generateTeam() {
        ArrayList<AbstractFriendlyPlayer> players=new ArrayList<AbstractFriendlyPlayer>();
        int x=40;
        int y=27;
        players.add(new Offensionis(new Location(y-4, x)));
        players.add(new Offensionis(new Location(y-3, x)));
        players.add(new Offensionis(new Location(y-2, x)));
        players.add(new Offensionis(new Location(y-1, x)));
        players.add(new Defensionis(new Location(y, x)));
        players.add(new Defensionis(new Location(y, x+1)));
        players.add(new Defensionis(new Location(y, x-1)));
        players.add(new Defensionis(new Location(y+1, x)));


        for(AbstractFriendlyPlayer p:players) {
            if (p instanceof Offensionis) {
                p.setDirection(-45);
            }
            addPlayer(p);
        }
    }
}
