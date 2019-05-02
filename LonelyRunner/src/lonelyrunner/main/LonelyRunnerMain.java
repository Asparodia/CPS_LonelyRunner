package lonelyrunner.main;

import java.util.Scanner;

import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Status;
public class LonelyRunnerMain extends lonelyRunner{


	public LonelyRunnerMain(EditableScreenImpl e) {
		super(e);
	}

	public static void main(String[] args) {
		String[] levels = new String[2];
		levels[0] = "src/lonelyrunner/main/level2.txt";
		levels[1] = "src/lonelyrunner/main/level3.txt";

		EditableScreenContract e = new EditableScreenContract(new EditableScreenImpl());
		String[][] lignes = readFile(levels[0]);
		e.init(lignes[0].length,lignes.length);
		for(int i = 0;i<e.getWidth();i++) {
			e.setNature(i,0,Cell.MTL);
		}
		if(!e.isPlayable()) {
			System.out.println("this level isnt playable !! : "+levels[0]);
		}
		for(int i=0; i<e.getWidth(); i++) {
			for(int j=0; j<e.getHeight(); j++) {
				switch(lignes[i][j]) {
				case "-":
					e.setNature(i, j, Cell.EMP);
					break;
				case "=":
					e.setNature(i, j, Cell.PLT);
					break;
				case "_":
					e.setNature(i, j, Cell.HOL);
					break;
				case "H":
					e.setNature(i, j, Cell.LAD);
					break;
				case "T":
					e.setNature(i, j, Cell.HDR);
					break;
				case "A" :
					e.setNature(i, j, Cell.MTL);
					break;

				}
			}
		}
		
		welcomeScreen();
		
		int v = 0;
		lonelyRunner run = new lonelyRunner(e.getDelegate());
		Scanner scan= new Scanner(System.in);
		while (true) {
			if(v == levels.length-1)
				System.out.println("XXXXXXXXXXXXXXXXX LAST LEVEL ! XXXXXXXXXXXXXXXXXX\n");
			else
				System.out.println("XXXXXXXXXXXXXXXXX Level "+(v+1)+" XXXXXXXXXXXXXXXXXX\n");
			run.afficher();
			int vie = lonelyRunner.engine.getNbLives();
			boolean retry = false;
			while(lonelyRunner.engine.getStatus() == Status.Playing && vie == lonelyRunner.engine.getNbLives()) {
				run.readCommand(scan);
				lonelyRunner.engine.step();
				run.afficher();
				if(vie != lonelyRunner.engine.getNbLives()) {
					if(vie>0) {
						retry = true;
					}
					else {
						retry = false;
					}
					break;
				}
			}
			if(lonelyRunner.engine.getStatus()== Status.Win) {
				System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX YOU WON ! XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
				if(v+1 < levels.length) {
					System.out.println(">>> Congrats buddy you live for another round\n");
				}
			}else {
				if(retry) {
					int l = lonelyRunner.engine.getNbLives();
					System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX YOU DIE ! XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
					System.out.println(">>> "+(l+1)+" chance left");
					run.nextLevel(levels[v]);
					lonelyRunner.engine.setNbLives(l);
					continue;
				}
			}
			if(lonelyRunner.engine.getNbLives() <= 0 && lonelyRunner.engine.getStatus() == Status.Loss) {
				System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX YOU DIE ! XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
				System.out.println(">>> You die... alone :(...\n");
				break;
			}
			if(v == levels.length-1) {
				break;
			}
			else {
				v++;
				run.nextLevel(levels[v]);
			}
		}
		scan.close();
		System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX THANK YOU FOR PLAYING XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");		
	}
	
	public static void welcomeScreen() {
		System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX WELCOME YOU LONELY RUNNER XXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX WANNA KNOW HOW TO PLAY ? IT'S SIMPLE XXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXX q to move left");
		System.out.println("XXXXXXXXXXXXX s to move down");
		System.out.println("XXXXXXXXXXXXX d to move right");
		System.out.println("XXXXXXXXXXXXX z to move up");
		System.out.println("XXXXXXXXXXXXX 4 to dig left");
		System.out.println("XXXXXXXXXXXXX 6 to dig right");
		System.out.println("XXXXXXXXXXXXX all other input will just make the game advence be carefull :)");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX HAVE FUN OR DIE TRYING :) XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
	}
}
