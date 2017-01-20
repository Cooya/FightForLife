package fightforlife.strategies;

import java.awt.Point;

import gameframework.moves_rules.MoveStrategyStraightLine;
import gameframework.moves_rules.SpeedVector;

public class MoveStrategyStraightLineExtended extends MoveStrategyStraightLine {
	private int speed;

	public MoveStrategyStraightLineExtended(Point pos, Point goal, int speed) {
		super(pos, goal);
		this.speed = speed;
	}
	
	@Override
	public SpeedVector getSpeedVector() {
		SpeedVector speedVector = super.getSpeedVector();
		speedVector.setSpeed(speed);
		return speedVector;
	}
}