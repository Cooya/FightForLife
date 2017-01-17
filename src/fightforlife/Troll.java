package fightforlife;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Troll extends GameMovable implements Drawable, GameEntity, Overlappable {
	public static final int RENDERING_SIZE = 60;
	
	private final SpriteManagerDefaultImpl spriteManager;
	protected boolean movable = true;

	public Troll(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/troll.png", defaultCanvas, RENDERING_SIZE, 9, 13);
		spriteManager.setTypes("", "", "", "", "strike_up", "strike_left", "strike_down", "strike_right", "up", "left", "down", "right");
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType += "right";
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if(movable)
			spriteManager.increment();
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
}