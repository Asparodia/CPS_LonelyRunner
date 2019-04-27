package lonelyrunner.service.utils;

import lonelyrunner.service.CharacterService;

public class SetCharItem {
	
	private CharacterService player=null;
	private CharacterService guard=null;
	private Item item=null;
	
	public SetCharItem(CharacterService p,CharacterService g, Item i) {
		player = p;
		guard = g;
		item = i;
	}

	public CharacterService getPlayer() {
		return player;
	}

	public void setPlayer(CharacterService car) {
		this.player = car;
	}
	
	public CharacterService getGuard() {
		return guard;
	}

	public void setGuard(CharacterService car) {
		this.guard = car;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public void removePlayer() {
		player = null;
	}
	
	public void removeItem() {
		item = null;
	}
	
	public void removeGuard() {
		guard = null;
	}

}
