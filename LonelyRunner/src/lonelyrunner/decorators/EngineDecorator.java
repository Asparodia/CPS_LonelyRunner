package lonelyrunner.decorators;

import java.util.ArrayList;
import java.util.Vector;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineDecorator extends Decorator implements EngineService {

	public EngineDecorator(EngineService delegate) {
		super(delegate);
	}

	public EngineService getDelegate() {
		return (EngineService) super.getDelegate();
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
	public ArrayList<Hole> getHoles() {
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

	@Override
	public void setCommand(Move c) {
		getDelegate().setCommand(c);

	}

	@Override
	public int getNbLives() {
		return getDelegate().getNbLives();
	}

	@Override
	public int getScore() {
		return getDelegate().getScore();
	}

	@Override
	public void setNbLives(int l) {
		getDelegate().setNbLives(l);
	}

	@Override
	public ArrayList<Vector<Integer>> guardInitPos() {
		return getDelegate().guardInitPos();
	}

}
