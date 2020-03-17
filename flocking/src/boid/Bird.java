package boid;

import java.util.ArrayList;

import graphics.Drawable;
import utils.Log;
import vector.Vector;

/*
 * class Bird{
 * 	  variables
 * 		Vector position 	// the boids position
 * 		Vector velocity 	//the boids velocity
 * 		Vector acceleration //the acceleration of the boid
 * 				//all have get
 * 
 * 	ok	Bird(int x,int y,vector vel);	//position x and y and initial velocity
 * 	ok	bool movement();				//update the birds position to the next frame
 * 	ok	seeBoid(boid other);			//how to tell a boid about other boids
 * 										//this triggers all different rules
 * 	ok 	preBehavior();					//set rule calulations to zero and reset counts
 * 	ok	behavior();						//finish all the calculations
 * 										//this finishes the calculation for all the rules active on the bird scales them
 * 										//and add them to the acceleration vector
}
 */

public class Bird implements Boid {
	//
	
	protected static final double MAX_ACCELERATION = .1;
	protected static final double MAX_SPEED = 5;
	protected static final double MIN_SPEED = 2;
	protected static final double SIGHT_RANGE = 75;
	
	protected static Log log = Log.getLog();
	protected static int DEBUG = Log.DEBUG+Log.BOIDS;
	protected static int ERROR = Log.ERROR+Log.BOIDS;
	protected static int EVENT = Log.EVENT+Log.BOIDS;

	
	private static final ArrayList<Bird> ALL_BIRDS = new ArrayList<Bird>();
	protected Vector position;
	protected Vector velocity;
	protected Vector acceleration;
	
	
	
	protected BoidRule rules;
	//rule vectors
	

	public Bird(int x, int y, Vector vel,BoidRule r) {
		rules = r;
		position = new Vector((double)x,(double)y);
		velocity = vel;
		acceleration = new Vector(0,0);
		
		ALL_BIRDS.add(this);
		log.println("new  boid:" + this.toString(),DEBUG);
	}
	public boolean remove() {
		ALL_BIRDS.remove(this);
		log.println("dead boid:" + this.toString(),DEBUG);
		return false;	
	}
	public BoidRule popRule() {
		BoidRule r = rules;
		rules = rules.getLower();
		return r;
	}
	public void pushRule(BoidRule r) {
		r.setLower(rules);
		rules = r;
	}
	public boolean movement() {
		//needed limit max acceleration
		this.acceleration.limit(0,getMAX_ACCELERATION());
		//update the velocity with the acceleration
		this.velocity.add(this.acceleration);
		//needed limit max speed
		this.velocity.limit(getMIN_SPEED(), getMAX_SPEED());
		//update the position with the velocity
		this.position.add(this.velocity);
		//needed rollover world edge
		if(this.position.getxComponent()<0) {
			this.position.setxComponent(this.position.getxComponent()+utils.Utils.SCREEN_WIDTH);
		}else if(this.position.getxComponent()>utils.Utils.SCREEN_WIDTH) {
			this.position.setxComponent(this.position.getxComponent()-utils.Utils.SCREEN_WIDTH);
		}
		if(this.position.getyComponent()<0) {
			this.position.setyComponent(this.position.getyComponent()+utils.Utils.SCREEN_HIEGHT);
		}else if(this.position.getyComponent()>utils.Utils.SCREEN_HIEGHT) {
			this.position.setyComponent(this.position.getyComponent()-utils.Utils.SCREEN_HIEGHT);
		}
		
		//update the image
		
		return true;
	}

	public void seeBoid(Boid other) {
		
		rules.seeBoid(this, other);
		
	}
	
	public boolean preBehaviour() {
		//reset all vectors and counts for next frame
		rules.clear();
		return false;
	}
	public boolean behaviour() {
		//zero the acceleration
		this.acceleration.setComponents(0, 0);
		rules.calulate(this);
		acceleration = rules.getAceleration();

		
		return false;
	}

	@Override
	public Vector getVelocityVector() {
		return this.velocity;
	}

	@Override
	public Vector getPositionVector() {
		return this.position;
	}

	public Vector getAcceleration() {
		return this.acceleration;
	}

	@Override
	public String toString() {
		return "Bird [position=" + position + ", velocity=" + velocity + "]";
	}

	@Override
	public double sightRange() {
		return getSIGHT_RANGE();
	}
	
	public static ArrayList<Bird> getAllBirds() {
		return ALL_BIRDS;
	}

	protected double getMAX_ACCELERATION() {
		return MAX_ACCELERATION;
	}

	protected double getMAX_SPEED() {
		return MAX_SPEED;
	}

	protected double getMIN_SPEED() {
		return MIN_SPEED;
	}

	protected double getSIGHT_RANGE() {
		return SIGHT_RANGE;
	}

	
	
	


}
