package lonelyrunner.main;

import java.util.Scanner;

import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Status;

public class LonelyRunnerMain extends lonelyRunner{
	
	
	public LonelyRunnerMain(EditableScreenImpl e) {
		super(e);
	}

	public static void main(String[] args) {
		EditableScreenImpl e = new EditableScreenImpl();
//		EditableScreenService e = new EditableScreenContract(new EditableScreenImpl());
		String[][] lignes = readFile("src/lonelyrunner/main/level2.txt");
		
		e.init(lignes[0].length,lignes.length);
		
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
		
		lonelyRunner run = new lonelyRunner(e);
		System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n WELCOME YOU LONELY RUNNER\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
		run.afficher();
		Scanner scan= new Scanner(System.in);
		
		while(lonelyRunner.engine.getStatus() == Status.Playing) {
			run.readCommand(scan);
			lonelyRunner.engine.step();
			run.afficher();
		}
		scan.close();
		if(lonelyRunner.engine.getStatus()== Status.Win) {
			System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX YOU WIN ! XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
			System.out.println("Congrats buddy you live for another round");
		}
		if(lonelyRunner.engine.getStatus()== Status.Loss) {
			System.out.println("\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX YOU DIE ! XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXX\n\n");
			System.out.println("You die alone...");
		}
		
	}

}
