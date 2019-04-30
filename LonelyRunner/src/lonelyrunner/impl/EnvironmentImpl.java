package lonelyrunner.impl;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EnvironmentService;
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
		
		this.env = new SetCharItem[ess.getWidth()][ ess.getHeight()];
		
		for(int i =0 ; i<ess.getWidth();i++) {
			for(int j = 0;j<ess.getHeight();j++) {
				this.env[i][j] = new SetCharItem();
			}
		}
		
		for(int i = 0;i<getWidth();i++) {
			for(int j=0;j<getHeight();j++) {
				super.cells[i][j] = ess.getCellNature(i, j);
			}
		}
		
	}
	
}
