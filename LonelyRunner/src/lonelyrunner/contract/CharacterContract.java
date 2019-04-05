package lonelyrunner.contract;

import lonelyrunner.decorators.CharacterDecorator;
import lonelyrunner.service.CharacterService;

public class CharacterContract extends CharacterDecorator {

	public CharacterContract(CharacterService c) {
		super(c);
	}
	
	public void checkInvariant() {
		
	}

}
