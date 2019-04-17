package lonelyrunner.service.utils;

import lonelyrunner.service.CharacterService;

public class SetCharItem {
	private CharacterService car;
	private Item item;
	
	public SetCharItem(CharacterService c, Item i) {
		car = c;
		item = i;
	}

	public CharacterService getCar() {
		return car;
	}

	public void setCar(CharacterService car) {
		this.car = car;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public void removeCharacter() {
		car = null;
	}
	
	public void removeItem() {
		item = null;
	}

}
