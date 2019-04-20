package lonelyrunner.contract;

import lonelyrunner.decorators.PlayerDecorator;
import lonelyrunner.service.CharacterService;

public class PlayerContract extends PlayerDecorator{

	public PlayerContract(CharacterService c) {
		super(c);
	}
	
	public void checkInvariant() {
		
	}

}
