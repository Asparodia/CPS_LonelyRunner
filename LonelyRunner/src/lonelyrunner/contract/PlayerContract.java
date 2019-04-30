package lonelyrunner.contract;

import java.util.ArrayList;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.PlayerDecorator;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Hole;

public class PlayerContract extends PlayerDecorator{

	public PlayerContract(PlayerService c) {
		super(c);
	}
	
	public void checkInvariant() {
		Cell c = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		if(!(c == Cell.EMP || c== Cell.HOL || c== Cell.LAD || c== Cell.HDR)) {
			throw new InvariantError("EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) not in {EMP,HOL,LAD,HDR}");	
		}
		boolean chara = (getDelegate().getEnvi().getCellContent(getDelegate().getWdt(), getDelegate().getHgt())).isInside(getDelegate());
		if(!(chara)) {
			throw new InvariantError("Exist Character X in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) should implie c = this");	
		}
	}


	@Override
	public void init(ScreenService s, int x, int y, EngineService engine) {
		if(!(s.getCellNature(x, y)==Cell.EMP)) {
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
		ArrayList<Hole> getHoles_atpre = getDelegate().getEngine().getHoles();
		
		checkInvariant();
		getDelegate().digL();
		checkInvariant();
				
		// Post
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("digL()" , "getHgt() should be equals to getHgt()@pre");
		}
		if(!(getWdt_atpre == getDelegate().getWdt())) {
			throw new PostconditionError("digL()" , "getHgt() should be equals to getHgt()@pre");
		}
		if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (!(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()))) {
			if(cell_leftdown == Cell.PLT) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre-1).getCar().isEmpty()) {
					if(!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()-1) == Cell.HOL)) {
						throw new PostconditionError("digL()" , "(EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {MTL,PLT} or exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))\n" + 
								"			and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == PLT and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1)\n" + 
								"				should implie  EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == HOL");
					}
					if((getHoles_atpre.size()+1 == getDelegate().getEngine().getHoles().size())) {
						for(Hole h : getDelegate().getEngine().getHoles()) {
							if(h.getX() == getDelegate().getWdt()-1 && h.getY() == getDelegate().getHgt()-1 && h.getTime() == 0) {
								continue;
							}
							if(!getHoles_atpre.contains(h)) {
								throw new PostconditionError("digL()" , "(EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {MTL,PLT} or exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))\n" + 
										"			and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1) == PLT and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre-1)\n" + 
										"				should implie getEngine()::getHoles() ==  getEngine()@pre::getHoles() Union Hole(getWdt()@pre-1,getHgt()@pre-1,0)");
							}
						}
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
		ArrayList<Hole> getHoles_atpre = getDelegate().getEngine().getHoles();
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
		if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (!(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()))) {
			if(cell_rightdown == Cell.PLT) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre+1, getHgt_atpre-1).getCar().isEmpty()) {
					if(!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt()+1, getDelegate().getHgt()-1) == Cell.HOL)) {
						throw new PostconditionError("digR()" , "(EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {MTL,PLT} or exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))\n" + 
								"			and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == PLT and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1)\n" + 
								"				should implie  EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == HOL");
					}
					if((getHoles_atpre.size()+1 == getDelegate().getEngine().getHoles().size())) {
						for(Hole h : getDelegate().getEngine().getHoles()) {
							if(h.getX() == getDelegate().getWdt()+1 && h.getY() == getDelegate().getHgt()-1 && h.getTime() == 0) {
								continue;
							}
							if(!getHoles_atpre.contains(h)) {
								throw new PostconditionError("digL()" , "(EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {MTL,PLT} or exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1))\n" + 
										"			and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1) == PLT and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre+1,getHgt()@pre-1)\n" + 
										"				should implie getEngine()::getHoles() ==  getEngine()@pre::getHoles() Union Hole(getWdt()@pre+1,getHgt()@pre-1,0)");
							}
						}
					}
				}
			}
		}
	}

	@Override
	public EngineService getEngine() {
		return getDelegate().getEngine(); // bizarre
	}
	
	@Override
	public void doNeutral() {
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		
		checkInvariant();
		getDelegate().doNeutral();
		checkInvariant();
		
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("doNeutral()" , "EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) not in {LAD,HDR} \n" + 
								"			and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) not in {PLT,MTL,LAD}\n" + 
								"			and not exist CharacterService c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)\n" + 
								"				should implie getWdt() == getWdt()@pre \\and getHgt() == getHgt()@pre - 1");
			
				}
			}
		}
	}

}
