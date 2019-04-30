package lonelyrunner.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import lonelyrunner.contract.EditableScreenContract;
import lonelyrunner.contract.EngineContract;
import lonelyrunner.impl.EditableScreenImpl;
import lonelyrunner.impl.EngineImpl;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;

public class lonelyRunner{
	protected static EngineContract engine;
	private static  Couple<Integer,Integer> playerInit;
	private static ArrayList<Couple<Integer,Integer>> guardsInit;
	private static ArrayList<Couple<Integer,Integer>> treasuresInit;
	
	public lonelyRunner(EditableScreenService e) {
		engine = new EngineContract(new EngineImpl());
		engine.init(e, playerInit, guardsInit, treasuresInit);
	}
	
	public static String[][] readFile(String file) {
		String[][] res = null;
		engine = null;
		playerInit = null;
		guardsInit = null;
		treasuresInit = null;
		
		try (FileReader reader = new FileReader(file);
				 BufferedReader br = new BufferedReader(reader)) {
			
			String line = br.readLine();
            String[] tailles = line.split(" ");
            res = new String[Integer.parseInt(tailles[0])][Integer.parseInt(tailles[1])];
            int j= Integer.parseInt(tailles[1])-1;
            
            line = br.readLine();
            String[] posPlayer = line.split(" ");
            playerInit = new Couple<Integer,Integer>(Integer.parseInt(posPlayer[0]),Integer.parseInt(posPlayer[1]));
            
            line = br.readLine();
            String[] posGuard = line.split(",");
            
            ArrayList<Couple<Integer,Integer>> posGuards = new ArrayList<Couple<Integer,Integer>>();
            for(String s: posGuard) {
            	String[] pg = s.split(" ");
            	posGuards.add(new Couple<Integer,Integer>(Integer.parseInt(pg[0]),Integer.parseInt(pg[1])));
            }
            guardsInit = posGuards;
            
            line = br.readLine();
            String[] posTreasure = line.split(",");
            ArrayList<Couple<Integer,Integer>> posTreasures = new ArrayList<Couple<Integer,Integer>>();
            for(String s: posTreasure) {
            	
            	String[] pt = s.split(" ");
            	posTreasures.add(new Couple<Integer,Integer>(Integer.parseInt(pt[0]),Integer.parseInt(pt[1])));
            }
            treasuresInit = posTreasures;
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
		for (GuardService i: engine.getGuards()) {
			res[i.getWdt()][i.getHgt()] = "G";
		}
		res[engine.getPlayer().getWdt()][engine.getPlayer().getHgt()] = "P";
		System.out.println("Lives : "+engine.getNbLives()+" Score : "+engine.getScore());
		for ( int ligne = res[0].length-1;ligne>=0;ligne--) {
			for (int col=0;col<res.length;col++) {
				System.out.print(res[col][ligne]);
			}
			System.out.println();
		}
	}
		
	public void nextLevel(String s) {
		String[][] lignes = readFile(s);
		engine = new EngineContract(new EngineImpl());
		EditableScreenContract e = new EditableScreenContract(new EditableScreenImpl());
		e.init(lignes[0].length,lignes.length);
		for(int i = 0;i<e.getWidth();i++) {
			e.setNature(i,0,Cell.MTL);
		}
		if(!e.isPlayable()) {
			System.out.println("this level isnt playable !! : "+s);
			return;
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
		engine.init(e, playerInit, guardsInit, treasuresInit);
	}
	
	public void readCommand(Scanner scan) {
		
		
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
		
		
	}
}