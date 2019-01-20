package ca.mcgill.esce211.lab1;

public interface MotorController {
	public void turnLeft(int delta);
	public void turnRight(int delta);
	public void goStraight();
	public void reverse();
}
