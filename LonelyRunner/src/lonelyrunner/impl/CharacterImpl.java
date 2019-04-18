package lonelyrunner.impl;

import lonelyrunner.service.CharacterService;
import lonelyrunner.service.EnvironmentService;
import lonelyrunner.service.ScreenService;

public class CharacterImpl implements CharacterService{
	
	protected EnvironmentService env;
	private int height;
	private int width;
	
	
	@Override
	public void init(ScreenService s, int x, int y) {
		width = x;
		height = y;
		env = (EnvironmentService) s;
		
	}
	
	@Override
	public EnvironmentService getEnvi() {
		return env;
	}

	@Override
	public int getHgt() {
		return height;
	}

	@Override
	public int getWdt() {
		return width;
	}

	

	@Override
	public void goLeft() {
		//\post: getHgt() == getHgt()@pre
		//\post: getWdt()@pre == 0 \implies getWdt() == getWdt()@pre 
		//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \in {MTL,PLT,HDR} \implies
			//getWdt()@pre == getWdt()
		//\post: EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \not in {LAD,HDR} 
			//\and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre - 1) \not in {PLT,MTL,LAD}
			//\and not exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1)
			//\implies getWdt() == getWdt()@pre
		//\post: \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \implies getWdt() == getWdt()@pre
		//\post: getWdt() != 0 \and EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre-1,getHgt()@pre) \not in {MTL,PLT}
			//\and ( EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre) \in {LAD,HDR}
				//\or EnvironmentService::getCellNature(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) in {PLT,MTL,LAD}
				//\or \exist Character c in EnvironmentService::getCellContent(getEnvi()@pre,getWdt()@pre,getHgt()@pre-1) )
			//\and \not (\exist Character c in EnvironmentService::getCellContent(getEnvi(@pre,getWdt()@pre-1,getHgt()@pre)) ) \implies getWdt() == getWdt()@pre - 1
		
	}

	@Override
	public void goRight() {
		
	}

	@Override
	public void goUp() {
		
	}

	@Override
	public void goDown() {
		
	}

}
