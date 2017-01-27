package fightforlife.strategies;

import java.awt.Point;

import fightforlife.Target;
import gameframework.moves_rules.MoveStrategy;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;

public class MoveStrategyDynamicTarget implements MoveStrategy {
	private Point currentPos;
	private Target target;
	private int speed;
	

	public MoveStrategyDynamicTarget(Point currentPos, Target target, int speed) {
		this.currentPos = currentPos;
		this.target = target;
		this.speed = speed;
	}

	@Override
	public SpeedVector getSpeedVector() {
		Point target = this.target.getPoint();
		double dist = this.currentPos.distance(target);
		int xDirection = (int) Math.rint((target.getX() - this.currentPos.getX()) / dist);
		int yDirection = (int) Math.rint((target.getY() - this.currentPos.getY()) / dist);		
		return new SpeedVectorDefaultImpl(new Point(xDirection, yDirection), this.speed);
	}
}