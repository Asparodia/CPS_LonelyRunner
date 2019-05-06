package lonelyrunner.contract;

import java.util.ArrayList;
import java.util.Vector;

import lonelyrunner.contract.contracterr.InvariantError;
import lonelyrunner.contract.contracterr.PostconditionError;
import lonelyrunner.contract.contracterr.PreconditionError;
import lonelyrunner.decorators.EngineDecorator;
import lonelyrunner.impl.EnvironmentImpl;
import lonelyrunner.impl.GuardImpl;
import lonelyrunner.service.CharacterService;
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

public class EngineContract extends EngineDecorator {

	public EngineContract(EngineService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		EnvironmentService env = getDelegate().getEnvironment();
		PlayerService player = getDelegate().getPlayer();
		ArrayList<GuardService> guards = getDelegate().getGuards();
		ArrayList<Hole> holes = getDelegate().getHoles();
		ArrayList<Item> items = getDelegate().getTreasures();

		if (!(env.getCellContent(player.getWdt(), player.getHgt()).isInside(player))) {
			throw new InvariantError("player is not synchro with the engine ");
		}
		for (GuardService g : guards) {
			if (!(env.getCellContent(g.getWdt(), g.getHgt()).isInside(g))) {
				throw new InvariantError("guards are not synchro with the engine ");
			}
		}
		for (Item i : items) {
			if (!(env.getCellContent(i.getCol(), i.getHgt()).getItem().getId() == i.getId())) {
				throw new InvariantError("items are not synchro with the engine ");
			}
		}
		for (Hole h : holes) {
			if (!(env.getCellNature(h.getX(), h.getY()) == Cell.HOL)) {
				throw new InvariantError("holes are not synchro with the engine ");
			}
		}
		if (getDelegate().getNbLives() < 0) {
			if (!(getDelegate().getStatus() == Status.Loss)) {
				throw new InvariantError("the player should have lost he doesnt have HP");
			}
		}
		if (getDelegate().getTreasures().isEmpty()) {
			if (!(getDelegate().getStatus() == Status.Win)) {
				throw new InvariantError("the player should have won there is no more treasure left");
			}
		}

	}

	@Override
	public int getNbLives() {
		return getDelegate().getNbLives();
	}

	@Override
	public int getScore() {
		return getDelegate().getScore();
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
		if (!es.isPlayable()) {
			throw new PreconditionError("init()", "editable screen not playable");
		}
		if (!(es.getCellNature(posChar.getElem1(), posChar.getElem2()) == Cell.EMP
				&& (posChar.getElem1() >= 0 && posChar.getElem2() < es.getWidth()))) {
			throw new PreconditionError("init()",
					"to init a player you have to be on a emp case and player coord must be in the screen");
		}
		for (Couple<Integer, Integer> c : posGuards) {
			if (!((es.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP)
					&& (c.getElem1() != posChar.getElem1() || c.getElem2() != posChar.getElem2())
					&& (c.getElem1() >= 0 && c.getElem2() < es.getWidth()))) {
				throw new PreconditionError("init()",
						"guard must be init on a emp case and not on the same case as the player and guard coord must be in the screen");
			}
		}
		if (posItems.isEmpty()) {
			throw new PreconditionError("init()", "Cant start a game without treasures");
		}
		for (Couple<Integer, Integer> c : posItems) {
			for (Couple<Integer, Integer> cc : posGuards) {
				if (!(c.getElem1() != cc.getElem1() || c.getElem2() != cc.getElem2())) {
					throw new PreconditionError("init()", "can't put a item on  the same case as a guard ");
				}
			}
			if (!(((es.getCellNature(c.getElem1(), c.getElem2() - 1) == Cell.MTL
					|| es.getCellNature(c.getElem1(), c.getElem2() - 1) == Cell.PLT)
					&& es.getCellNature(c.getElem1(), c.getElem2()) == Cell.EMP)
					&& (c.getElem1() != posChar.getElem1() || c.getElem2() != posChar.getElem2())
					&& (c.getElem1() >= 0 && c.getElem2() < es.getWidth()))) {
				throw new PreconditionError("init()",
						"cant put an item on a case if it's not emp or above a mtl or a plt case and not on the same case as a the player and item coord must be in the screen");
			}
		}
		getDelegate().init(es, posChar, posGuards, posItems);
		checkInvariant();

		if (!(getDelegate().getStatus() == Status.Playing)) {
			throw new PostconditionError("init()", "engine status should be at playing after init");
		}
		if (!(getDelegate().getEnvironment()
				.getCellContent(getDelegate().getPlayer().getWdt(), getDelegate().getPlayer().getHgt())
				.isInside(getDelegate().getPlayer()))) {
			throw new PostconditionError("init()", "player is not on the right place");
		}
		for (int i = 0; i < getDelegate().getEnvironment().getWidth(); i++) {
			for (int j = 0; j < getDelegate().getEnvironment().getHeight(); j++) {
				if (i != getDelegate().getPlayer().getWdt() || j != getDelegate().getPlayer().getHgt()) {
					if (!(!getDelegate().getEnvironment().getCellContent(i, j).isInside(getDelegate().getPlayer()))) {
						throw new PostconditionError("init()", "player is not on the right place");
					}
				}
			}
		}
		for (GuardService gs : getDelegate().getGuards()) {
			if (!(getDelegate().getEnvironment().getCellContent(gs.getWdt(), gs.getHgt()).isInside(gs))) {
				throw new PostconditionError("init()", "guard is not on the right place");
			}
			for (int i = 0; i < getDelegate().getEnvironment().getWidth(); i++) {
				for (int j = 0; j < getDelegate().getEnvironment().getHeight(); j++) {
					if (i != gs.getWdt() || j != gs.getHgt()) {
						if (!(!getDelegate().getEnvironment().getCellContent(i, j).isInside(gs))) {
							throw new PostconditionError("init()", "guard is not on the right place");
						}
					}
				}
			}
		}
		for (Item it : getDelegate().getTreasures()) {
			if (!(getDelegate().getEnvironment().getCellContent(it.getCol(), it.getHgt()).getItem().getId() == it
					.getId())) {
				throw new PostconditionError("init()", "item is not on the right place");
			}
			for (int i = 0; i < getDelegate().getEnvironment().getWidth(); i++) {
				for (int j = 0; j < getDelegate().getEnvironment().getHeight(); j++) {
					if (i != it.getCol() || j != it.getHgt()) {
						if (!(!(getDelegate().getEnvironment().getCellContent(i, j).getItem() != null
								&& (getDelegate().getEnvironment().getCellContent(i, j).getItem().getId()) == it
										.getId()))) {
							throw new PostconditionError("init()", "item is not on the right place");
						}
					}
				}
			}
		}
		if (!(getDelegate().getNbLives() == 2)) {
			throw new PostconditionError("init()", "number of lives should be 2");
		}
		if (!(getDelegate().getScore() == 0))
			throw new PostconditionError("init()", "score should be 0");

		if (!(getDelegate().getPlayer().getEngine() == getDelegate())) {
			throw new PostconditionError("init()", "player engine is not this engine");
		}
		for (Vector<Integer> v : getDelegate().guardInitPos()) {
			for (GuardService g : getDelegate().getGuards()) {
				if (g.getId() == v.get(0)) {
					if (!(g.getHgt() == v.get(2) && g.getWdt() == v.get(1)))
						throw new PostconditionError("init()", "guardInitPos should have all init pos of guards");
				}
			}
		}
	}

	// \post: \forall T:Item \in getTreasures()
	// T \in
	// EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
	// \implies T not in getTreasures() \and getScore() == getScore()@pre + 1
	// \post: \forall G:Guard \in getGuards()
	// G \in
	// EnvironmentService::getCellContent(getEnvironment(getPlayer()),getWdt(getPlayer()),getHgt(getPlayer()))
	// \implies getNbLives() == getNbLives()@pre - 1
	// \post: \forall G:Guard \in getGuards()@pre
	// t:Item \in
	// EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G))
	// \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G))
	// == HOL
	// \implies t \in
	// EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)-1)
	// t:Item \in
	// EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G))
	// \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G))
	// != HOL
	// \implies t \in
	// EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G))

	@Override
	public void step() {
		ArrayList<Hole> Holes_atpre = new ArrayList<>();
		for (Hole h : getDelegate().getHoles()) {
			Hole nh = new Hole(h.getX(), h.getY(), h.getTime());
			nh.setId(h.getId());
			Holes_atpre.add(nh);

		}
		EnvironmentImpl getEnvi_atpre = new EnvironmentImpl();
		getEnvi_atpre.clone(getDelegate().getEnvironment());

		ArrayList<Item> Items_atpre = new ArrayList<>();
		for (Item h : getDelegate().getTreasures()) {
			Item nh = new Item(h.getId(), h.getNature(), h.getCol(), h.getHgt());
			Items_atpre.add(nh);

		}
		
		ArrayList<Vector<Integer>> guardInit = new ArrayList<>();
		for(Vector<Integer> v : getDelegate().guardInitPos()) {
			Vector<Integer> add = new Vector<Integer>();
			add.add(v.get(0));
			add.add(v.get(1));
			add.add(v.get(2));
			guardInit.add(add);
		}

		int nbLive_atpre = getDelegate().getNbLives();
		int score_atpre = getDelegate().getScore();
		int hgtPlayer_atpre = getDelegate().getPlayer().getHgt();
		int wdtPlayer_atpre = getDelegate().getPlayer().getWdt();

		ArrayList<GuardService> guards_atpre = new ArrayList<>();
		for (GuardService gs : getDelegate().getGuards()) {
			GuardImpl clone = new GuardImpl();
			clone.clone(gs);
			guards_atpre.add(clone);
		}

		checkInvariant();
		getDelegate().step();
		checkInvariant();

		for (Item i : Items_atpre) {
			if (i.getCol() == getDelegate().getPlayer().getWdt() && i.getHgt() == getDelegate().getPlayer().getHgt()) {
				if (!(getDelegate().getScore() == score_atpre + 1))
					throw new PostconditionError("step()", "score should have been incremented");
				for (Item ni : getDelegate().getTreasures()) {
					if (!(ni.getId() != i.getId())) {
						throw new PostconditionError("step()", "this item should be in the item list anymore");
					}
				}
			}
		}
		for (GuardService g : getDelegate().getGuards()) {
			if (g.getWdt() == getDelegate().getPlayer().getWdt() && g.getHgt() == getDelegate().getPlayer().getHgt()) {
				if (!(getDelegate().getNbLives() == nbLive_atpre - 1))
					throw new PostconditionError("step()", "the player should have lost one life");
			}
		}
		//\post: \forall G:Guard \in getGuards()@pre
		// t:Item \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)) \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G)) == HOL
			//\implies t \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)-1)
		// t:Item \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G)) \and EnvironmentService::getCellNature(getEnvironment(G),getWdt(G),getHgt(G)) != HOL
				//\implies t \in EnvironmentService::getCellContent(getEnvironment(G),getWdt(G),getHgt(G))
		for (Hole H : Holes_atpre) {
			if (H.getTime() < 14) {
				boolean in = false;
				boolean timeIncr = false;
				for (Hole h : getDelegate().getHoles()) {
					if (h.getId() == H.getId()) {
						in = true;
						if (h.getTime() == H.getTime() + 1 && getDelegate().getStatus() == Status.Playing) {
							timeIncr = true;
						}
					}
				}
				if (!(in && timeIncr)) {
					throw new PostconditionError("step()",
							"time of holes should be incremented and if this time is strictly under 15 its must still be getHole ");
				}
			}
			if (H.getTime() == 14) {
				if (getDelegate().getEnvironment().getCellContent(H.getX(), H.getY())
						.isInside(getDelegate().getPlayer())) {
					if (!(getDelegate().getNbLives() == nbLive_atpre - 1)) {
						throw new PostconditionError("step()",
								"the player is on a hole that will be fill so he have to lose a life");
					}
				}
				for (Hole h : getDelegate().getHoles()) {
					if (!(h.getId() != H.getId()))
						throw new PostconditionError("step()", "this hole should have been filled");
				}
				for (GuardService gs : guards_atpre) {
					if (gs.getWdt() == H.getX() && gs.getHgt() == H.getY()) {
						
						if (!(getDelegate().getEnvironment().getCellContent(H.getX(), H.getY()).getCar().isEmpty())) {
							throw new PostconditionError("step",
									"no one should be here now that this hole doesnt exists");
						}
						for (Vector<Integer> v : guardInit) {
							if (v.get(0) == gs.getId()) {
								for (CharacterService cs : getDelegate().getEnvironment()
										.getCellContent(v.get(1), v.get(2)).getCar()) {
									if (cs.getClass() == GuardImpl.class) {
										if (!(((GuardImpl) cs).getHgt() == v.get(2) && ((GuardImpl) cs).getWdt() == v.get(1))) {
											throw new PostconditionError("step",
													"this guard should be at his init pos");
										}
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
	public void setCommand(Move c) {
		checkInvariant();
		getDelegate().setCommand(c);
		checkInvariant();
		if (!(getDelegate().getNextCommand() == c)) {
			throw new PostconditionError("setCommand(" + c + ")", "nextCommande should have been " + c);
		}

	}

	@Override
	public void setNbLives(int l) {
		checkInvariant();
		getDelegate().setNbLives(l);
		checkInvariant();
		if (!(getDelegate().getNbLives() == l)) {
			throw new PostconditionError("setNbLives(" + l + ")", "nbLives should have been " + l);
		}
	}

}
