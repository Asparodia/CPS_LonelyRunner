package lonelyrunner.service.utils;

import java.util.ArrayList;

import lonelyrunner.decorators.Decorator;
import lonelyrunner.service.CharacterService;

public class SetCharItem {
	
	private ArrayList<CharacterService> car = new ArrayList<>();
	private Item item=null;


	public Item getItem() {
		return item;
	}

	private Object getDelegate(Object c) {
		if (c instanceof Decorator) {
			return  ((Decorator)c).getDelegate();
		}
		return c;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	
	public ArrayList<CharacterService> getCar() {
		return car;
	}
	
	public boolean isInside(CharacterService c) {
		return car.contains(getDelegate(c));
	}

	public void addCar(CharacterService car) {
		this.car.add((CharacterService)getDelegate(car));
	}
	
	public void removeCharacter(CharacterService s) {
		car.remove(getDelegate(s));
	}
	
	public void removeItem() {
		item = null;
	}
	
}
