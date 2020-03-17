package boid;

import vector.Vector;
/*

Cohesion rule
fly to avg position

sum position of boids 

divide by count

fly to the center


 */

public class Cohesion extends BoidRule {

	private Vector cohesion=new Vector(0,0);
	private int cohesionCount=0;
	private double cohesion_weight=1;

	public Cohesion() {
		super();
	}
	
	public Cohesion(BoidRule next) {
		super(next);
	}


	public Cohesion(BoidRule next, double cohesion_weight) {
		super(next);
		this.cohesion_weight = cohesion_weight;
	}


	public Vector getAceleration() {
		Vector total = lower.getAceleration();
		cohesion.multiply(cohesion_weight);
		total.add(cohesion);
		return total;
	}


	public boolean clear() {
		lower.clear();
		cohesion.setZero();
		cohesionCount=0;
		return true;
	}


	public Vector seeBoid(Boid me, Boid other) {
		lower.seeBoid(me, other);
		if(me.getClass() == other.getClass()) {
			cohesion.add(other.getPositionVector());
			cohesionCount+=1;
		}
		return null;
	}


	public boolean calulate(Boid me) {
		lower.calulate(me);
		if(cohesionCount>0) {//divide to get the avg
			cohesion.divide(cohesionCount);
			//create vector from my velocity to the target
			cohesion = Vector.subtract(cohesion,me.getPositionVector());
		}
		return true;
	}

	@Override
	public Vector getVector() {
		return cohesion;
	}
}
