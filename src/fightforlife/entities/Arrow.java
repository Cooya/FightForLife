package fightforlife.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import fightforlife.managers.ImageManager;
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
	private static final int SPEED = 30; // ne doit pas être égal ou supérieur à RENDERING_SIZE
	private static final String IMAGE_PATH = "images/arrow.png";
	private static final String IMAGE_DIR = "images/arrow/";
	private static final String IMAGE_ORIENTATION = "up";
	
	private static DrawableImage image = null;
	private static ImageManager imageManager;
	
	private Hero hero;
	private String orientation;
	
	public Arrow(Canvas canvas, Hero hero) {
		if(image == null) {
			image = new DrawableImage(IMAGE_PATH, canvas);
			try {
				imageManager = new ImageManager(canvas, image.getImage(), IMAGE_DIR, IMAGE_ORIENTATION);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		Point pos = hero.getPosition();
		this.setSpeedVector(new SpeedVectorDefaultImpl(hero.getSpeedVector().getDirection(), SPEED));
		this.setPosition(pos);
		this.hero = hero;

		Point direction;
		switch(hero.getOrientation()) {
			case 0 : direction = new Point(99999, 0); this.orientation = "left"; break;
			case 1 : direction = new Point(-99999, 0); this.orientation = "right"; break;
			case 2 : direction = new Point(0, 99999); this.orientation = "up"; break;
			case 3 : direction = new Point(0, -99999); this.orientation = "down"; break;
			default: direction = new Point(0, 0); this.orientation = "right";
		}

		GameMovableDriverDefaultImpl driver = new GameMovableDriverDefaultImpl();
		driver.setmoveBlockerChecker(hero.getMoveBlocker());
		driver.setStrategy(new MoveStrategyStraightLineExtended(pos, direction, SPEED));
		setDriver(driver);
	}

	@Override
	public void draw(Graphics g) {
		Point p = super.getPosition();
		try {
			imageManager.draw(g, p.x, p.y, this.orientation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void oneStepMoveAddedBehavior() {

	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
	}
	
	public Hero getHero() {
		return hero;
	}
}