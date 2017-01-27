package fightforlife;

import java.awt.Point;

import observer_util.Observer;

public class Target implements Observer<Point> {
	private Point point;
	
	public Target(Point point) {
		this.point = point;
	}

	@Override
	public void update(Point point) {
		this.point = point;
	}
	
	public Point getPoint() {
		return this.point;
	}
}