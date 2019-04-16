package lonelyrunner.contract;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.utils.Move;

public class GuardContract extends CharacterContract implements GuardService {

	public GuardContract(GuardService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
	
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Move getBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CharacterService getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTimeInHole() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void climbLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
