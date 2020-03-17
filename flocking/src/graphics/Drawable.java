package graphics;
/*
 * unimplemented
	class Drawable{

ok		vector getCenter
ok		Colors getColor
ok		list drawable getdrawables
ok		double getHeight
ok		double getWidth
ok 		double getRadius
  
 }
 * 
 * */



/*
interface {
	public Vector getCenter
	public arrayList<Drawable> getDrawables
	public arralist<vector> getLines()
	public double getRadius
	public double getWidth()
	public double getHeight()
	public colors getcolor()
}
 
 
 */

import java.util.ArrayList;
import vector.Vector;
 

public interface Drawable {
	public default ArrayList<Drawable> getDrawables(){
		return null;
	}
	public default ArrayList<Vector> getlines() {
		return null;
	}
	public Vector getCenter();
	public default double getRadius() {
		return 0;
	}
	public default double getWidth() {
		return 0;
	}
	public default double getHeight() {
		return 0;
	}
	public default Colors getColor() {
		return Colors.BLUE;
	}
}
