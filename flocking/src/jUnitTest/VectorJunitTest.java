package jUnitTest;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import vector.Vector;

class VectorJunitTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "TestCreation.csv")
	public void TestCreation(String points, double px1, double py1, double px2, double py2, String polor, double radians, double length, String cartesian, double cx1, double cy1, String direction) {
		Vector firstV = new Vector(px1, py1, px2, py2);
		Vector secondV = new Vector(radians, length, true);
		Vector thirdV = new Vector(cx1, cy1);
		String msg = direction;
		assertEquals(true, firstV.isEqual(secondV), msg);
		assertEquals(true, secondV.isEqual(thirdV), msg);
	}
	
	@ParameterizedTest
	@CsvFileSource(resources = "TestFunctions.csv")
	public void TestFunctions(String vectorInit, double xc, double yc, String function, String param1, String param2, String returnIfAny, String vectorResult, double rxc, double ryc, String msg) {
		Vector firstV = new Vector(xc, yc);
		Vector resultV = new Vector(rxc, ryc);
		double p1, p2;
		if(function.equals("add")) {
			p1 = Double.parseDouble(param1);
			p2 = Double.parseDouble(param2);
			Vector secondV = new Vector(p1, p2);
			boolean addVector = firstV.add(secondV);
			//assertEquals(String.valueOf(returnIfAny), addVector, msg);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("subtract")){
			p1 = Double.parseDouble(param1);
			p2 = Double.parseDouble(param2);
			Vector secondV = new Vector(p1, p2);
			boolean subtractVector = firstV.subtract(secondV);
			//assertEquals(returnIfAny, subtractVector, msg);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if (function.equals("limit")) {
			p1 = Double.parseDouble(param1);
			p2 = Double.parseDouble(param2);
			boolean limitReturnVal = firstV.limit(p1, p2);
			//assertEquals(returnIfAny, limitReturnVal, msg);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("scale")) {
			p1 = Double.parseDouble(param1);
			firstV.scale(p1);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if (function.equals("rotate")) {
			p1 = Double.parseDouble(param1);
			firstV.rotate(p1);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("divide")) {
			p1 = Double.parseDouble(param1);
			firstV.divide(p1);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("multiply")) {
			p1 = Double.parseDouble(param1);
			firstV.multiply(p1);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("setLength")) {
			p1 = Double.parseDouble(param1);
			firstV.setLength(p1);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("setAngle")) {
			p1 = Double.parseDouble(param1);
			System.out.println(firstV);
			firstV.setAngle(p1);
			System.out.println("*"+firstV);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("getLength")) {
			double returnLength = firstV.getLength();
			returnLength = utils.Utils.round(returnLength);
			double result = Double.parseDouble(returnIfAny);
			assertEquals(result, returnLength, msg);
		}else if(function.equals("getAngle")) {
			double returnAngle = firstV.getAngle();
			double result = Double.parseDouble(returnIfAny);
			assertEquals(result, utils.Utils.round(returnAngle), msg);
		}else if(function.equals("setComponents")) {
			p1 = Double.parseDouble(param1);
			p2 = Double.parseDouble(param2);
			firstV.setComponents(p1, p2);
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("invert")) {
			firstV.invert();
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("normalize")) {
			firstV.normalize();
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("setZero")) {
			firstV.setZero();
			assertEquals(true, firstV.isEqual(resultV), msg);
		}else if(function.equals("perpendicular")) {
			Vector expt = firstV.perpendicular();
			assertEquals(true, expt.isEqual(resultV), msg);
		}else if(function.equals("dotProduct")) {
			p1 = Double.parseDouble(param1);
			p2 = Double.parseDouble(param2);
			double expt = Double.parseDouble(returnIfAny);
			Vector param = new Vector(p1, p2);
			double result = firstV.dotProduct(param);
			assertEquals(result, expt, 0);
		}
	}
	
	@Test
	public void divideByZero() {
		Vector Test = new Vector(7,6);
		Vector Testr = new Vector(10,10);
		Test.divide(0);
		Test.add(new Vector(10,10));
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void zeroVectorMath() {
		Vector Test = new Vector(0,0);
		Vector Testr= new Vector(1,2);
		Test.scale(1);
		Test.add(new Vector(1,2));
//		System.out.println(Test); 
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void scaleNegative() {
		Vector Test = new Vector(7,2);
		Vector Testr= new Vector(0,0);
		Testr.copy(Test);
		Test.scale(-Test.getLength());
		Testr.invert();
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void normalizeAZeroTest() {
		Vector Test = new Vector(0,0);
		Test.normalize();
		Test.add(new Vector(2,7));
		assertEquals(true,Test.isEqual(new Vector(2,7)));
	}
	@Test
	public void zeroScale() {
		Vector Test = new Vector(7,6);
		Vector Testr = new Vector(10,10);
		Test.multiply(0);
		Test.add(Testr);
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void getBadAngleRight() {
		Vector Test = new Vector(0,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(angle,7,true);
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void getBadAngleUp() {
		Vector Test = new Vector(Math.PI/2,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(angle,7,true);
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void getBadAngleLeft() {
		Vector Test = new Vector(Math.PI,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(angle,7,true);
		assertEquals(true,Test.isEqual(Testr));
	}
	
	@Test
	public void getBadAngleDown() {
		Vector Test = new Vector(-Math.PI/2,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(angle,7,true);
		assertEquals(true,Test.isEqual(Testr));
	}
	
	@Test
	public void setBadAngleRight() {
		Vector Test = new Vector(0,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(1,0);
		Testr.scale(7);
		Testr.setAngle(angle);
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void setBadAngleUp() {
		Vector Test = new Vector(Math.PI/2,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(1,0);
		Testr.scale(7);
		Testr.setAngle(angle);
//		System.out.println("Testr vector: " + Testr);
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void setBadAngleLeft() {
		Vector Test = new Vector(Math.PI,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(1,0);
		Testr.scale(7);
		Testr.setAngle(angle);
		assertEquals(true,Test.isEqual(Testr));
	}
	@Test
	public void setBadAngleDown() {
		Vector Test = new Vector(-Math.PI/2,7,true);
		double angle = Test.getAngle();
		Vector Testr = new Vector(1,0);
		Testr.scale(7);
		Testr.setAngle(angle);
		assertEquals(true,Test.isEqual(Testr));
	}
	
	@Test
	public void addVector() {
		Vector a = new Vector(1, 2);
		Vector b = new Vector(3, 4);
		Vector res = Vector.add(a, b);
		Vector expt = new Vector(4, 6);
		assertEquals(true, res.isEqual(expt));
	}
	
	@Test
	public void subVector() {
		Vector a = new Vector(1, 2);
		Vector b = new Vector(3, 4);
		Vector res = Vector.subtract(a, b);
		Vector expt = new Vector(-2, -2);
		assertEquals(true, res.isEqual(expt));
	}
	
	@Test
	public void showtoString() {
		Vector a = new Vector(1, 2);
		String expt = "Vector [xComponent=1.0, yComponent=2.0]";
		assertEquals(expt, a.toString());
	}
	
	@Test
	public void isNotEqual() {
		Vector a = new Vector(1, 2);
		Vector b = new Vector(1, 2.5);
		assertEquals(false, a.isEqual(b));
	}

}
