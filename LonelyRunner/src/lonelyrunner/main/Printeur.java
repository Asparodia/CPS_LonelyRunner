package lonelyrunner.main;

import lonelyrunner.service.utils.Status;

public class Printeur extends Thread{
	
	lonelyRunner run;
	
	public Printeur(lonelyRunner r) {
		run = r;
	}
	
	public void run() {
		while(run.engine.getStatus() == Status.Playing) {
			run.afficher();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
