package lonelyrunner.contract;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Item;

public class EnvironmentContract extends ScreenContract implements EnvironmentService {

	public EnvironmentContract(EnvironmentService delegate) {
		super(delegate);
	}
	public void checkInvariant() {
		
	}
	@Override
	public Couple<CharacterService, Item> getCellContent(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
