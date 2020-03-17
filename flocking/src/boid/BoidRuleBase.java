package boid;

import java.util.ArrayList;

import graphics.Drawable;
import vector.Vector;

public class BoidRuleBase extends BoidRule {


	private Vector acceleration = new Vector(0,0);
	
	public Vector getAceleration() {
		return acceleration;
	}


	public boolean clear() {
		acceleration.setZero();
		return false;
	}


	public Vector seeBoid(Boid me, Boid other) {
		return null;
	}


	public boolean calulate(Boid me) {
		return false;
	}
	public boolean drawingComponents(ArrayList<Drawable> drawings) {
		return false;
	}


	@Override
	public Vector getVector() {
		return acceleration;
	}

}
