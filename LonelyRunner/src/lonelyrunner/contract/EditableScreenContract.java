package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.utils.Cell;

public class EditableScreenContract extends ScreenContract implements EditableScreenService {

	
	public EditableScreenContract(EditableScreenService ess) {
			super(ess);
		}
	
	@Override
	public EditableScreenService getDelegate() {
		return (EditableScreenService) super.getDelegate();
	}
	
	public void checkInvariant() {
			
		}
	
	@Override
	public void init(int h,int w) {
		// pre
		if(!(h>0)) {
			throw new PreconditionError("init("+h+", "+w+" )" , "height must be strictly positive");
		}
		if(!(w>0)) {
			throw new PreconditionError("init("+h+", "+w+" )" , "width must be strictly positive");
		}
		// run
		getDelegate().init(h,w);   // do not inherit from init, so do not call super !
		// inv post
		checkInvariant();
		// post
		// post
		if(!(h==super.getHeight())) {
			throw new PostconditionError("init("+h+", "+w+" )" , "h is not equal to getHeight after init");
		}
		if(!(w==super.getWidth())) {
			throw new PostconditionError("init("+h+", "+w+" )" , "w is not equal to getWidth after init");
		}
		for (int i =0;i<super.getWidth();i++){
			for(int j =0;j<super.getHeight();j++) {
				if(!(super.getCellNature(i, j) == Cell.EMP)) {
					throw new PostconditionError("init("+h+", "+w+" )" , "cell ("+i+", "+j+") is not EMP");
				}
			}
			
		}
	}
	
	@Override
	public boolean isPlayable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		// TODO Auto-generated method stub
		
	}
}
