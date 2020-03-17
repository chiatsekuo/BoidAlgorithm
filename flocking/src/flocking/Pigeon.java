package flocking;

import boid.Alignment;
import boid.BoidRule;
import boid.BoidRuleBase;
import boid.Cohesion;
import boid.Flee;
import boid.Separation;
import boid.Sight;
import graphics.Colors;
import graphics.DrawingBird;
import graphics.DrawingRule;
import graphics.DrawingSight;
import vector.Vector;

public class Pigeon extends DrawingBird {

	private static BoidRule DrawingBasic(boolean draw){
		BoidRule end = new BoidRuleBase();
		end = new Flee(end,1,Hawk.class);
		if(draw) {
			end = new DrawingRule(end,Colors.YELLOW);
		}
		end = new Alignment(end,1);
		if(draw) {
			end = new DrawingRule(end,Colors.YELLOW);
		}
		end = new Separation(end,1);
		if(draw) {
			end = new DrawingRule(end,Colors.RED);
		}
		end = new Cohesion(end,1);
		if(draw) {
			end = new DrawingRule(end,Colors.GREEN);
		}
		if(draw) {
			end = new DrawingSight(end);
		}else {
			end = new Sight(end);
		}
		return end;
	}
	public Pigeon(int x, int y, Vector vel,boolean draw) {
		super(x, y, vel,DrawingBasic(draw),Colors.ORANGE);
	}


}
