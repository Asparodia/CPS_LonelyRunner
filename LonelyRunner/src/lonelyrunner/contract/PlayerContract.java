package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.PlayerDecorator;
import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;

public class PlayerContract extends PlayerDecorator{

	public PlayerContract(CharacterService c) {
		super(c);
	}
	
	public void checkInvariant() {
		Cell c = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		if(!(c == Cell.EMP || c== Cell.HOL || c== Cell.LAD || c== Cell.HDR)) {
			throw new InvariantError("EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\in {EMP,HOL,LAD,HDR}");	
		}
		CharacterService chara = (getDelegate().getEnvi().getCellContent(getDelegate().getWdt(), getDelegate().getHgt())).getCar();
		if(chara != null) {
			if(!(chara.equals(this))) {
				throw new InvariantError("\\exist Character X in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \\implies c = this");	
			}
		}
	}


	@Override
	public void init(ScreenService s, int x, int y, EngineService engine) {
		if(!(getDelegate().getEnvi().getCellNature(x, y)==Cell.EMP)) {
			throw new PreconditionError("init( s, "+x+", "+y+" )" , "Cell is not empty");
		}
		getDelegate().init(s, x, y,engine);
		checkInvariant();
	}

	@Override
	public void step() {
		checkInvariant();
		getDelegate().step();
		checkInvariant();
	}

	@Override
	public void digL() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		Cell cell_leftdown = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()-1);
		
		checkInvariant();
		getDelegate().digL();
		checkInvariant();
				
		// Post
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("digL()" , "getHgt() == getHgt()@pre");
		}
		if(!(getWdt_atpre == getDelegate().getWdt())) {
			throw new PostconditionError("digL()" , "getHgt() == getHgt()@pre");
		}
		if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() != null)) {
			if(cell_leftdown == Cell.PLT) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre-1).getCar() == null) {
					if(!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()-1) == Cell.HOL)) {
						throw new PostconditionError("digL()" , "(EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \\in {MTL,PLT} \\or \\exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))\n" + 
								"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == PLT \\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1)\n" + 
								"				//\\implies  EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == HOL");
					}
				}
			}
		}
		
	}

	@Override
	public void digR() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		Cell cell_rightdown = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()-1);
		
		checkInvariant();
		getDelegate().digL();
		checkInvariant();
				
		// Post
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("digR()" , "getHgt() == getHgt()@pre");
		}
		if(!(getWdt_atpre == getDelegate().getWdt())) {
			throw new PostconditionError("digR()" , "getHgt() == getHgt()@pre");
		}
		if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar() != null)) {
			if(cell_rightdown == Cell.PLT) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre+1, getHgt_atpre-1).getCar() == null) {
					if(!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt()+1, getDelegate().getHgt()-1) == Cell.HOL)) {
						throw new PostconditionError("digR()" , "(EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) \\in {MTL,PLT} \\or \\exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))\n" + 
								"			//\\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == PLT \\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1)\n" + 
								"				//\\implies  EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == HOL");
					}
				}
			}
		}
	}

	@Override
	public EngineService getEngine() {
		return getDelegate().getEngine(); // bizarre
	}

}
