package lonelyrunner.service;


import lonelyrunner.service.utils.SetCharItem;

public interface EnvironmentService extends ScreenService {

    //Observators
	//\pre: getCellContent(x,y) req 0<=y<getHeight() and 0<=x<getWidth()
	public SetCharItem getCellContent(int x, int y);
	
	// Constructors
	//\post: \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight()
				// getCellNature(x,y) == EditableScreenService::getCellNature(x,y)
	public void init(EditableScreenService ess);
	
	
	//Invariants
	// \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() 
			//\forall c1:Character and c2:Character in [getCellContent(x,y),getCellContent(x,y)] c1 = c2
	// \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() 
			// getCellNature(x,y) in [MTL,PLR] \implies getCellContent(x,y) == Null
	// \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() 
			// getCellContent(x,y) == Treasure \implies getCellNature(x,y) == EMP \and getCellNature(x,y-1) \in [PLT,MTL]

}	
