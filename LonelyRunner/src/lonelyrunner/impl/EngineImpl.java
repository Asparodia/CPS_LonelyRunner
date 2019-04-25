package lonelyrunner.impl;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineImpl implements EngineService {

	@Override
	public EditableScreenService getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerService getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Item> getTreasures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Move getNextCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Couple<Integer, Integer>, Integer> getHoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(EditableScreenService es, Couple<Integer, Integer> posChar,
			ArrayList<Couple<Integer, Integer>> posGuards, ArrayList<Couple<Integer, Integer>> posItems) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
