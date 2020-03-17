package jUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ray.Ray;
import ray.RayDetectable;
import shape.Circle;
import shape.Rectangle;
import vector.Vector;

class RayTest {

	protected Ray Test;
	@BeforeEach
	void setUp() throws Exception {
		Test = new Ray();
		Ray.getRaydetectable().clear();
	}
	
	
	@Test
	void testHit() {
		class detectStub implements RayDetectable{
			private int calls = 0;
			public double distanceToPoint(Vector point) {
				calls+=1;
				if(calls == 3) {
					return 0;
				}else {
					return 10;
				}
			}
			public double distanceToPointCircle(Vector point) {return 0;}
			public ArrayList<Vector> getPoints(Vector projection) {return null;}
			public Vector getCenter() {return null;}
			
		}
		Ray.getRaydetectable().add(new detectStub());
		Test.setStartPoint(new Vector(0,0));
		Test.setDirection(new Vector(1,1));
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,20);
	}
	
	@Test
	void testMiss() {
		class detectStub implements RayDetectable{
			private int calls = 0;
			public double distanceToPoint(Vector point) {
				calls+=1;
				if(calls == 22) {
					return 0;
				}else {
					return 10;
				}
			}
			public double distanceToPointCircle(Vector point) {return 0;}
			public ArrayList<Vector> getPoints(Vector projection) {return null;}
			public Vector getCenter() {return null;}
			
		}
		Ray.getRaydetectable().add(new detectStub());
		Test.setStartPoint(new Vector(0,0));
		Test.setDirection(new Vector(1,1));
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}

	@Test
	void testflatHit() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,0));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(200,50),100,100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		System.out.println(Test.toString());
		assertEquals(distance,100);
	}
	@Test
	void testflatMiss() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,0));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(200,200),100,100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}
	@Test
	void testflatRange() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,0));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(350,50),100,100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}
	
	
	@Test
	void testAngleHit() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(200,200),100,100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,Math.sqrt(100*100*2));
	}
	@Test
	void testAngleMiss() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(0,150),100,100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}
	@Test
	void testAngleRange() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(300,300),100,100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}
	
	@Test
	void testCircleHit() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Circle(new Vector(200,200),100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,Math.sqrt(150*150*2)-100);
	}
	@Test
	void testCircleMiss() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Circle(new Vector(0,200),100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}
	@Test
	void testCircleRange() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Circle(new Vector(400,400),100));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}
	
	@Test
	void manyOjectrange() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Circle(new Vector(400,400),100));
		Ray.getRaydetectable().add(new Circle(new Vector(100,200),50));
		Ray.getRaydetectable().add(new Circle(new Vector(200,100),50));
		
		double distance = Test.trace(200);
		System.out.println(distance);
		assertEquals(distance,-1);
	}
	@Test
	void manyOjecthit() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Circle(new Vector(400,400),100));
		Ray.getRaydetectable().add(new Circle(new Vector(100,200),50));
		Ray.getRaydetectable().add(new Circle(new Vector(200,100),50));
		
		double distance = Test.trace(400);
		System.out.println(distance);
		assertEquals(distance,Math.sqrt(350*350*2)-100);
	}
	
	@Test
	void searchTest1() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,0));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(200,50),100,100));
		
		Test.search(200);
		
		Vector option1 = Vector.subtract(new Vector(200-50,50-50),new Vector(50,50));
		Vector option2 = Vector.subtract(new Vector(200-50,50+50),new Vector(50,50));
		Vector answer = Test.getDirection();
		option1.normalize();
		option2.normalize();
		answer.normalize();
		System.out.println(option1.getAngle());
		System.out.println(option2.getAngle());
		System.out.println(answer.getAngle());
		
		boolean statement = (answer.getAngle()<option1.getAngle())||(answer.getAngle()>option2.getAngle());
		assertEquals(true,statement);
	}
	
	@Test
	void searchTest2() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Rectangle(new Vector(200,200),100,100));
		
		Test.search(200);
		
		Vector option1 = Vector.subtract(new Vector(200-50,50+50),Test.getStartPoint());
		Vector option2 = Vector.subtract(new Vector(200+50,50+50),Test.getStartPoint());
		Vector answer = Test.getDirection();
		option1.normalize();
		option2.normalize();
		answer.normalize();
		System.out.println(option1.getAngle());
		System.out.println(option2.getAngle());
		System.out.println(answer.getAngle());
		
		boolean statement = (answer.getAngle()<option1.getAngle())||(answer.getAngle()>option2.getAngle());
		assertEquals(true,statement);		
	}
	
//	@Test
//	void searchTest3() {
//		Test.setStartPoint(new Vector(50,50));
//		Test.setDirection(new Vector(1,0));
//		
//		Ray.getRaydetectable().add(new Rectangle(new Vector(200,50),100,100));
//		
//		Test.search(200);
//		
//		Vector option2 = Vector.subtract(new Vector(200-50,50-50),new Vector(50,50));
//		Vector option1 = Vector.subtract(new Vector(200-50,50+50),new Vector(50,50));
//		Vector answer = Test.getDirection();
//		option1.normalize();
//		option2.normalize();
//		answer.normalize();
//		System.out.println(option1.getAngle());
//		System.out.println(option2.getAngle());
//		System.out.println(answer.getAngle());
//		
//		boolean statement = (answer.getAngle()<option1.getAngle())||(answer.getAngle()>option2.getAngle());
//		assertEquals(true,statement);
//	}
//	
//	@Test
//	void searchTest4() {
//		Test.setStartPoint(new Vector(50,50));
//		Test.setDirection(new Vector(1,1));
//		
//		Ray.getRaydetectable().add(new Rectangle(new Vector(200,200),100,100));
//		
//		Test.search(200);
//		
//		Vector option1 = Vector.subtract(new Vector(200-50,50+50),Test.getStartPoint());
//		Vector option2 = Vector.subtract(new Vector(200+50,50+50),Test.getStartPoint());
//		Vector answer = Test.getDirection();
//		option1.normalize();
//		option2.normalize();
//		answer.normalize();
//		System.out.println(option1.getAngle());
//		System.out.println(option2.getAngle());
//		System.out.println(answer.getAngle());
//		
//		boolean statement = (answer.getAngle()<option1.getAngle())||(answer.getAngle()>option2.getAngle());
//		assertEquals(true,statement);		
//	}
	
	@Test
	void insideCircle() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Circle(new Vector(50,50),100));
		double distance = Test.trace(400);
		System.out.println(distance);
		assertEquals(distance,0);
	}
	@Test
	void insideCircleSearch() {
		Test.setStartPoint(new Vector(50,50));
		Test.setDirection(new Vector(1,1));
		
		Ray.getRaydetectable().add(new Circle(new Vector(50,50),100));
		boolean possible = Test.search(400);
		System.out.println(possible);
		assertEquals(possible,false);
	}
	
	
}
