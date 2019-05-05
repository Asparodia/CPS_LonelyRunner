package lonelyrunner.service;

import lonelyrunner.service.utils.Cell;

public interface EditableScreenService extends  /*refine*/ ScreenService {
	
	// Observators
	
	public boolean isPlayable();
	
	// Operators
	
	// \pre : setNature(x,y,c) req 0<=y<getHeigh() and 0<=x<getWidth()
	// \post : getCellNature(x,y) == c
	// \post : // \forall u:int and v:int with 0<=u<getWidth() and 0<=v<getHeight() 
					// (x != u or y != v) \implies getCellNature(u,v) = getCellNature(u,v)@pre
	public void setNature(int x, int y, Cell c);
	
	// Invariants 
	
	// isPlayable() =min= // \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight() 
								// getCellNature(x,y) != HOL and // \forall u:int with 0<=x<getWidth() getCellNature(x,0) = MTL 
	

}
