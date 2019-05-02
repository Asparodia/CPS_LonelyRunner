package lonelyrunner.contract;

import java.util.ArrayList;

import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.EngineDecorator;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.PlayerService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineContract extends EngineDecorator{

	public EngineContract(EngineService delegate) {
		super(delegate);
	}
	
	public void checkInvariant() {
		
	}

	@Override
	public EnvironmentService getEnvironment() {
		return getDelegate().getEnvironment();
	}

	@Override
	public PlayerService getPlayer() {
		return getDelegate().getPlayer();
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return getDelegate().getGuards();
	}

	@Override
	public ArrayList<Item> getTreasures() {
		return getDelegate().getTreasures();
	}

	@Override
	public Status getStatus() {
		return getDelegate().getStatus();
	}

	@Override
	public Move getNextCommand() {
		return getDelegate().getNextCommand();
	}

	@Override
	public ArrayList<Hole> getHoles() {
		return getDelegate().getHoles();
	}

	@Override
	public void init(EditableScreenService es, Couple<Integer, Integer> posChar,
			ArrayList<Couple<Integer, Integer>> posGuards, ArrayList<Couple<Integer, Integer>> posItems) {
		
		//\pre init(ES,posChar,posGuards,posItems) \req EditableScreenService::isPlayable(ES) == True 
				//\and getCellNature(ES,posChar.x,posChar.y) == EMP
				//\and \forall pos:Couple<Int,Int> in posGuards ((ScreenService::getCellNature(ES,pos.x,pos.y) = EMP) \and (pos.x != posChar.x \and pos.y != posChar.y))
				//\and \forall posI:Couple<Int,Int> in posItems ((ScreenService::getCellNature(ES,posI.x,posI.y) = EMP) \and ((posI.x != posChar.x \and posI.y != posChar.y \and ( \forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \and  posI.y!=pos.y ))) )
					//\and getCellNature(ES,posI.x,posI.y-1) == {PLT,MTL}
		
		// Pre
		if(!es.isPlayable()) {
			throw new PreconditionError("init()" , "editable screen not playable");
		}
		if(!(es.getCellNature(posChar.getElem1(), posChar.getElem2()) == Cell.EMP)){
			throw new PreconditionError("init()" , "getCellNature(ES,posChar.x,posChar.y) == EMP");
		}
		
//		for (Couple<Integer,Integer> c : posGuards) {
//			if(!(es.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP && c.getElem1() != posChar.getElem1() && c.getElem2() != posChar.getElem2())){
//				throw new PreconditionError("init()" , "\\forall pos:Couple<Int,Int> in posGuards ((ScreenService::getCellNature(ES,pos.x,pos.y) = EMP) \\and (pos.x != posChar.x \\and pos.y != posChar.y))");
//			}
//		}
//		for (Couple<Integer,Integer> c : posItems) {
//			for (Couple<Integer,Integer> cc : posGuards) {
//				if(!(c.getElem1() != cc.getElem1() && c.getElem2() != cc.getElem2())){
//					throw new PreconditionError("init()" , "\\forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \\and  posI.y!=pos.y ");
//				}
//			}
//			if(!(es.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.EMP && es.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP && c.getElem1() != posChar.getElem1() && c.getElem2() != posChar.getElem2())){
//				throw new PreconditionError("init()" , "\\\forall posI:Couple<Int,Int> in posItems ((ScreenService::getCellNature(ES,posI.x,posI.y) = EMP) \\and ((posI.x != posChar.x \\and posI.y != posChar.y \\and ( \\forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \\and  posI.y!=pos.y ))) )\n" + 
//						"					//\\and getCellNature(ES,posI.x,posI.y-1) == {PLT,MTL}}");
//			}
//		}
		
		getDelegate().init(es, posChar, posGuards, posItems);
	}

	@Override
	public void step() {
		getDelegate().step();
	}

	@Override
	public void setCommand(Move c) {
		getDelegate().setCommand(c);
		
	}
	@Override
	public void setNbLives(int l) {
		getDelegate().setNbLives(l);
	}
	
	@Override
	public int getNbLives() {
		return getDelegate().getNbLives();
	}
	
	@Override
	public int getScore() {
		return getDelegate().getScore();
	}
}
