package ca.mcgill.esce211.lab1;

public class PController implements UltrasonicController {

	private static final int FILTER_OUT = 20;
	private static final int DISTANCE_THRESHOLD = 150;
	private final int bandCenter;
	private final int bandWidth;
	private final int maxDelta;
	private final float pFactor;
	private final int SLEEPINT = 50;

	private int distance;
	private int filterControl;
	private MotorController motorController;

	public PController(int bandCenter, int bandwidth, int maxDelta, float pFactor, MotorController motorController) {
		this.bandCenter = bandCenter;
		this.bandWidth = bandwidth;
		this.filterControl = 0;
		this.motorController = motorController;
		this.maxDelta = maxDelta;
		this.pFactor = pFactor;

		motorController.goStraight();
	}

	@Override
	public void processUSData(int distance) {
		int error;

		// rudimentary filter - toss out invalid samples corresponding to null
		// signal.
		// (n.b. this was not included in the Bang-bang controller, but easily
		// could have).
		//
		if (distance >= DISTANCE_THRESHOLD && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the
			// filter value
			filterControl++;
		} else if (distance >= DISTANCE_THRESHOLD) {
			// We have repeated large values, so there must actually be nothing
			// there: leave the distance alone
			this.distance = distance;
		} else {
			// distance went below DISTANCE_THRESHOLD: reset filter and leave
			// distance alone.
			filterControl = 0;
			this.distance = distance;
		}
			
	
		error = bandCenter - distance;
		
		// out of bounds
		if (Math.abs(error) > bandWidth / 2) {
			int scaledDelta = Math.min(maxDelta, Math.abs((int) (error * pFactor)));
			// too close to the wall
			if (error >= 0) {
				motorController.turnRight(scaledDelta);
			}
			// too far away from the wall
			else {
				motorController.turnLeft(scaledDelta);
			}
		}
		else {
			
			motorController.goStraight();
		}
		/*
		try {
			Thread.sleep(SLEEPINT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		 */
	}

	@Override
	public int readUSDistance() {
		return this.distance;
	}

}
