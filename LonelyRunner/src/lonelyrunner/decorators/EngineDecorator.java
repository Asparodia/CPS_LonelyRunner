package lonelyrunner.decorators;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
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
		return getDelegate().getEnvironment();
	}

	@Override
	public PlayerService getPlayer() {
		return getDelegate().getPlayer();
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return getDelegate().getGuards();
	}

	@Override
	public ArrayList<Item> getTreasures() {
		return getDelegate().getTreasures();
	}

	@Override
	public Status getStatus() {
		return getDelegate().getStatus();
	}

	@Override
	public Move getNextCommand() {
		return getDelegate().getNextCommand();
	}

	@Override
	public HashMap<Couple<Integer, Integer>, Integer> getHoles() {
		return getDelegate().getHoles();
	}

	@Override
	public void init(EditableScreenService es, Couple<Integer, Integer> posChar,
			ArrayList<Couple<Integer, Integer>> posGuards, ArrayList<Couple<Integer, Integer>> posItems) {
		getDelegate().init(es, posChar, posGuards, posItems);
	}

	@Override
	public void step() {
		getDelegate().step();
	}

}
