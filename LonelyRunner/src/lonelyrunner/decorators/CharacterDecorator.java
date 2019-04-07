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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHgt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWdt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init(ScreenService s, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goDown() {
		// TODO Auto-generated method stub
		
	}
}
