package lonelyrunner.impl;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.SetCharItem;

public class EnvironmentImpl extends ScreenImpl implements EnvironmentService{

	SetCharItem[][] env;
	
	@Override
	public SetCharItem getCellContent(int x, int y) {
		return env[x][y];
	}

	@Override
	public void init(EditableScreenService ess) {
		super.init(ess.getHeight(), ess.getWidth());
		this.env = new SetCharItem[ess.getWidth()][ ess.getHeight() ];
		
	}
	
//	public void setCellContent(int x, int y,CharacterService c, Item i) {
//		env[x][y].setCar(c);
//		env[x][y].setItem(i);
//	}

}
