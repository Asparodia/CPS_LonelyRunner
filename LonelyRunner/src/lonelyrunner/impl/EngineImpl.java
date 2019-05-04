package lonelyrunner.impl;

import java.util.ArrayList;
import java.util.Vector;

import lonelyrunner.contract.EnvironmentContract;
import lonelyrunner.contract.GuardContract;
import lonelyrunner.contract.PlayerContract;
import lonelyrunner.service.EditableScreenService;
import lonelyrunner.service.EngineService;
import lonelyrunner.service.GuardService;
import lonelyrunner.service.utils.Cell;
import lonelyrunner.service.utils.Couple;
import lonelyrunner.service.utils.Hole;
import lonelyrunner.service.utils.Item;
import lonelyrunner.service.utils.ItemType;
import lonelyrunner.service.utils.Move;
import lonelyrunner.service.utils.Status;

public class EngineImpl implements EngineService {
	
	
	ArrayList<Vector<Integer>> guardInit = new ArrayList<Vector<Integer>>();
	
	
	EnvironmentContract environment;
	PlayerContract player;
	ArrayList<GuardService> guards = new ArrayList<>();
	ArrayList<Item> treasures = new ArrayList<>();
	Status status;
	Move command = Move.NEUTRAL ;
	int lives = 2; 
	int score = 0;
	ArrayList<Hole> holes = new ArrayList<>();

	@Override
	public void init(EditableScreenService es, Couple<Integer, Integer> posChar,
			ArrayList<Couple<Integer, Integer>> posGuards, ArrayList<Couple<Integer, Integer>> posItems) {
		
		status = Status.Playing;
		
		EnvironmentImpl envi = new EnvironmentImpl();
		environment = new EnvironmentContract(envi);
		environment.init(es);
		
		PlayerImpl p = new PlayerImpl();
		player = new PlayerContract(p);
		player.init(environment, posChar.getElem1(), posChar.getElem2(),this);
		
		environment.getCellContent(posChar.getElem1(), posChar.getElem2()).addCar(player.getDelegate());
		
		for(Couple<Integer,Integer> c : posGuards) {
			GuardImpl g = new GuardImpl();
			GuardContract gc = new GuardContract(g);
			gc.init(environment,c.getElem1(),c.getElem2(),player);
			environment.getCellContent(c.getElem1(), c.getElem2()).addCar(gc.getDelegate());
			this.guards.add(gc);
			Vector <Integer> vec = new Vector<>();
			vec.add(gc.getId());
			vec.add(c.getElem1());
			vec.add(c.getElem2());
			guardInit.add(vec);
			
		}
		int id = 0;
		for(Couple<Integer,Integer> c : posItems) {
			Item i = new Item(id, ItemType.Treasure,c.getElem1(),c.getElem2());
			id++;
			environment.getCellContent(c.getElem1(), c.getElem2()).setItem(i);
			this.treasures.add(i);
		}
		
		
		
		
	}
	
	@Override
	public EnvironmentContract getEnvironment() {
		return environment;
	}

	@Override
	public PlayerContract getPlayer() {
		return player;
	}

	@Override
	public ArrayList<GuardService> getGuards() {
		return new ArrayList<GuardService>(guards);
	}

	@Override
	public ArrayList<Item> getTreasures() {
		ArrayList<Item> res = new ArrayList<>();
		for (Item i : treasures) {
			if(i.getNature() == ItemType.Treasure) {
				res.add(i);
			}
		}
		return res;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Move getNextCommand() {
		return command;
	}

	@Override
	public ArrayList<Hole> getHoles() {
		return holes;
	}

	public void setCommand(Move c) {
		this.command = c;
	}
	

	@Override
	public void step() {
		player.getWdt();
		player.getHgt();
		
		
		player.step();
		
		ArrayList<Item> torem = new ArrayList<>();
		for (Item i : treasures) {
			if(i.getNature() == ItemType.Treasure) {
				if(i.getCol() == player.getWdt() && i.getHgt() == player.getHgt()) {
					environment.getCellContent(i.getCol(), i.getHgt()).removeItem();
					torem.add(i);
					score++;
				}
			}
		}
		treasures.removeAll(torem);

		for (GuardService g : guards) {
//			if(environment.getCellContent(g.getWdt(), g.getHgt()).getItem() != null) {
//				Item i = environment.getCellContent(g.getWdt(), g.getHgt()).getItem();
//				environment.getCellContent(g.getWdt(), g.getHgt()).removeItem();
//				g.step();
//				environment.getCellContent(g.getWdt(), g.getHgt()).setItem(i);
//				for(Item it : treasures) {
//					if(it.getId() == i.getId() ) {
//						if(g.getEnvi().getCellNature(g.getWdt(), g.getHgt()) != Cell.HOL) {
////							i.setCol(g.getWdt());
////							i.setHgt(g.getHgt());
//							it.setCol(g.getWdt());
//							it.setHgt(g.getHgt());
//						}
//						else {
//							// relache l'item si il tombe dans un trou
//							environment.getCellContent(it.getCol(), it.getHgt()).removeItem();
////							i.setCol(g.getWdt());
////							i.setHgt(g.getHgt()+1);
//							it.setCol(g.getWdt());
//							it.setHgt(g.getHgt()+1);
//							environment.getCellContent(it.getCol(), it.getHgt()).setItem(it);
//						}
//					}
//				}
//				
//			}
//			else {
				g.step();
//			}
			
		}
		
		
		
		for (GuardService g : guards) {
			if(g.getWdt() == player.getWdt() && g.getHgt() == player.getHgt()) {
				if(lives == 0 ) {
					status = Status.Loss;
				}
				lives--;
				return;
			}
		}
		
		ArrayList<Hole> holeToRem = new ArrayList<>();
		for (Hole g : holes) {
			int time = g.getTime();
			g.setTime(time+1);
			if(g.getTime() == 10) {
				holeToRem.add(g);
				if(g.getX() == player.getWdt() && g.getY() == player.getHgt()) {
					if(lives == 0 ) {
						status = Status.Loss;
					}
					lives--;
					
					return;
				}
				// getCar la est chelou
				if(!environment.getCellContent(g.getX(), g.getY()).getCar().isEmpty()) {
					int nbEntity = environment.getCellContent(g.getX(), g.getY()).getCar().size();
					if(nbEntity == 1 ) {
							GuardService id = ((GuardService)environment.getCellContent(g.getX(), g.getY()).getCar().get(0));
							
							int oldId = id.getId();
							int initX = 0;
							int initY = 0;
							
							for(Vector<Integer> v : guardInit) {
								if(oldId == v.get(0)) {
									initX = v.get(1);
									initY = v.get(2);
								}
							}
							environment.getCellContent(g.getX(), g.getY()).removeCharacter(environment.getCellContent(g.getX(), g.getY()).getCar().get(0));
							environment.fill(g.getX(), g.getY());
							
							GuardImpl newg = new GuardImpl();
							GuardContract gc = new GuardContract(newg);
							
							
							gc.init(environment,initX,initY ,player);
							guards.add(gc);	
							
							for(Vector<Integer> v : guardInit) {
								if(oldId == v.get(0)) {
									v.set(0,gc.getId());
									v.set(1,initX);
									v.set(2,initY);
								}
							}
							for(GuardService gg : guards) {
								if(gg.getId()==oldId) {
									guards.remove(gg);
									break;
								}
							}
							continue;
						}
					if(nbEntity == 2) {
						//ptete faire plus de test ici
						if(lives == 0 ) {
							status = Status.Loss;
						}
						lives--;
						return;
					}
					else {
						System.err.println("ici t'es dans l'implem de engine ya un pb 3 perso dans le meme hol");
					}
				}
				environment.fill(g.getX(), g.getY());
				
			}
		}
		holes.removeAll(holeToRem);
		
		boolean end = true;
		for (Item i : treasures) {
			if(i.getNature() == ItemType.Treasure) {
				end = false;
			}
		}
		if(end) {
			status = Status.Win;
			return;
		}
		System.out.println(status);
		
	}

	@Override
	public int getNbLives() {
		return lives;
	}
	
	public void setNbLives(int a) {
		lives = a;
	}

	@Override
	public int getScore() {
		return score;
	}
	

}
