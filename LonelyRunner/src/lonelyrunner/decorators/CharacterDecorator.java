package lonelyrunner.decorators;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.ScreenService;

public class CharacterDecorator extends Decorator implements CharacterService {
	
	public CharacterDecorator(CharacterService c) {
		super(c);
	}
	
	public CharacterService getDelegate() {
		return (CharacterService)super.getDelegate();
	}

	@Override
	public EnvironmentService getEnvi() {
		return getDelegate().getEnvi();
	}

	@Override
	public int getHgt() {
		return getDelegate().getHgt();
	}

	@Override
	public int getWdt() {
		return getDelegate().getWdt();
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		getDelegate().init(s, x, y);
		
	}

	@Override
	public void goLeft() {
		getDelegate().goLeft();
		
	}

	@Override
	public void goRight() {
		getDelegate().goRight();
		
	}

	@Override
	public void goUp() {
		getDelegate().goUp();
		
	}

	@Override
	public void goDown() {
		getDelegate().goDown();
		
	}
}
