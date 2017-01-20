package fightforlife.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import fightforlife.strategies.MoveStrategyStraightLineExtended;
import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.Overlappable;
import gameframework.moves_rules.SpeedVectorDefaultImpl;

public class Arrow extends GameMovable implements Drawable, GameEntity, Overlappable {
	private static final int RENDERING_SIZE = 32;
	private static final int SPEED = 20;
	private static DrawableImage image = null;

	public Arrow(Canvas canvas, Hero hero) {
		if(image == null)
			image = new DrawableImage("images/arrow.png", canvas);
		Point pos = hero.getPosition();
		this.setSpeedVector(new SpeedVectorDefaultImpl(hero.getSpeedVector().getDirection(), SPEED));
		this.setPosition(pos);
		
		Point direction;
		switch(hero.getSlant()) {
			case 0 : direction = new Point(99999, 0); break;
			case 1 : direction = new Point(-99999, 0); break;
			case 2 : direction = new Point(0, 99999); break;
			case 3 : direction = new Point(0, -99999); break;
			default: direction = new Point(0, 0); 
		}
		
		GameMovableDriverDefaultImpl driver = new GameMovableDriverDefaultImpl();
		driver.setmoveBlockerChecker(hero.getMoveBlocker());
		driver.setStrategy(new MoveStrategyStraightLineExtended(pos, direction, SPEED));
		setDriver(driver);
	}
	
	@Override
	public void draw(Graphics g) {
		Point p = super.getPosition();
		g.drawImage(image.getImage(), p.x, p.y, RENDERING_SIZE, RENDERING_SIZE, null);
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
	}
}