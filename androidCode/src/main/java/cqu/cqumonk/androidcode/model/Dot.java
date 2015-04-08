package cqu.cqumonk.androidcode.model;

public class Dot {

	private int x,y;
	private Status status;
	
	//每个点的状态，IN代表当前猫在此处，ON代表此点为障碍，OFF代表此点无障碍
	public enum Status{
		ON,OFF,IN
	}

	public Dot(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.status = Status.OFF;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

}
