package fightforlife.strategies;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.KeyEvent;

import fightforlife.entities.Arrow;
import fightforlife.entities.Hero;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.MoveStrategyKeyboard;
import gameframework.moves_rules.SpeedVector;
import gameframework.moves_rules.SpeedVectorDefaultImpl;

public class MoveStrategyKeyboardExtended extends MoveStrategyKeyboard {
	private static final Point NULL_POINT = new Point(0, 0);
	
	private Canvas canvas;
	private GameUniverse universe;
	private Hero hero;
	private SpeedVector tmpVector;
	private int counter;
	private Point lastDirection;
	
	public MoveStrategyKeyboardExtended(Canvas canvas, GameUniverse universe, Hero hero) {
		this.canvas = canvas;
		this.universe = universe;
		this.hero = hero;
		this.tmpVector = new SpeedVectorDefaultImpl(NULL_POINT);
		this.counter = 0;
		this.lastDirection = NULL_POINT;
	}
	
	@Override
	public SpeedVector getSpeedVector() {
		Point direction = this.speedVector.getDirection();
		this.tmpVector.setDirection(direction);
		
		if(!this.lastDirection.equals(direction)) {
			this.counter = 0;
			this.lastDirection = direction;
		}
		
		if(this.counter++ > 3) {
			this.speedVector.setDirection(NULL_POINT);
			this.counter = 0;
		}
		return this.tmpVector;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		super.keyPressed(event);
		switch(event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				this.universe.addGameEntity(new Arrow(this.canvas, this.hero));
				break;
		}
	}
}