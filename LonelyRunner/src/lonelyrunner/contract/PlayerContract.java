package lonelyrunner.contract;

import java.util.ArrayList;
import java.util.HashMap;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.PlayerDecorator;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.ScreenService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Status;

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
			throw new InvariantError("exist Player P in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre) should implie p = this");	
		}
	}


	@Override
	public void init(ScreenService s, int x, int y, EngineService engine) {
		if(!(s.getCellNature(x, y)==Cell.EMP)) {
			throw new PreconditionError("init( s, "+x+", "+y+" )" , "Cell is not empty");
		}
		if(!(engine.getStatus() == Status.Playing)) {
			throw new PreconditionError("init( s, "+x+", "+y+" )" , "engine is not on playing mode");
		}
		getDelegate().init(s, x, y,engine);
		checkInvariant();
		if(!(getDelegate().getEngine().getEnvironment().getCellContent(x, y).isInside(getDelegate()) )) {
			throw new PreconditionError("init( s, "+x+", "+y+" )" , "player not set in engine environment");
		}
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
		
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		
		HashMap<Couple<Integer,Integer>,Cell> getCellNature_atpre = new HashMap<>();
		for (int i =0;i<getEnvi_atpre.getWidth();i++){
			for(int j =0;j<getEnvi_atpre.getHeight();j++) {
				if(i== getWdt_atpre-1 && j == getHgt_atpre-1 ) {
					continue;
				}
				Couple<Integer,Integer> c = new Couple<Integer, Integer>(i,j);
				Cell nc = getEnvi_atpre.getCellNature(i, j);
				getCellNature_atpre.put(c, nc);
			}
		}
		
		
		ArrayList<Hole> getHoles_atpre = new ArrayList<>();
		
		for(Hole h : getDelegate().getEngine().getHoles()) {
			getHoles_atpre.add(new Hole(h.getX(),h.getY(),h.getTime()));
		}
		
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
		if(cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("digL()" , "digL() is impossible while falling");
				}
			}
		}
		if( getWdt_atpre > 0 ) {
			if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (!(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()))) {
				Cell cell_leftdown = getEnvi_atpre.getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()-1);
				if( cell_leftdown == Cell.PLT ) {
					if(getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getCar().isEmpty() && getEnvi_atpre.getCellContent(getWdt_atpre-1, getHgt_atpre).getItem() == null) {
						if(!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()-1) == Cell.HOL)) {
							throw new PostconditionError("digL()" , "the cell should have been hol");
						}
						if((getHoles_atpre.size()+1 == getDelegate().getEngine().getHoles().size())) {
							for(Hole h : getDelegate().getEngine().getHoles()) {
								if(h.getX() == getDelegate().getWdt()-1 && h.getY() == getDelegate().getHgt()-1 && h.getTime() == 0) {
									continue;
								}
								if(!getHoles_atpre.contains(h)) {
									throw new PostconditionError("digL()" , "Must not touch the other holes in getHoles()");
								}
							}
						}
						for (int i =0;i<getEnvi_atpre.getWidth();i++){
							for(int j =0;j<getEnvi_atpre.getHeight();j++) {
								Cell nc = null;
								for(Couple<Integer,Integer> cp : getCellNature_atpre.keySet()) {
									if(cp.getElem1() == i && cp.getElem2() == j) {
										nc = getCellNature_atpre.get(cp);
									}
								}
								
								if(nc != null) {
									if(!(getEnvi_atpre.getCellNature(i, j) == nc)) {
										throw new PostconditionError("dig("+(getWdt_atpre-1)+", "+(getHgt_atpre-1)+" )" , "cell ("+i+", "+j+") changed ");
									}
								}
							
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
		
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvi());
		
		Cell cell_down = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt()-1);
		Cell cell_atpre = getDelegate().getEnvi().getCellNature(getDelegate().getWdt(), getDelegate().getHgt());
		
		HashMap<Couple<Integer,Integer>,Cell> getCellNature_atpre = new HashMap<>();
		for (int i =0;i<getEnvi_atpre.getWidth();i++){
			for(int j =0;j<getEnvi_atpre.getHeight();j++) {
				if(i== getWdt_atpre+1 && j == getHgt_atpre-1 ) {
					continue;
				}
				Couple<Integer,Integer> c = new Couple<Integer, Integer>(i,j);
				Cell nc = getEnvi_atpre.getCellNature(i, j);
				getCellNature_atpre.put(c, nc);
			}
		}
		
		ArrayList<Hole> getHoles_atpre = new ArrayList<>();
		
		for(Hole h : getDelegate().getEngine().getHoles()) {
			getHoles_atpre.add(new Hole(h.getX(),h.getY(),h.getTime()));
		}
		
		checkInvariant();
		getDelegate().digR();
		checkInvariant();
				
		// Post
		
		if(!(getHgt_atpre == getDelegate().getHgt())) {
			throw new PostconditionError("digR()" , "getHgt() == getHgt()@pre");
		}
		if(!(getWdt_atpre == getDelegate().getWdt())) {
			throw new PostconditionError("digR()" , "getHgt() == getHgt()@pre");
		}
		if( cell_atpre != Cell.LAD && cell_atpre != Cell.HDR) {
			if(cell_down != Cell.PLT && cell_down != Cell.MTL && cell_down != Cell.LAD) {
				if(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()) {
					if(!(getWdt_atpre == getDelegate().getWdt() && getHgt_atpre-1 == getDelegate().getHgt()))
						throw new PostconditionError("digR()" , "digR() is impossible while falling");
				}
			}
		}
		if( getWdt_atpre < getEnvi_atpre.getHeight()-1 ) {
			if((cell_down == Cell.MTL || cell_down == Cell.PLT) || (!(getEnvi_atpre.getCellContent(getWdt_atpre, getHgt_atpre-1).getCar().isEmpty()))) {
				Cell cell_rightdown = getDelegate().getEnvi().getCellNature(getDelegate().getWdt()+1, getDelegate().getHgt()-1);
				if( cell_rightdown == Cell.PLT ) {
					if(getEnvi_atpre.getCellContent(getWdt_atpre+1, getHgt_atpre).getCar().isEmpty() && getEnvi_atpre.getCellContent(getWdt_atpre+1, getHgt_atpre).getItem() == null) {
						if(!(getDelegate().getEnvi().getCellNature(getDelegate().getWdt()-1, getDelegate().getHgt()-1) == Cell.HOL)) {
							throw new PostconditionError("digR()" , "the cell should have been hol");
						}
						if((getHoles_atpre.size()+1 == getDelegate().getEngine().getHoles().size())) {
							for(Hole h : getDelegate().getEngine().getHoles()) {
								if(h.getX() == getDelegate().getWdt()+1 && h.getY() == getDelegate().getHgt()-1 && h.getTime() == 0) {
									continue;
								}
								if(!getHoles_atpre.contains(h)) {
									throw new PostconditionError("digR()" , "Must not touch the other holes in getHoles()");
								}
							}
						}
						for (int i =0;i<getEnvi_atpre.getWidth();i++){
							for(int j =0;j<getEnvi_atpre.getHeight();j++) {
								Cell nc = null;
								for(Couple<Integer,Integer> cp : getCellNature_atpre.keySet()) {
									if(cp.getElem1() == i && cp.getElem2() == j) {
										nc = getCellNature_atpre.get(cp);
									}
								}
								
								if(nc != null) {
									if(!(getEnvi_atpre.getCellNature(i, j) == nc)) {
										throw new PostconditionError("dig("+(getWdt_atpre+1)+", "+(getHgt_atpre-1)+" )" , "cell ("+i+", "+j+") changed ");
									}
								}
							
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
