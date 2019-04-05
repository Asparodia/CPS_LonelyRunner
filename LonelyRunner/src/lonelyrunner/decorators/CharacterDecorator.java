package lonelyrunner.decorators;

import lonelyrunner.service.CharacterService;

public class CharacterDecorator implements CharacterService {
	private final CharacterService delegate;
	
	public CharacterDecorator(CharacterService c) {
		this.delegate = c;
	}
	
	public CharacterService getDelegate() {
		return this.delegate;
	}
}
