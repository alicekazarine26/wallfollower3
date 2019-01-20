package ca.mcgill.esce211.lab1;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class MotorCtr implements MotorController {
	
	private final EV3LargeRegulatedMotor leftMotor;
	private final EV3LargeRegulatedMotor rightMotor;
	
	private final int MOTOR_SPEED;
	
	public MotorCtr(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor, int motorSpeed) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.MOTOR_SPEED = motorSpeed;
	}

	@Override
	public void turnLeft(int delta) {
		leftMotor.setSpeed(MOTOR_SPEED - delta);
		rightMotor.setSpeed(MOTOR_SPEED + delta);
		leftMotor.forward();
		rightMotor.forward();
	}

	@Override
	public void turnRight(int delta) {
		leftMotor.setSpeed(MOTOR_SPEED + delta);
		rightMotor.setSpeed(MOTOR_SPEED - delta);
		leftMotor.forward();
		rightMotor.forward();
	}

	@Override
	public void goStraight() {
		leftMotor.setSpeed(MOTOR_SPEED);
		rightMotor.setSpeed(MOTOR_SPEED);
		leftMotor.forward();
		rightMotor.forward();

	}

	@Override
	public void reverse() {
		leftMotor.setSpeed(MOTOR_SPEED);
		rightMotor.setSpeed(MOTOR_SPEED);
		leftMotor.backward();
		rightMotor.backward();
	}

}
