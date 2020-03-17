package boid;

import vector.Vector;
/*
Alignment rule
turn to the avg direction of near by boids

sum velocitys

divide by count



 */



public class Alignment extends BoidRule {
	
	private Vector alignment=new Vector(0,0);
	private int alignmentCount=0;
	private double aligment_weight = 1;
	
	public Alignment(BoidRule end) {
		super(end);
	}
	public Alignment(BoidRule end, double aligment_weight) {
		super(end);
		this.aligment_weight = aligment_weight;
	}


	public Vector getAceleration() {
		Vector total = lower.getAceleration();
		alignment.multiply(aligment_weight);
		total.add(alignment);
		return total;
	}


	public boolean clear() {
		lower.clear();
		alignment.setZero();
		alignmentCount=0;
		return true;
	}


	public Vector seeBoid(Boid me, Boid other) {
		lower.seeBoid(me, other);
		if(me.getClass()==other.getClass()) {
			
			alignment.add(other.getVelocityVector());
			alignmentCount+=1;
		}
		return null;
	}


	public boolean calulate(Boid me) {
		lower.calulate(me);
		if(alignmentCount>0) {//divide to get the avg
			alignment.divide(alignmentCount);
			//create vector from my velocity to the target
			alignment = Vector.subtract(alignment,me.getVelocityVector());
		}
		return true;
	}
	@Override
	public Vector getVector() {
		return alignment;
	}

}
