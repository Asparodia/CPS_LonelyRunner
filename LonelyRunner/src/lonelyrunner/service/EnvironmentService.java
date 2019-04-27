package lonelyrunner.service;


import lonelyrunner.service.utils.SetCharItem;

public interface EnvironmentService extends /*refine*/ScreenService {

    //Observators
	//\pre: getCellContent(x,y) req 0<=y<getHeight() and 0<=x<getWidth()
	public SetCharItem getCellContent(int x, int y);
	
	// Constructors
	//\post: \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight()
				// getCellNature(x,y) == EditableScreenService::getCellNature(x,y)
	public void init(EditableScreenService ess);
	
	
	//Invariants

	// \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() 
			// getCellNature(x,y) in [MTL,PLR] \implies getCellContent(x,y) == Null
	// \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() 
			// t:Treasure \in getCellContent(x,y)  \implies getCellNature(x,y) == EMP \and getCellNature(x,y-1) \in [PLT,MTL]
	
}	

// \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() 
//\forall c1:CharacterService and c2:CharacterService in [getCellContent(x,y),getCellContent(x,y)] c1 = c2