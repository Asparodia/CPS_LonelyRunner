package lonelyrunner.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import lonelyrunner.contract.EngineContract;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.impl.EngineImpl;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;

public class lonelyRunner{
	protected static EngineContract engine;
	
	public lonelyRunner(EditableScreenImpl e, Couple<Integer,Integer> player, ArrayList<Couple<Integer,Integer>> guards, ArrayList<Couple<Integer,Integer>> treasures) {
		engine = new EngineContract(new EngineImpl());
		engine.init(e, player, guards, treasures);
	}
	
	public static String[][] readFile(String file) {
		String[][] res = null;
		
		try (FileReader reader = new FileReader(file);
				 BufferedReader br = new BufferedReader(reader)) {
			
			String line = br.readLine();
            String[] tailles = line.split(" ");
            res = new String[Integer.parseInt(tailles[0])][Integer.parseInt(tailles[1])];
            
            int j= Integer.parseInt(tailles[1])-1; 
            
	        while ((line = br.readLine()) != null) {
	        	for(int i=0; i<line.length(); i++) {
	        		res[i][j] = String.valueOf(line.charAt(i));
	        		
	        	}
	        	j--;
	        }
	        reader.close();
		 } catch (IOException e) {
	        System.err.format("IOException: %s%n", e);
	     }
		return res;
	}
	
	public void afficher() {
		String [][] res = new String[engine.getEnvironment().getWidth()][engine.getEnvironment().getHeight()];
		for (int i=0; i<engine.getEnvironment().getWidth(); i++) {
			for (int j=0; j<engine.getEnvironment().getHeight(); j++) {
				
				switch(engine.getEnvironment().getCellNature(i, j)) {
					case EMP:
						res[i][j] = " ";
						break;
					case PLT:
						res[i][j] = "=";
						break;
					case HOL:
						res[i][j] = "_";
						break;
					case LAD:
						res[i][j] = "H";
						break;
					case HDR:
						res[i][j] = "-";
						break;
					case MTL:
						res[i][j] = "X";
						break;
					default:
						res[i][j] = "E";
						break;
				}
			}
		}
		
		for (Item i: engine.getTreasures()) {
			res[i.getCol()][i.getHgt()] = "*";
		}
		res[engine.getPlayer().getWdt()][engine.getPlayer().getHgt()] = "P";
		
		for ( int ligne = res[0].length-1;ligne>=0;ligne--) {
			for (int col=0;col<res.length;col++) {
				System.out.print(res[col][ligne]);
			}
			System.out.println();
		}
	}
	
	public void readCommand() {
		
		Scanner scan= new Scanner(System.in);
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
		
		scan.close();
	}
}