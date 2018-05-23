
public class ThreadTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Thread subject2 = new Thread() {
			public void run() {
				try {
					while (true) {
						System.out.println("" + System.currentTimeMillis());
					}
				}
				catch (Exception e) {
					System.out.println("Stopped");
					System.out.println(e);
				}
			}
		};
		subject2.start();
		try {
			Thread.sleep(500);
		}
		catch (InterruptedException ex) {
		}
		System.out.println("coordinator stopping!");
		subject2.stop(); //.interrupt();
		System.out.println("coordinator stopping!");

	}

}

//long timeLimit = 100 / team.getPlayers().size();
//getMoveLocationThread.start();
//Thread.sleep(timeLimit);
//if (getMoveLocationThread.isAlive()) {
//	getMoveLocationThread.stop();
//	System.out.println(this+" was stopped");
//}
