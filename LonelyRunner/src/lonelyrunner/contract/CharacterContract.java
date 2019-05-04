package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.CharacterDecorator;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;

public class CharacterContract extends CharacterDecorator {

	public CharacterContract(CharacterService c) {
		super(c);
	}
	
	public void checkInvariant() {		
		Cell c = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		if(!(c == Cell.EMP || c== Cell.HOL || c== Cell.LAD || c== Cell.HDR)) {
			throw new InvariantError("EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) not in {EMP,HOL,LAD,HDR}");	
		}
		boolean chara = (getDelegate().getEnvi().getCellContent(getDelegate().getWdt(), getDelegate().getHgt())).isInside(getDelegate());
		if(!(chara)) {
			throw new InvariantError("exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) should implie c = this");	
		}
	}
	
	@Override
	public void init(ScreenService s, int x, int y) {
		if(!(s.getCellNature(x, y)==Cell.EMP)) {
			throw new PreconditionError("init( s, "+x+", "+y+" )" , "Cell is not empty");
		}
		getDelegate().init(s, x, y);
		checkInvariant();
	}

	@Override
	public void goLeft() {
		//Captures
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);		
		
		checkInvariant();
		getDelegate().goLeft();
		checkInvariant();
		
		
		// Post
		if(getWdt_atpre == 0) {
			if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goLeft()" , "getWdt()@pre == 0 implies getWdt() == getWdt()@pre and getHgt()@pre == getHgt()");
		}
		if(getWdt_atpre > 0) {
			if( getEnvi_atpre.getCellNature(getWdt_atpre-1, getHgt_atpre) == Cell.MTL || getEnvi_atpre.getCellNature(getWdt_atpre-1, getHgt_atpre) == Cell.PLT) {
				if(!(getWdt_atpre == getDelegate().getWdt()  && getHgt_atpre == getDelegate().getHgt()))
					throw new PostconditionError("goLeft()" , "getWdt()@pre > 0 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT} implies getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");
				}
			}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("goLeft()" , "goLeft() is impossible while falling");
				}
			}
		}
		if(getWdt_atpre > 0 && getEnvi_atpre.getCellNature(getWdt_atpre-1, getHgt_atpre) != Cell.MTL && getEnvi_atpre.getCellNature(getWdt_atpre-1, getHgt_atpre) != Cell.PLT) {
			if( (cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) || (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD || !(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) )){
					if(!(getWdt_atpre-1 == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
						throw new PostconditionError("goLeft()" , "(getWdt()@pre > 0 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) not in {MTL,PLT} )\n" + 
								"		and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) in {LAD,HDR}\n" + 
								"			or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n" + 
								"			or exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n" + 
								"		should implie getWdt() == getWdt()@pre - 1 and getHgt()@pre == getHgt()");
			}
		}
	}

	@Override
	public void goRight() {
		
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		
		checkInvariant();
		getDelegate().goRight();
		checkInvariant();
		
		// Post
 
		if(getWdt_atpre == getEnvi_atpre.getWidth()-1) {
			if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goRight()" , "getWdt()@pre == EnvironmentService::getWidth()-1 implies getWdt() == getWdt()@pre and getHgt()@pre == getHgt()");
		}
		if( getWdt_atpre < getDelegate().getWdt() - 1) {
			if( (getEnvi_atpre.getCellNature(getWdt_atpre+1, getHgt_atpre) == Cell.MTL || getEnvi_atpre.getCellNature(getWdt_atpre+1, getHgt_atpre) ==Cell.PLT)) {
				if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
					throw new PostconditionError("goRight()" , "getWdt()@pre < EnvironmentService::getWidth()-1 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) in {MTL,PLT} should implie" + 
							"getWdt()@pre == getWdt() and getHgt()@pre == getHgt()");
			}
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if( getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()  ) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("goRight()" , "goRight() is impossible while falling");
				}
			}
		}
		if(getWdt_atpre < getEnvi_atpre.getWidth()-1 && getEnvi_atpre.getCellNature(getWdt_atpre+1, getHgt_atpre) != Cell.MTL && getEnvi_atpre.getCellNature(getWdt_atpre+1,getHgt_atpre) != Cell.PLT) {
			if( (cell_atpre == Cell.LAD || cell_atpre == Cell.HDR) || (cell_down == Cell.PLT || cell_down == Cell.MTL || cell_down == Cell.LAD || !(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) ) ){
					if(!(getWdt_atpre+1 == getDelegate().getWdt() && getHgt_atpre == getDelegate().getHgt()))
						throw new PostconditionError("goRight()" , "( getWdt()@pre < EnvironmentService::getWidth()-1 and EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) in {LAD,HDR}\n" + 
								"			or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n" + 
								"			or exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n" + 
								"		should implie getWdt() == getWdt()@pre +1 and getHgt()@pre == getHgt()");
			}
		}
	}

	@Override
	public void goUp() {
		
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		
		checkInvariant();
		getDelegate().goUp();
		checkInvariant();
		
		// Post
		if(getHgt_atpre ==getEnvi_atpre.getHeight()-1) {
			if(!(getHgt_atpre == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goUp()" , "getHgt()@pre == EnvironmentService::getHeight()-1 should implies getHgt() == getHgt()@pre and getWdt() == getWdt()@pre ");
		}
		if( getHgt_atpre <getEnvi_atpre.getHeight()-1) {
			Cell cell_up = getEnvi_atpre.getCellNature(getWdt_atpre, getHgt_atpre+1);
			if(cell_up == Cell.MTL || cell_up ==Cell.PLT || cell_up ==Cell.HDR ) {
				if(!(getHgt_atpre == getDelegate().getHgt()  && getWdt_atpre == getDelegate().getWdt()))
					throw new PostconditionError("goUp()" , "EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT, HDR} should implies getHgt() = getHgt()@pre and getWdt() == getWdt()@pre");
			}
		}
				
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("goUp()" , "goUp() is impossible while falling");
			
				}
			}
		}
		if(getHgt_atpre < getEnvi_atpre.getHeight()-1) {
			Cell cell_up = getEnvi_atpre.getCellNature(getWdt_atpre, getHgt_atpre+1);
			if(cell_atpre == Cell.LAD && (cell_up == Cell.LAD || cell_up == Cell.EMP)) {
				if(!(getHgt_atpre+1 == getDelegate().getHgt()  && getWdt_atpre == getDelegate().getWdt())) {
					throw new PostconditionError("goUp()" , "(getHgt()@pre <EnvironmentService::getHeight() -  1)\n" + 
							"		and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == LAD\n" + 
							"		and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {LAD,EMP} \n" + 
							"		should implie getHgt() = getHgt()@pre + 1 and getWdt() == getWdt()@pre");
			}
			}
		}
	}

	@Override
	public void goDown() {		
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		
		checkInvariant();
		getDelegate().goDown();
		checkInvariant();
		
		// Post
		
		if(getHgt_atpre ==0) {
			if(!(getHgt_atpre == getDelegate().getHgt()  && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()" , "getHgt()@pre == 0 should implies getHgt()==getHgt()@pre and getWdt() == getWdt()@pre");
		}
		if(getHgt_atpre >0 && (cell_down == Cell.MTL || cell_down ==Cell.PLT)) {
			if(!(getHgt_atpre == getDelegate().getHgt()  && getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goDown()" , "(EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) in {MTL, PLT} should implie getHgt()==getHgt()@pre and getWdt() == getWdt()@pre");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty() ) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("goDown()" , "goDown() impossible while falling");
				}
			}
		}
		if(getHgt_atpre > 0) {
			if(cell_atpre == Cell.EMP || cell_atpre == Cell.LAD || cell_atpre == Cell.HDR || cell_atpre == Cell.HOL ) {
				if(cell_down == Cell.EMP || cell_down == Cell.LAD  || cell_down == Cell.HOL || cell_down == Cell.HDR) {
						if(! ( getHgt_atpre-1 == getDelegate().getHgt() && getWdt_atpre == getDelegate().getWdt()) ) {
							throw new PostconditionError("goDown()" , " getHgt()@pre > 0 and Environment::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) in {LAD,HDR,EMP,HOL}\r\n" + 
									"		and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre-1) in {EMP,LAD,HDR,HOL}\r\n" + 
									"			should implie getHgt()==getHgt()@pre-1 and getWdt() == getWdt()@pre");
						}
							
				}
			}
		}
	}

}
