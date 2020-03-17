package flocking;

import boid.Alignment;
import boid.BoidRule;
import boid.BoidRuleBase;
import boid.Chase;
import boid.Cohesion;
import boid.Separation;
import boid.Sight;
import graphics.Colors;
import graphics.DrawingBird;
import graphics.DrawingRule;
import graphics.DrawingSight;
import vector.Vector;

public class Hawk extends DrawingBird {

	private static BoidRule DrawingBasic(boolean draw){
		BoidRule end = new BoidRuleBase();
		end = new Chase(end,2,Pigeon.class);
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
	public Hawk(int x, int y, Vector vel,boolean draw) {
		super(x, y, vel,DrawingBasic(draw),Colors.RED);
	}
}
