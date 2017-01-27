package fightforlife.strategies;

import java.awt.Point;

import observer_util.Observer;
import gameframework.moves_rules.MoveStrategy;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;

public class MoveStrategyDynamicTarget implements MoveStrategy, Observer<Point> {
	private Point currentPos;
	private Point target;
	private int speed;
	

	public MoveStrategyDynamicTarget(Point currentPos, Point target, int speed) {
		this.currentPos = currentPos;
		this.target = target;
		this.speed = speed;
	}

	@Override
	public void update(Point point) {
		this.target = point;
	}
	
	@Override
	public SpeedVector getSpeedVector() {
		double dist = this.currentPos.distance(this.target);
		int xDirection = (int) Math.rint((this.target.getX() - this.currentPos.getX()) / dist);
		int yDirection = (int) Math.rint((this.target.getY() - this.currentPos.getY()) / dist);		
		return new SpeedVectorDefaultImpl(new Point(xDirection, yDirection), this.speed);
	}
}