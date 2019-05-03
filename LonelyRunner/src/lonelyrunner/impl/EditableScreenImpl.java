package lonelyrunner.impl;

import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.utils.Cell;

public class EditableScreenImpl extends ScreenImpl  implements EditableScreenService{
	private boolean playable=false;
	
	@Override
	public boolean isPlayable() {
		return playable;
	}
	
	@Override
	public void init(int h, int w) {
		super.init(h, w);
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		cells[x][y] = c;
		
		playable = true;
		for(int i=0;i<getWidth();i++) {
			for(int j=0;j<getHeight();j++) {
				if(!(cells[i][j] != Cell.HOL)) {
					playable = false;
					break;
				}
					
			}
		}
		for(int i=0;i<getWidth();i++) {
			if(!(cells[i][0] == Cell.MTL)) {
				playable = false;
				break;
			}
		}
	}

}
