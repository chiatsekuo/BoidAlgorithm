package boid;

import vector.Vector;
/*

Seperation

sum vecotrs pointed from them to me
scale to invers of length

sum and avg

 */
public class Separation extends BoidRule {
	private Vector separation=new Vector(0,0);
	private int separationCount=0;
	private double speration_weight = 1;
	public Separation(BoidRule next) {
		super(next);
	}
	public Separation(BoidRule next,double speration_weight) {
		super(next);
		this.speration_weight=speration_weight;
	}


	public Vector getAceleration() {
		Vector total = lower.getAceleration();
		separation.multiply(speration_weight);
		total.add(separation);
		return total;
	}


	public boolean clear() {
		lower.clear();
		separation.setZero();
		separationCount=0;
		return true;
	}


	public Vector seeBoid(Boid me, Boid other) {
		lower.seeBoid(me, other);
		//if(me.getClass()==other.getClass()) {
			Vector force = Vector.subtract(me.getPositionVector(),other.getPositionVector());
			double length = force.getLength();
			force.scale(.5 * me.sightRange() * me.sightRange());
			//weaken vector based on distance
			force.divide(Boid.distance(me,other));
			//sum the vectors
			this.separation.add(force);
			this.separationCount+=1;
		//}
		return null;
	}


	public boolean calulate(Boid me) {
		
		lower.calulate(me);
		if(separationCount>0) {
			//avg the vector
			separation.divide(separationCount);
		}
		return true;
	}
	@Override
	public Vector getVector() {
		return separation;
	}
}
