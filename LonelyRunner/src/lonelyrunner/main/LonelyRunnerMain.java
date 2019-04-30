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
		String[] levels = new String[1];
		//levels[0] = "src/lonelyrunner/main/level2.txt";
		levels[0] = "src/lonelyrunner/main/level3.txt";

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

		System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n WELCOME YOU LONELY RUNNER\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
		int v = 0;
		lonelyRunner run = new lonelyRunner(e.getDelegate());
		Scanner scan= new Scanner(System.in);
		while (true) {
			run.afficher();

			while(lonelyRunner.engine.getStatus() == Status.Playing) {
				run.readCommand(scan);
				lonelyRunner.engine.step();
				run.afficher();
			}
			if(lonelyRunner.engine.getStatus()== Status.Win) {
				System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX YOU WIN ! XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
				if(v+1 < levels.length) {
					System.out.println(">>> Congrats buddy you live for another round");
				}

			}
			if(lonelyRunner.engine.getStatus() == Status.Loss) {
				System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX YOU DIE ! XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
				System.out.println(">>> You die... alone...");
				if(lonelyRunner.engine.getNbLives()>0) {
					int l = lonelyRunner.engine.getNbLives();
					System.out.println(">>> "+l+" chance left");
					run.nextLevel(levels[v]); //tester pour voir si le retry il marche
					lonelyRunner.engine.setNbLives(l);
					continue;
				}
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
		System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n THANK YOU FOR PLAYING\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");		
	}

}
