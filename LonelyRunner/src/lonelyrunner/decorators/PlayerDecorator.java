package lonelyrunner.decorators;

import lonelyrunner.service.EngineService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;

public class PlayerDecorator extends CharacterDecorator implements PlayerService {

	public PlayerDecorator(PlayerService c) {
		super(c);
	}

	public PlayerService getDelegate() {
		return (PlayerService) super.getDelegate();
	}

	@Override
	public EngineService getEngine() {
		return getDelegate().getEngine();
	}

	@Override
	public void init(ScreenService s, int x, int y, EngineService engine) {
		getDelegate().init(s, x, y, engine);

	}

	@Override
	public void step() {
		getDelegate().step();

	}

	@Override
	public void digL() {
		getDelegate().digL();

	}

	@Override
	public void digR() {
		getDelegate().digR();
	}

	@Override
	public void doNeutral() {
		getDelegate().doNeutral();

	}

}
