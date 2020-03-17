package jUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import boid.Alignment;
import boid.Bird;
import boid.Boid;
import boid.BoidRule;
import boid.BoidRuleBase;
import boid.Cohesion;
import boid.Separation;
import boid.Sight;
import flocking.Hawk;
import flocking.Pigeon;
import graphics.DrawingRule;
import graphics.DrawingSight;
import ray.Ray;
import ray.RayDetectable;
import shape.Circle;
import shape.Rectangle;
import utils.Utils;
import vector.Vector;

class BoidJunitTest {

	@BeforeEach
	void setUp() throws Exception {
		Bird.getAllBirds().clear();
		Ray.getRaydetectable().clear();
	}
	
	@Test
	void testAlignment() {
		Vector vela = new Vector(10,10);
		Vector velb = new Vector(-10,-10);
		Vector velc = new Vector(10,0);
		Vector posa = new Vector(10,10);
		Vector posb = new Vector(10,60);
		Vector posc = new Vector(50,40);
		Bird a = new Bird((int)posa.getxComponent(),(int)posa.getyComponent(),vela,new Alignment(new BoidRuleBase()));
		Bird b = new Bird((int)posb.getxComponent(),(int)posb.getyComponent(),velb,new Alignment(new BoidRuleBase()));
		Bird c = new Bird((int)posc.getxComponent(),(int)posc.getyComponent(),velc,new Alignment(new BoidRuleBase()));
		
		a.preBehaviour();
		b.preBehaviour();
		c.preBehaviour();
		
		Boid.sight(a, b);
		Boid.sight(a, c);
		Boid.sight(b, c);
		
		a.behaviour();
		b.behaviour();
		c.behaviour();
		
		
		System.out.println(a.getAcceleration());
		System.out.println(b.getAcceleration());
		System.out.println(c.getAcceleration());
		
		Vector Ga = Vector.add(velb, velc);
		Ga.divide(2);
		Ga.subtract(vela);
		Vector Gb = Vector.add(vela, velc);
		Gb.divide(2);
		Gb.subtract(velb);
		Vector Gc = Vector.add(vela, velb);
		Gc.divide(2);
		Gc.subtract(velc);
		
		assertEquals(true,a.getAcceleration().isEqual(Ga));
		assertEquals(true,b.getAcceleration().isEqual(Gb));
		assertEquals(true,c.getAcceleration().isEqual(Gc));
	}
	@Test
	void testCohesion() {
		Vector vela = new Vector(10,10);
		Vector velb = new Vector(-10,-10);
		Vector velc = new Vector(10,0);
		Vector posa = new Vector(10,10);
		Vector posb = new Vector(10,60);
		Vector posc = new Vector(50,40);
		Bird a = new Bird((int)posa.getxComponent(),(int)posa.getyComponent(),vela,new Cohesion(new BoidRuleBase()));
		Bird b = new Bird((int)posb.getxComponent(),(int)posb.getyComponent(),velb,new Cohesion(new BoidRuleBase()));
		Bird c = new Bird((int)posc.getxComponent(),(int)posc.getyComponent(),velc,new Cohesion(new BoidRuleBase()));
		
		a.preBehaviour();
		b.preBehaviour();
		c.preBehaviour();
		
		Boid.sight(a, b);
		Boid.sight(a, c);
		Boid.sight(b, c);
		
		a.behaviour();
		b.behaviour();
		c.behaviour();
		
		
		System.out.println(a.getAcceleration());
		System.out.println(b.getAcceleration());
		System.out.println(c.getAcceleration());
		
		Vector Ga = Vector.add(posb, posc);
		Ga.divide(2);
		Ga.subtract(posa);
		Vector Gb = Vector.add(posa, posc);
		Gb.divide(2);
		Gb.subtract(posb);
		Vector Gc = Vector.add(posa, posb);
		Gc.divide(2);
		Gc.subtract(posc);
		
		assertEquals(true,a.getAcceleration().isEqual(Ga));
		assertEquals(true,b.getAcceleration().isEqual(Gb));
		assertEquals(true,c.getAcceleration().isEqual(Gc));
	}
	@Test
	void testSeperation() {
		Vector vela = new Vector(10,10);
		Vector velb = new Vector(-10,-10);
		Vector velc = new Vector(10,0);
		Vector posa = new Vector(10,10);
		Vector posb = new Vector(10,60);
		Vector posc = new Vector(50,40);
		Bird a = new Bird((int)posa.getxComponent(),(int)posa.getyComponent(),vela,new Separation(new BoidRuleBase()));
		Bird b = new Bird((int)posb.getxComponent(),(int)posb.getyComponent(),velb,new Separation(new BoidRuleBase()));
		Bird c = new Bird((int)posc.getxComponent(),(int)posc.getyComponent(),velc,new Separation(new BoidRuleBase()));
		
		a.preBehaviour();
		b.preBehaviour();
		c.preBehaviour();
		
		Boid.sight(a, b);
		Boid.sight(a, c);
		Boid.sight(b, c);
		
		a.behaviour();
		b.behaviour();
		c.behaviour();
		
		assertEquals(true,a.getAcceleration().isEqual(new Vector(-22.5,-45.0)));
		assertEquals(true,b.getAcceleration().isEqual(new Vector(-28.1249,42.1875)));
		assertEquals(true,c.getAcceleration().isEqual(new Vector(50.625,2.8125)));
	}

	
	@Test
	void testHawkPigeon() {
		
		Bird a = new Hawk(100,100,new Vector(1,0),true);
		Bird b = new Pigeon(150,100,new Vector(1,0),true);
		
		a.preBehaviour();
		b.preBehaviour();
	
		Boid.sight(a, b);
		
		b.behaviour();
		a.behaviour();
		//all that should be there is the chase acceleration and flee
		
		
		assertEquals(true,a.getAcceleration().isEqual(new Vector(100,0))); // Hawk flys to the differnce in position chase force is double
		assertEquals(true,b.getAcceleration().isEqual(new Vector(50,0))); // pigeon flys to difference in position inversed
	}
	@Test
	void testHawkPigeonOutOfRange() {
		
		Bird a = new Hawk(100,100,new Vector(1,0),false);
		Bird b = new Pigeon(180,100,new Vector(1,0),false);
		
		a.preBehaviour();
		b.preBehaviour();
	
		Boid.sight(a, b);
		
		a.behaviour();
		b.behaviour();
		//all that should be there is the chase acceleration and flee
		
		
		assertEquals(true,a.getAcceleration().isEqual(new Vector(0,0))); // Hawk flys to the differnce in position
		assertEquals(true,a.getAcceleration().isEqual(new Vector(0,0))); // pigeon flys to difference in position inversed
	}
	@Test
	void testDynamicCreation() {
		Bird a = new Bird(100,100,new Vector(0,1),new BoidRuleBase());
		Bird b = new Bird(100,100,new Vector(1,0),new BoidRuleBase());
		
		a.preBehaviour();
		b.preBehaviour();
		
		Boid.sight(a, b);
		
		a.behaviour();
		b.behaviour();
		
		a.movement();
		b.movement();
		
		//shoud have no effect so they just move the minmium distance of two
		
		assertEquals(true,a.getPositionVector().isEqual(new Vector(100,102)));
		assertEquals(true,b.getPositionVector().isEqual(new Vector(102,100)));
		
		a.pushRule(new Cohesion());
		b.pushRule(new Cohesion());
		
		
		a.preBehaviour();
		b.preBehaviour();
		
		Boid.sight(a, b);
		
		a.behaviour();
		b.behaviour();
		
		assertEquals(true,a.getAcceleration().isEqual(new Vector(2,-2)));
		assertEquals(true,b.getAcceleration().isEqual(new Vector(-2,2)));
		
		
		//sample velocities
		Vector vela = new Vector(0,0);
		vela.copy(a.getVelocityVector());
		Vector velb = new Vector(0,0);
		velb.copy(b.getVelocityVector());
		
		//remove rule
		a.popRule();
		b.popRule();
		
		a.preBehaviour();
		b.preBehaviour();
		
		Boid.sight(a, b);
		
		a.behaviour();
		b.behaviour();
		
		assertEquals(true,a.getVelocityVector().isEqual(vela));
		assertEquals(true,b.getVelocityVector().isEqual(velb));
		
	}
	@Test
	void testOverTheTop() {
		Bird a = new Bird(100,2,new Vector(0,-4),new BoidRuleBase());
		a.movement();
		assertEquals(true,a.getPositionVector().isEqual(new Vector(100,Utils.SCREEN_HIEGHT-2)));
		
	}
	@Test
	void testOverTheSide() {
		Bird a = new Bird(2,130,new Vector(-6,0),new BoidRuleBase());
		a.movement();
		assertEquals(true,a.getPositionVector().isEqual(new Vector(Utils.SCREEN_WIDTH-3,130))); // note max speed of 5
	}
	@Test
	void testdiagnol() {
		Bird a = new Bird(Utils.SCREEN_WIDTH-2,Utils.SCREEN_HIEGHT-1,new Vector(4,2),new BoidRuleBase());
		a.movement();
		assertEquals(true,a.getPositionVector().isEqual(new Vector(2,1)));
	}
	@Test
	void testSight() {
		
		class raydetectableStub extends Circle implements RayDetectable {

			public raydetectableStub() {super(new Vector(0,0),0);}
			public double distanceToPoint(Vector point) {
				return 0;
			}

			
		}
		
		Bird a = new Bird(100,100,new Vector(3,0),new DrawingSight(new BoidRuleBase(),10));
		
		Ray.getRaydetectable().add(new raydetectableStub());
		
		a.preBehaviour();
		a.behaviour();
		a.movement();
		
		assertEquals(true,a.getVelocityVector().isEqual(new Vector(3,0)));//no change due to imposibility
	
	}
	boolean first = true;
	@Test
	void testColisionOnChange() {
		
		class RayStub extends Ray{
			public double trace(double limitDistance) {
				if(first) {
					first = false; 
					return -10;
				}else {
					return 10;
				}
				
			}
		}
		Sight r = new Sight(new BoidRuleBase(),10,new RayStub());
		
		Bird a = new Bird(100,100,new Vector(3,0),r);
		
		
		a.preBehaviour();
		a.behaviour();
		a.movement();
		
		assertEquals(true,a.getVelocityVector().isEqual(new Vector(3,0)));//no change due to nothing in the way
	
	}
	@Test
	void testSightSearchpossible() {
		
		Bird a = new Bird(100,100,new Vector(3,0),new DrawingSight(new BoidRuleBase(),100));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(200,100),100,100));
		
		
		Vector velocity= new Vector(0,0);
		
		velocity.copy(a.getVelocityVector());
		
		a.preBehaviour();
		a.behaviour();
		a.movement();
		
		
		
		assertEquals(true,a.getAcceleration().isEqual(new Vector(0,0)));//no change due to imposibility
		assertEquals(false, velocity.isEqual(a.getVelocityVector()));
	}
	@Test
	void testSightSearchImposible() {
		
		Bird a = new Bird(100,100,new Vector(3,0),new DrawingSight(new BoidRuleBase(),10));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(200,100),100,100));
		
		Vector velocity= new Vector(0,0);
		velocity.copy(a.getVelocityVector());
		
		a.preBehaviour();
		a.behaviour();
		a.movement();
		
		assertEquals(true,a.getAcceleration().isEqual(new Vector(0,0)));
		assertEquals(true, velocity.isEqual(a.getVelocityVector()));
		
	}
	static int seeBoidCount = 0;
	@Test
	void testBoidEdeges() {
		
		class BoidStub extends Bird{
			
			
			public BoidStub(int x, int y, Vector vel, BoidRule r) {
				super(x, y, vel, r);
			}
			public void seeBoid(Boid Other) {
				seeBoidCount+=1;
			}
		}
		seeBoidCount = 0;
		
		Bird a = new BoidStub(10,10,new Vector(0,0),new BoidRuleBase());
		Bird b = new BoidStub(Utils.SCREEN_WIDTH-10,Utils.SCREEN_HIEGHT-10,new Vector(0,0),new BoidRuleBase());
		
		Boid.sight(a, b);
		
		assertEquals(seeBoidCount,2);
	}
	@Test
	void testBoidEdeges2() {
		
		class BoidStub extends Bird{
			
			
			public BoidStub(int x, int y, Vector vel, BoidRule r) {
				super(x, y, vel, r);
			}
			public void seeBoid(Boid Other) {
				seeBoidCount+=1;
			}
		}
		seeBoidCount = 0;
		
		Bird a = new BoidStub(Utils.SCREEN_WIDTH-10,10,new Vector(0,0),new BoidRuleBase());
		Bird b = new BoidStub(10,Utils.SCREEN_HIEGHT-10,new Vector(0,0),new BoidRuleBase());
		
		Boid.sight(a, b);
		
		assertEquals(seeBoidCount,2);
	}
	
	@Test
	void testBoidEdeges3() {
		
		class BoidStub extends Bird{
			
			
			public BoidStub(int x, int y, Vector vel, BoidRule r) {
				super(x, y, vel, r);
			}
			public void seeBoid(Boid Other) {
				seeBoidCount+=1;
			}
		}
		seeBoidCount = 0;
		
		Bird a = new BoidStub(Utils.SCREEN_WIDTH-100,100,new Vector(0,0),new BoidRuleBase());
		Bird b = new BoidStub(100,Utils.SCREEN_HIEGHT-100,new Vector(0,0),new BoidRuleBase());
		
		Boid.sight(a, b);
		
		assertEquals(seeBoidCount,0);
	}
	@Test
	void testBoidEdeges4() {
		
		class BoidStub extends Bird{
			
			
			public BoidStub(int x, int y, Vector vel, BoidRule r) {
				super(x, y, vel, r);
			}
			public void seeBoid(Boid Other) {
				seeBoidCount+=1;
			}
		}
		seeBoidCount = 0;
		
		Bird a = new BoidStub(300,20,new Vector(0,0),new BoidRuleBase());
		Bird b = new BoidStub(300,Utils.SCREEN_HIEGHT-20,new Vector(0,0),new BoidRuleBase());
		
		Boid.sight(b, a);
		
		assertEquals(seeBoidCount,2);
	}
	@Test
	void testBoidRules() {
		
		BoidRule a = new BoidRuleBase();
		BoidRule b = new DrawingRule(a, null);
		
		assertEquals(true,b.getVector().isEqual(a.getVector()));
		
	}
	@Test
	void testBoidRemove() {
		Bird a = new Pigeon(0,0,new Vector(0,0),true);
		Bird b = new Pigeon(0,0,new Vector(0,0),false);
		
		assertEquals(Bird.getAllBirds().size(),2);
		
		a.remove();
		b.remove();
		
		assertEquals(Bird.getAllBirds().size(),0);
	}
	@Test
	void testHawkChasePigeon() {
		
		Bird a = new Hawk(100,100,new Vector(1,0),true);
		Bird b = new Pigeon(170,100,new Vector(1,0),false);
		Bird c = new Pigeon(160,100,new Vector(1,0),true);
		Bird d = new Pigeon(150,100,new Vector(1,0),false);
		
		a.preBehaviour();
		b.preBehaviour();
	
		Boid.sight(a, b);
		Boid.sight(a, d);
		Boid.sight(a, c);
		
		
		a.behaviour();
		b.behaviour();
		//all that should be there is the chase acceleration and flee
		
		
		assertEquals(true,a.getAcceleration().isEqual(new Vector(100,0))); // Hawk flys to the differnce in position chase force is double
	}
}
