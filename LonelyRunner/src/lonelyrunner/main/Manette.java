package lonelyrunner.main;

import java.util.Scanner;

import lonelyrunner.service.EngineService;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class Manette extends Thread{
	
	Scanner scan;
	EngineService engine;
	
	public Manette(EngineService engine,Scanner s) {
		this.scan= s;
		this.engine = engine;
	}
	
	public void run() {
		while(engine.getStatus() == Status.Playing) {
			System.out.print(">>> ");
			String ligne = "";
	        if (scan.hasNext()) {
	            ligne = scan.next();
	            scan.nextLine();
	        }
			switch(ligne) {
				case "z":
					engine.setCommand(Move.UP);
					break;
				case "q":
					engine.setCommand(Move.LEFT);
					break;
				case "s":
					engine.setCommand(Move.DOWN);
					break;
				case "d":
					engine.setCommand(Move.RIGHT);
					break;
				case "4":
					engine.setCommand(Move.DigL);
					break;
				case "6":
					engine.setCommand(Move.DigR);
					break;
			}
			engine.step();
		}
		
		
		
	}

}
