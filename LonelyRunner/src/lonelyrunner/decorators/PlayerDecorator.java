package lonelyrunner.decorators;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.PlayerService;

public class PlayerDecorator extends CharacterDecorator implements PlayerService {

	public PlayerDecorator(CharacterService c) {
		super(c);
	}
	
	public PlayerService getDelegate() {
		return (PlayerService) super.getDelegate();
	}

}
