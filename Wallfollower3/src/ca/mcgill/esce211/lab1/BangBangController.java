package ca.mcgill.esce211.lab1;

public class BangBangController implements UltrasonicController {

	private static final int FILTER_OUT = 100;
	private static final int DISTANCE_THRESHOLD = 80;
	private final int bandCenter;
	private final int bandwidth;
	private final int BANG_DELTA;
	private final int SLEEPINT = 60;
	//private final int maxError = 25;
	
	private MotorController motorController;
	
	private int filterControl;
	private int distance;

	public BangBangController(int bandCenter, int bandwidth, int bangDelta, MotorController motorController) {
		this.bandCenter = bandCenter;
		this.bandwidth = bandwidth;
		this.filterControl = 0;
		this.motorController = motorController;
		this.BANG_DELTA = bangDelta;
		
		
		motorController.goStraight();
	}

	@Override
	public void processUSData(int polledDistance) {
//		this.distance = polledDistance;
		int error;

		// rudimentary filter - toss out invalid samples corresponding to null
		// signal.
		// (n.b. this was not included in the Bang-bang controller, but easily
		// could have).
		//
		if (polledDistance >= DISTANCE_THRESHOLD && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the
			// filter value
			filterControl++;
		} else if (polledDistance >= DISTANCE_THRESHOLD) {
			// We have repeated large values, so there must actually be nothing
			// there: leave the distance alone
			this.distance = polledDistance;
		} else {
			// distance went below 255: reset filter and leave
			// distance alone.
			filterControl = 0;
			this.distance = polledDistance;
		}
		
		error = bandCenter - distance;
		
		// out of bounds
		if (Math.abs(error) > bandwidth / 2) {
			// too close to the wall
			if (error > 0) {
				motorController.turnRight(BANG_DELTA);
			}
			// too far away from the wall
			else {
				motorController.turnLeft(BANG_DELTA);
			}
		}
		
		
		else {
			motorController.goStraight();
		}
		
		try {
			Thread.sleep(SLEEPINT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int readUSDistance() {
		return this.distance;
	}
}
