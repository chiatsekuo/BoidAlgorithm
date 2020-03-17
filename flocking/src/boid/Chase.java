package boid;

import vector.Vector;
/*
Chase rule

pick one boid in range and fly to it



 */
public class Chase extends BoidRule {

	private Vector chase=new Vector(0,0);
	private boolean foundChase=false;
	private double chase_weight = 1;
	private Class<?> target;
	
	public Chase(BoidRule next,Class<?> c) {
		super(next);
		target = c;
		
	}
	public Chase(BoidRule next,double chase_weight,Class<?> c) {
		this(next,c);
		this.chase_weight = chase_weight;
		target = c;
	}
	
	public Vector getAceleration() {
		Vector total = lower.getAceleration();
		chase.multiply(chase_weight);
		total.add(chase);
		return total;
	}


	public boolean clear() {
		lower.clear();
		chase.setZero();
		foundChase = false;
		return true;
	}


	public Vector seeBoid(Boid me, Boid other) {
		lower.seeBoid(me, other);
		if(other.getClass() == target) {
			if(foundChase) {
				if(utils.Utils.distance(other.getPositionVector(), me.getPositionVector())<utils.Utils.distance(chase,me.getPositionVector())) {
					chase = other.getPositionVector();
				}
			}else {
				chase = other.getPositionVector();
				foundChase=true;
			}
		}
		return null;
	}


	public boolean calulate(Boid me) {
		lower.calulate(me);
		if(foundChase) {
			chase = Vector.subtract(chase, me.getPositionVector());
		}
		return true;
	}
	@Override
	public Vector getVector() {
		return chase;
	}
}
