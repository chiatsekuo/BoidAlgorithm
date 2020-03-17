package boid;

import java.util.ArrayList;

import graphics.Drawable;
import vector.Vector;
/*

class BoidRule{

	BoidRule(BoidRule a) // there is a next rule so add to linked list
	BoidRule()
	getAceleration()	// sum all the rules
	clear()				// clear all the rules 
	seeBoid(Boid me,Boid other)//get the two boids
	calulate(Boid me)			// avg the vectors before the aceleration
	drawingComponents(List drawings)	//for extracting componetes
	getVector()					//get the rule vector

}

 */
public abstract class BoidRule {
	
	public BoidRule lower;
	public BoidRule(BoidRule a) {
		lower = a;
	}
	public BoidRule(){
		lower = null;
	}
	
	public abstract Vector getAceleration();
	public abstract boolean clear();
	public abstract Vector seeBoid(Boid me,Boid other);
	public abstract boolean calulate(Boid me);
	public boolean drawingComponents(ArrayList<Drawable> drawings) {
		lower.drawingComponents(drawings);
		return false;
	}
	public Vector getVector() {
		return lower.getVector();
	}
	public  BoidRule getLower() {
		return lower;
	}
	public void setLower(BoidRule a) {
		lower = a;
	}
	
	
}
