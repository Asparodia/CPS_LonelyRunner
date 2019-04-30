package lonelyrunner.service;

import lonelyrunner.service.utils.Cell;

public interface ScreenService {
	
	// Observators
	
	public int getHeight();
	public int getWidth();
	
	//\pre : getCellNature(i,j) requires 0 <= j < Height(S) and 0 <= i < Width(S)
	public Cell getCellNature(int i, int j);
	
	// Constructors
	
	//\pre: init(h,w) requires 0 < h and 0 < w
	//\post : getHeight() == h
	//\post : getWidth() == w
	//\post : \forall x:int \and y:int with 0<= x < getWidth and 0 <= y < getHeight()  getCellNature(x,y) =EMP 
	public void init(int h, int w);
	
	
	// Operators
	
	//\pre : dig(S,u,v) requires getCellNature(S,u,v) = PLT
	//\post :getCellNature(u,v) = HOL
	//\post : \forall x:int and y:int with 0<=x<getWidth() and 0<=y<getHeight()  (x!=u or y!=v) \implies getCellNature()@pre = getCellNature() 
	public void dig(int u,int v);
	
	//\pre : fill(S,x,y) requires getCellNature(S,x,y) = HOL
	//\post :getCellNature(x,y) = PLT
	//\post : \forall u:int and v:int with 0<=u<getWidth() and 0<=v<getHeight()  (x!=u or y!=v) \implies getCellNature()@pre = getCellNature() 	
	public void fill(int x, int y);
	
	
	// Invariants
	//Pas d'invariant
	
}
