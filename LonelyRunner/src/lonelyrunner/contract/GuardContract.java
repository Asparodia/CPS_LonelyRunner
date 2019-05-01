package lonelyrunner.contract;

import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.GuardDecorator;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Move;

public class GuardContract extends GuardDecorator {

	public GuardContract(GuardService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
	
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
	public int getId() {
		return getDelegate().getId();
	}

	@Override
	public Move getBehaviour() {
		return getDelegate().getBehaviour();
	}

	@Override
	public PlayerService getTarget() {
		return getDelegate().getTarget();
	}

	@Override
	public int getTimeInHole() {
		return getDelegate().getTimeInHole();
	}

	//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) == HOL 
			//\and  getWdt()@pre != 0 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) in {MTL,PLT}
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1) \in {EMP,HOL,LAD,HDR}
			//\and not exist GuardService c in  EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre+1)
				//implies getWdt() == getWdt()@pre-1 \and getHgt() = getHgt(C)@pre+1
	@Override
	public void climbLeft() {
		
		int getHgt_atpre = getDelegate().getHgt();
		int getWdt_atpre = getDelegate().getWdt();
		EnvironmentService getEnvi_atpre = getDelegate().getEnvi();
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_leftup = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()+1);
		Cell cell_left = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt());
		boolean noOne = getDelegate().getEnvi().getCellContent(getDelegate().getWdt()-1, getDelegate().getHgt()+1).getCar().isEmpty();
		
		checkInvariant();
		getDelegate().climbLeft();
		checkInvariant();
		
		if(cell_atpre == Cell.HOL) {
			if(getWdt_atpre != 0 ) {
				if(cell_left == Cell.MTL || cell_left == Cell.MTL || cell_left == Cell.MTL || cell_left == Cell.MTL ) {
					
				}
			}
			
		}
		
	}
	
	@Override
	public void climbRight() {
		getDelegate().climbRight();
		
	}

	@Override
	public void step() {
		getDelegate().step();
	}
	
	@Override
	public void doNeutral() {
		getDelegate().doNeutral();
	}

}
