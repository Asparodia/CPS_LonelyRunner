package lonelyrunner.impl;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.contract.EnvironmentContract;
import lonelyrunner.contract.GuardContract;
import lonelyrunner.contract.PlayerContract;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.ItemType;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineImpl implements EngineService {
	
	EnvironmentContract environment;
	PlayerContract player;
	ArrayList<GuardService> guards = new ArrayList<>();
	ArrayList<Item> treasures = new ArrayList<>();
	Status status;
	Move command = Move.NEUTRAL ;
	
	HashMap<Couple<Integer,Integer>,Integer> holes;

	@Override
	public void init(EditableScreenService es, Couple<Integer, Integer> posChar,
			ArrayList<Couple<Integer, Integer>> posGuards, ArrayList<Couple<Integer, Integer>> posItems) {
		
		
		EnvironmentImpl envi = new EnvironmentImpl();
		environment = new EnvironmentContract(envi);
		environment.init(es);
		
		PlayerImpl p = new PlayerImpl();
		player = new PlayerContract(p);
		player.init(environment, posChar.getElem1(), posChar.getElem2(),this);
		
		environment.getCellContent(posChar.getElem1(), posChar.getElem2()).addCar(player.getDelegate());
		
		for(Couple<Integer,Integer> c : posGuards) {
			
			GuardImpl g = new GuardImpl();
			GuardContract gc = new GuardContract(g);
			gc.init(environment,c.getElem1(),c.getElem2(),player);
			environment.getCellContent(c.getElem1(), c.getElem2()).addCar(gc.getDelegate());
			this.guards.add(gc);
		}
		int id = 0;
		for(Couple<Integer,Integer> c : posItems) {
			Item i = new Item(id, ItemType.Treasure,c.getElem1(),c.getElem2());
			id++;
			environment.getCellContent(c.getElem1(), c.getElem2()).setItem(i);
			this.treasures.add(i);
		}
		status = Status.Playing;
		
		
	}
	
	@Override
	public EnvironmentContract getEnvironment() {
		return environment;
	}

	@Override
	public PlayerContract getPlayer() {
		return player;
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return new ArrayList<GuardService>(guards);
	}

	@Override
	public ArrayList<Item> getTreasures() {
		ArrayList<Item> res = new ArrayList<>();
		for (Item i : treasures) {
			if(i.getNature() == ItemType.Treasure) {
				res.add(i);
			}
		}
		return res;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Move getNextCommand() {
		return command;
	}

	@Override
	public HashMap<Couple<Integer, Integer>, Integer> getHoles() {
		return holes;
	}

	public void setCommand(Move c) {
		this.command = c;
	}
	

	@Override
	public void step() {
		
		int x = player.getWdt();
		int y = player.getHgt();
		
		environment.getCellContent(x, y).removeCharacter(player.getDelegate());
		
		player.step();
		
//		for (GuardService g : guards) {
//			g.step();
//		}
		
		environment.getCellContent(player.getWdt(), player.getHgt()).addCar(player.getDelegate());
		
		ArrayList<Item> rem = new ArrayList<>();
		for (Item i : treasures) {
			if(i.getNature() == ItemType.Treasure) {
				if(i.getCol() == player.getWdt() && i.getHgt() == player.getHgt()) {
					environment.getCellContent(i.getCol(), i.getHgt()).removeItem();
					rem.add(i);
				}
			}
		}
		treasures.removeAll(rem);
		for (GuardService g : guards) {
			if(g.getWdt() == player.getWdt() && g.getHgt() == player.getHgt()) {
				status = Status.Loss;
				return;
			}
		}
		
		boolean end = true;
		for (Item i : treasures) {
			if(i.getNature() == ItemType.Treasure) {
				end = false;
			}
		}
		if(end) {
			status = Status.Win;
			return;
		}
		

		
		// Manque cas holes	
	}
}
