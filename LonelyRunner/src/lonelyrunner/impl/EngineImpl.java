package lonelyrunner.impl;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.ItemType;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineImpl implements EngineService {
	
	EnvironmentService environment;
	PlayerService player;
	ArrayList<GuardService> guards;
	ArrayList<Item> treasures;
	Status status;
	Move command;
	
	HashMap<Couple<Integer,Integer>,Integer> holes;

	@Override
	public void init(EditableScreenService es, Couple<Integer, Integer> posChar,
			ArrayList<Couple<Integer, Integer>> posGuards, ArrayList<Couple<Integer, Integer>> posItems) {
		environment = new EnvironmentImpl();
		environment.init(es);
		player = new PlayerImpl();
		player.init(environment, posChar.getElem1(), posChar.getElem2(),this);
		environment.getCellContent(posChar.getElem1(), posChar.getElem2()).setCar(player);
		for(Couple<Integer,Integer> c : posGuards) {
			GuardService g = new GuardImpl();
			g.init(environment,c.getElem1(),c.getElem2());
			environment.getCellContent(c.getElem1(), c.getElem2()).setCar(g);
			this.guards.add(g);
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
	public EnvironmentService getEnvironment() {
		return environment;
	}

	@Override
	public PlayerService getPlayer() {
		return player;
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return guards;
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
		command = c;
	}
	

	@Override
	public void step() {
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
		
		int x = player.getWdt();
		int y = player.getHgt();
		
		environment.getCellContent(x, y).removeCharacter();
		
		player.step();
		
		environment.getCellContent(player.getWdt(), player.getHgt()).setCar(player);
		
		// Manque cas lose et les holes
		
	}

}
