package lonelyrunner.main;

import java.util.ArrayList;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Status;

public class LonelyRunnerMain extends lonelyRunner{
	
	
	public LonelyRunnerMain(EditableScreenImpl e, Couple<Integer, Integer> player,
			ArrayList<Couple<Integer, Integer>> guards, ArrayList<Couple<Integer, Integer>> treasures) {
		super(e, player, guards, treasures);
	}

	public static void main(String[] args) {
		EditableScreenImpl e = new EditableScreenImpl();
		
		String[][] lignes = readFile("level1.txt");
		e.init(lignes.length, lignes[0].length);
		
		for(int i=0; i<lignes.length; i++) {
			for(int j=0; j<lignes[i].length; j++) {
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
				case "‾":
					e.setNature(i, j, Cell.HDR);
					break;
				case "X" :
					e.setNature(i, j, Cell.MTL);
					break;
					
				}
			}
		}
		

		
		ArrayList<Couple<Integer,Integer>> treasures = new ArrayList<Couple<Integer,Integer>>();
		treasures.add(new Couple<Integer,Integer>(6,5));
		treasures.add(new Couple<Integer,Integer>(7,1));
		treasures.add(new Couple<Integer,Integer>(0,2));
		
		ArrayList<Couple<Integer,Integer>> gardes = new ArrayList<Couple<Integer,Integer>>();
		
		lonelyRunner run = new lonelyRunner(e,new Couple<Integer,Integer>(1,1), gardes, treasures);
		run.afficher();
		while(run.engine.getStatus() == Status.Playing) {
			run.readCommand();
			run.engine.step();
			run.afficher();
		}
	}

}
