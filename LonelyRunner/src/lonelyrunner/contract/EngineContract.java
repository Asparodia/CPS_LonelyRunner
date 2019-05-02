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
		
		for (Couple<Integer,Integer> c : posGuards) {
			if(!((es.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP) && c.getElem1() != posChar.getElem1() || c.getElem2() != posChar.getElem2())){
				throw new PreconditionError("init()" , "\\forall pos:Couple<Int,Int> in posGuards ((ScreenService::getCellNature(ES,pos.x,pos.y) = EMP) \\and (pos.x != posChar.x \\or pos.y != posChar.y))");
			}
		}
		for (Couple<Integer,Integer> c : posItems) {
			for (Couple<Integer,Integer> cc : posGuards) {
				if(!(c.getElem1() != cc.getElem1() || c.getElem2() != cc.getElem2())){
					throw new PreconditionError("init()" , "\\forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \\and  posI.y!=pos.y ");
				}
			}
			if(!( (es.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.MTL || es.getCellNature(c.getElem1(), c.getElem2()-1) == Cell.PLT )  && es.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP && c.getElem1() != posChar.getElem1() || c.getElem2() != posChar.getElem2())){
				throw new PreconditionError("init()" , "\\\forall posI:Couple<Int,Int> in posItems ((ScreenService::getCellNature(ES,posI.x,posI.y) = EMP) \\and ((posI.x != posChar.x \\and posI.y != posChar.y \\and ( \\forall pos:Couple<Int,Int> in posGuards posI.x!= pos.x \\and  posI.y!=pos.y ))) )\n" + 
						"					//\\and getCellNature(ES,posI.x,posI.y-1) == {PLT,MTL}}");
			}
		}
		
		getDelegate().init(es, posChar, posGuards, posItems);
	}

	//\post:  \forall T:Item \in getTreasures()
		// T \in EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
			//\implies T not in getTreasures() \and getScore() == getScore()@pre + 1
	//\post: \forall G:Guard \in getGuards() 
		// G \in EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
			//\implies  getNbLives() == getNbLives()@pre - 1
	//\post: \forall G:Guard \in getGuards()@pre
		// t:Item \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)) \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G)) == HOL
			//\implies t \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)-1)
		// t:Item \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)) \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G)) != HOL
				//\implies t \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G))
	//\forall H:Hole \in getHoles()
		//H.time == 15 \and PlayerService p in EnvironmentService::getCellContent(H.x,H.y)
			//\implies getNbLives() == getNbLives()@pre - 1
		//H.time == 15 \and GuardService g in EnvironmentService::getCellContent(H.x,H.y)
			//\implies getHoles() == getHoles()@pre \minus H \and g.getHdt() == g.initPos.x \and  g.getWdt() == g.initPos.y
		//H.time == 15 \implies getHoles() == getHoles()@pre \minus H
	//\post: getTreasures() == empty \implies getStatus() == Win
	//\post: getNbLives() <= 0 \implies getStatus() == Loss
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
