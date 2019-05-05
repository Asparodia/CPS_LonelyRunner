package lonelyrunner.contract;

import java.util.HashMap;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;

public class EditableScreenContract extends ScreenContract implements EditableScreenService {

	
	public EditableScreenContract(EditableScreenService ess) {
			super(ess);
		}
	
	@Override
	public EditableScreenService getDelegate() {
		return (EditableScreenService) super.getDelegate();
	}
	
	public void checkInvariant() {
		super.checkInvariant(); 
		Boolean t = true;
		for(int x=0;x<getWidth();x++) {
			for(int y=0;y<getHeight();y++) {
				if(!(getCellNature(x, y) != Cell.HOL)) {
					t = false;
				}
			}
		}
		for(int x=0;x<getWidth();x++) {
			if(!(getCellNature(x, 0) == Cell.MTL)) {
				t = false;
			}
		}
		if(!(getDelegate().isPlayable() == t)) {
			throw new InvariantError("isPlayable() =min= forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() \n" + 
					"									getCellNature(x,y) != HOL \n"+
					"									and forall u:int with 0<=u<getWidth() getCellNature(u,0) = MTL");
		}
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
		Boolean r = getDelegate().isPlayable();
		return r;
	}

	@Override
	public void setNature(int x, int y, Cell c) {
		if(!(x>=0 && x<getWidth())) {
			throw new PreconditionError("setNature("+x+", "+y+" c )" , "x must be between 0 and strictly inf to getWidth");
		}
		if(!(y>=0 && y<getHeight())) {
			throw new PreconditionError("setNature("+x+", "+y+" c )" , "y must be between 0 and strictly inf to getHeight");
		}
		HashMap<Couple<Integer,Integer>,Cell> getCellNature_atpre = new HashMap<Couple<Integer,Integer>,Cell>();
		for(int a=0;a<getWidth();a++) {
			for(int b=0;b<getHeight();b++) {
				Couple<Integer,Integer> couple = new Couple<Integer, Integer>(a,b);
				getCellNature_atpre.put(couple, getCellNature(a, b));
			}
		}
		checkInvariant();
		getDelegate().setNature(x, y, c);
		checkInvariant();
		if(!(getDelegate().getCellNature(x, y) == c)) {
			throw new PostconditionError("setNature("+x+", "+y+" c )", " cellNature of "+x+" "+y+" didn't change ");
		}
		for(int a=0;a<getWidth();a++) {
			for(int b=0;b<getHeight();b++) {
				if(a != x || b != y) {
					Cell nc = null;
					for(Couple<Integer,Integer> cp : getCellNature_atpre.keySet()) {
						if(cp.getElem1() == a && cp.getElem2() == b) {
							nc = getCellNature_atpre.get(cp);
						}
					}
					if(nc!=null) {
					if(!(getCellNature(a, b) == nc)) {
						throw new PostconditionError("setNature("+x+", "+y+" c )" , "cellNature of "+x+" "+y+" changed, wasn't suppose to change ");
					}
					}
				}
			}
		}
	}
}
