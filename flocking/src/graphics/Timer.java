package graphics;

import java.util.concurrent.TimeUnit;

import utils.Log;

import java.lang.System;
/*
class timer {
ok	timer(name,waitTime) //give a name as an id and a amount of time in millisconds
ok	sleep()				//wait until the defined number of miliseconds has passed between the last class
ok	set/get miliseoncds //change the speed of the timer at runtime

}

 */

public class Timer {
	private static final Log log = utils.Log.getLog();
	private static final int DEBUG = utils.Log.TIMER + utils.Log.DEBUG; 
	private static final int ERROR = utils.Log.TIMER + utils.Log.ERROR;
	
	private int milliSeconds = 10;
	private long timeStamp;
	private String name;
	
	
	public Timer(String name, int unit) {
		milliSeconds=unit;
		this.name=name;
		timeStamp = System.currentTimeMillis();
	}
	
	public void sleep() throws InterruptedException {
		long current = System.currentTimeMillis();
		if(milliSeconds>=current-timeStamp) {
			TimeUnit.MILLISECONDS.sleep(milliSeconds - (current-timeStamp));
			log.println(this.toString()+"\tWaited:\t " + String.valueOf(milliSeconds - (current-timeStamp)),DEBUG);
		}else {
			log.println(this.toString()+"\tBehind:\t" + String.valueOf(milliSeconds - (current-timeStamp)),ERROR);
		}
		timeStamp=current;
	}

	protected int getMilliSeconds() {
		return milliSeconds;
	}

	protected void setMilliSeconds(int milliseconds) {
		this.milliSeconds = milliseconds;
	}

	@Override
	public String toString() {
		return "Timer [name: " + this.name + " timeUnit: " + this.milliSeconds+"]";
	}
}
