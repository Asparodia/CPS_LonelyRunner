package lonelyrunner.decorators;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineDecorator implements EngineService{
	
	private final EngineService delegate;

	public EngineDecorator(EngineService delegate) {
		this.delegate = delegate;
	}

	public EngineService getDelegate() {
		return delegate;
	}

	@Override
	public EnvironmentService getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CharacterService getPlayer() {
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
	public void Step() {
		// TODO Auto-generated method stub
		
	}

}
