package lonelyrunner.service.utils;

import java.util.ArrayList;

import lonelyrunner.service.CharacterService;

public class SetCharItem {
	
	private ArrayList<CharacterService> car = new ArrayList<>();
	private Item item=null;


	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
	public ArrayList<CharacterService> getCar() {
		return car;
	}
	
	public boolean isInside(CharacterService c) {
		return car.contains(c);
	}

	public void addCar(CharacterService car) {
		this.car.add(car);
	}
	
	public void removeCharacter(CharacterService s) {
		car.remove(s);
	}
	
	public void removeItem() {
		item = null;
	}
	
}
