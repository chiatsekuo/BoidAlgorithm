package boid;

import ray.Ray;
import utils.Log;
import vector.Vector;
/*


set velociy if in dange of colision

 */


public class Sight extends BoidRule {
	private static Log log = Log.getLog();
	private static int DEBUG = Log.DEBUG+Log.BOIDS;
	private Ray Sight = new Ray();
	private int trace_distance=100;
	private Vector EndingAcceleration = new Vector(0,0);
	
	public Sight(BoidRule next){
		super(next);
	}
	public Sight(BoidRule next,Ray sig){
		this(next);
		this.Sight = sig;
	}
	public Sight(BoidRule next, int trace_distance){
		this(next);
		this.trace_distance=trace_distance;
	}
	public Sight(BoidRule next, int trace_distance,Ray sig){
		this(next,trace_distance);
		this.Sight = sig;
	}
	public Ray getRay() {
		return Sight;
	}
	public Vector getAceleration() {
		return EndingAcceleration;
	}


	public boolean clear() {
		lower.clear();
		return false;
	}


	public Vector seeBoid(Boid me, Boid other) {
		lower.seeBoid(me, other);
		return null;
	}
	public boolean calulate(Boid me) {
		lower.calulate(me);
		Vector acceleration = lower.getAceleration();
		
		Vector pos = new Vector(0,0);
		pos.copy(me.getPositionVector());
		Sight.setStartPoint(pos);
		Vector dir = new Vector(0,0);
		dir.copy(me.getVelocityVector());
		Sight.setDirection(dir);
		
		double distance = Sight.trace(trace_distance);
		
		if(distance < 0 ) {//if there is nothing in the way 
			Vector testVelocity = Vector.add(me.getVelocityVector(), acceleration);
			pos.copy(me.getPositionVector());
			Sight.setStartPoint(pos);
			dir.copy(testVelocity);
			Sight.setDirection(dir);
			distance = Sight.trace(trace_distance);
			if(distance < 0 ) {
				//no danger
				EndingAcceleration = acceleration;
			}else {
				//changing puts me in danger
				EndingAcceleration.setComponents(0, 0);
			} 
			
		}else {
			//zero the acceleration
			EndingAcceleration.setComponents(0, 0);
			boolean s = Sight.search(trace_distance);
			if(s) {
				Vector avoidDirection = Sight.getDirection();
				
				//foced based
				if(false) {
					avoidDirection.scale(me.getVelocityVector().getLength());
					Vector trunFoce = Vector.subtract(avoidDirection, me.getVelocityVector());
					trunFoce.divide(distance/ me.getVelocityVector().getLength());
					 me.getVelocityVector().add(trunFoce);
				}else {
					//angle based
					 me.getVelocityVector().setAngle(avoidDirection.getAngle());
				}
				
				//this.acceleration.add(trunFoce);
				log.println("Boid Avoided Object",this.DEBUG);
			}
			return false;
		}
		return false;
	}
}
