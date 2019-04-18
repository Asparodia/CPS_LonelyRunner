package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.CharacterDecorator;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;

public class CharacterContract extends CharacterDecorator {

	public CharacterContract(CharacterService c) {
		super(c);
	}
	
	
	public void checkInvariant() {		
		Cell c = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		if(!(c == Cell.EMP || c== Cell.HOL || c== Cell.LAD || c== Cell.HDR)) {
			throw new InvariantError("EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\in {EMP,HOL,LAD,HDR}");	
		}
		
		CharacterService chara = (getDelegate().getEnvi().getCellContent(getDelegate().getWdt(), getDelegate().getHgt())).getCar();
		if(!(chara.equals(this))) {
			throw new InvariantError("\\exist Character X in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\implies c = this");	
		}
		
	}
	
	@Override
	public void init(ScreenService s, int x, int y) {
		if(!(getDelegate().getEnvi().getCellNature(x, y)==Cell.EMP)) {
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
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		Cell cell_left = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt());
		
		checkInvariant();
		getDelegate().goLeft();
		checkInvariant();
		
		// Post
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("goLeft()" , "getHgt() == getHgt()@pre");
		}
		if(getWdt_atpre ==0) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goLeft()" , "getWdt()@pre == 0 \\implies getWdt() == getWdt()@pre");
		}
		if( cell_left == Cell.MTL || cell_left ==Cell.PLT || cell_left ==Cell.HDR) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goLeft()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \\in {MTL,PLT,HDR} \\implies getWdt()@pre == getWdt()");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() == null) {
					if(!(getWdt_atpre == getDelegate().getWdt()))
						throw new PostconditionError("goLeft()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\not in {LAD,HDR} \n" + 
								"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \\not in {PLT,MTL,LAD}\n" + 
								"			//\\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
								"			//\\implies getWdt() == getWdt()@pre");
			
				}
			}
		}
		if(getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar() != null) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goLeft()" , "\\exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \\implies getWdt() == getWdt()@pre");
			
		}
		if(getDelegate().getWdt() != 0 && cell_left != Cell.MTL && cell_left != Cell.PLT) {
			if( (cell_atpre == Cell.LAD && cell_atpre == Cell.HDR) || (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) || (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() != null) ){
				if( (getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar() == null) ) {
					if(!(getWdt_atpre-1 == getDelegate().getWdt()))
						throw new PostconditionError("goLeft()" , " getWdt()@pre != 0 \\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \\not in {MTL,PLT}\n" + 
								"		//\\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\in {LAD,HDR}\n" + 
								"			//\\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n" + 
								"			//\\or \\exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n" + 
								"		//\\and \\not (\\exist Character c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre)) ) \\implies getWdt() == getWdt()@pre - 1");
					
			
				}
			}
		}
	}

	@Override
	public void goRight() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		Cell cell_right = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()+1, getDelegate().getHgt());
		
		checkInvariant();
		getDelegate().goRight();
		checkInvariant();
		
		// Post

		
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("goRight()" , "getHgt() == getHgt()@pre");
		}
		if(getWdt_atpre ==getEnvi_atpre.getWidth()-1) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goRight()" , "getWdt()@pre == EnvironmentService::getWidth()-1 \\implies getWdt() == getWdt()@pre");
		}
		if( cell_right == Cell.MTL || cell_right ==Cell.PLT || cell_right ==Cell.HDR) {
			if(!(getWdt_atpre == getDelegate().getWdt()))
				throw new PostconditionError("goRight()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \\in {MTL,PLT,HDR} \\implies\n" + 
						"			// getWdt()@pre == getWdt()");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() == null) {
					if(!(getWdt_atpre == getDelegate().getWdt()))
						throw new PostconditionError("goRight()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\not in {LAD,HDR} \n" + 
								"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \\not in {PLT,MTL,LAD}\n" + 
								"			//\\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
								"			//\\implies getWdt() == getWdt()@pre");
			
				}
			}
		}

//		if(getEnvi_atpre.getCellContent(getWdt_atpre+1, getHgt_atpre).getCar() != null) {
//			if(!(getWdt_atpre == getDelegate().getWdt()))
//				throw new PostconditionError("goRight()" , "\\exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \\implies getWdt() == getWdt()@pre");
//			
//		}
		if(getDelegate().getWdt() != 0 && cell_right != Cell.MTL && cell_right != Cell.PLT) {
			if( (cell_atpre == Cell.LAD && cell_atpre == Cell.HDR) || (cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) || (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() != null) ){
				if( (getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar() == null) ) {
					if(!(getWdt_atpre+1 == getDelegate().getWdt()))
						throw new PostconditionError("goRight()" , "getWdt()@pre != 0 \\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre) \\not in {MTL,PLT}\n" + 
								"		//\\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt(),getHgt()@pre) \\in {LAD,HDR}\n" + 
								"			//\\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}\n" + 
								"			//\\or \\exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )\n" + 
								"		//\\and \\not (\\exist Character c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre+1,getHgt()@pre)) ) \\implies getWdt() == getWdt()@pre +1");
				}
			}
		}
	}

	@Override
	public void goUp() {
		//\post: (getHgt()@pre != EnvironmentService::getHeight() -  1)
			//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) == EMP
			//\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) == LAD
				//\and EnvironmentService::CellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1) in {MTL, PLT} \or exists Character c in EnvironmentService::CellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1)
				//\implies getHgt() = getHgt()@pre + 1
			//\or EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) = LAD
				//\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {EMP, LAD}
					//\implies getHgt() = getHgt()@pre + 1
		
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_up = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()+1);
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		
		checkInvariant();
		getDelegate().goUp();
		checkInvariant();
		
		// Post

		if(!(getWdt_atpre == getDelegate().getWdt())) {
			throw new PostconditionError("goUp()" , "getWdt() == getWdt()@pre");
		}
		if(getHgt_atpre ==getEnvi_atpre.getHeight()-1) {
			if(!(getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goUp()" , "getHgt()@pre == EnvironmentService::getHeight()-1 \\implies getHgt() == getHgt()@pre ");
		}
		if( (cell_up == Cell.MTL || cell_up ==Cell.PLT || cell_up ==Cell.HDR)|| (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre+1).getCar() != null) ) {
			if(!(getHgt_atpre == getDelegate().getHgt()))
				throw new PostconditionError("goUp()" , "EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre + 1) in {MTL, PLT, HDR} \\or exists Character c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt(), getHgt() + 1)\n" + 
						"			//\\implies getHgt() = getHgt()@pre");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() == null) {
					if(!(getHgt_atpre == getDelegate().getHgt()))
						throw new PostconditionError("goUp()" , "EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre) not in {LAD,HDR}\n" + 
								"			//\\and EnvironmentService::getCellNature(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1) not in {MTL, PLT, LAD} \n" + 
								"			//\\and not exists Character c in EnvironmentService::getCellContent(getEnvi()@pre, getWdt()@pre, getHgt()@pre - 1)\n" + 
								"				//\\implies getHgt() = getHgt(C)@pre");
			
				}
			}
		}
		
	}

	@Override
	public void goDown() {
		getDelegate().goDown();
		
	}

}
