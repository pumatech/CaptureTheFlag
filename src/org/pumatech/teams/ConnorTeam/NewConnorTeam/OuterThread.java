package org.pumatech.teams.ConnorTeam.NewConnorTeam;

public class OuterThread implements Runnable {
	private Feild feil;

	public OuterThread(Feild f) {
		feil = f;
	}

	public void run() {
		try {
			feil.calc();
		} catch (Exception e) {
			System.out.println("Outer exception is caught "+e);
		}
	}
}
