package fightforlife;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.Overlappable;
import gameframework.moves_rules.MoveStrategyStraightLine;

public class Arrow extends GameMovable implements Drawable, GameEntity, Overlappable {
	private static final int RENDERING_SIZE = 32;
	private static final int SPEED = 10;
	private static DrawableImage image = null;

	public Arrow(Canvas canvas, Hero hero) {
		if(image == null)
			image = new DrawableImage("images/arrow.png", canvas);
		Point pos = hero.getPosition();
		this.setSpeedVector(hero.getSpeedVector());
		this.setPosition(pos);
		GameMovableDriverDefaultImpl driver = new GameMovableDriverDefaultImpl();
		
		
		Point direction;
		switch(hero.getSlant()) {
			case 0 : direction = new Point(99999, 0); break;
			case 1 : direction = new Point(-99999, 0); break;
			case 2 : direction = new Point(0, 99999); break;
			case 3 : direction = new Point(0, -99999); break;
			default: direction = new Point(0, 0); 
		}
		driver.setStrategy(new MoveStrategyStraightLine(pos, direction));
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