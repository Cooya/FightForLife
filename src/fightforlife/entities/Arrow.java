package fightforlife.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

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
	private static final int SPEED = 50;
	private static DrawableImage image = null;
	private int orientation;

	public Arrow(Canvas canvas, Hero hero) {
		if(image == null)
			image = new DrawableImage("images/flame.gif", canvas);
		Point pos = hero.getPosition();
		this.setSpeedVector(new SpeedVectorDefaultImpl(hero.getSpeedVector().getDirection(), SPEED));
		this.setPosition(pos);

		Point direction;
		switch(hero.getSlant()) {
			case 0 : direction = new Point(99999, 0); this.orientation = 0; break;
			case 1 : direction = new Point(-99999, 0); this.orientation = 1; break;
			case 2 : direction = new Point(0, 99999); this.orientation = 2; break;
			case 3 : direction = new Point(0, -99999); this.orientation = 3; break;
			default: direction = new Point(0, 0);
		}

		GameMovableDriverDefaultImpl driver = new GameMovableDriverDefaultImpl();
		driver.setmoveBlockerChecker(hero.getMoveBlocker());
		driver.setStrategy(new MoveStrategyStraightLineExtended(pos, direction, SPEED));
		setDriver(driver);
	}

	@Override
	public void draw(Graphics g) {	
		AffineTransform trans = new AffineTransform();
		Point p = super.getPosition();
		trans.translate(p.x + RENDERING_SIZE / 2, p.y + RENDERING_SIZE / 2);
		
		switch(this.orientation) {
			case 0 : trans.rotate(Math.toRadians(0)); break;
			case 1 : trans.rotate(Math.toRadians(180)); break;
			case 2 : trans.rotate(Math.toRadians(90)); break;
			case 3 : trans.rotate(Math.toRadians(-90)); break;
		}
		
		Image img = image.getImage();
		trans.scale((double) RENDERING_SIZE / img.getHeight(null) / 2, (double) RENDERING_SIZE / img.getWidth(null) * 2);
		((Graphics2D) g).drawImage(image.getImage(), trans, null);
	}

	@Override
	public void oneStepMoveAddedBehavior() {

	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
	}
}