package lonelyrunner.decorators;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.ScreenService;

public class CharacterDecorator implements CharacterService {
	private final CharacterService delegate;
	
	public CharacterDecorator(CharacterService c) {
		this.delegate = c;
	}
	
	public CharacterService getDelegate() {
		return this.delegate;
	}

	@Override
	public EnvironmentService getEnvi() {
		return delegate.getEnvi();
	}

	@Override
	public int getHgt() {
		return delegate.getHgt();
	}

	@Override
	public int getWdt() {
		return delegate.getWdt();
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		delegate.init(s, x, y);
		
	}

	@Override
	public void goLeft() {
		delegate.goLeft();
		
	}

	@Override
	public void goRight() {
		delegate.goRight();
		
	}

	@Override
	public void goUp() {
		delegate.goUp();
		
	}

	@Override
	public void goDown() {
		delegate.goDown();
		
	}
}
