package fightforlife;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManager;
import gameframework.core.SpriteManagerDefaultImpl;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Hero extends GameMovable implements Drawable, GameEntity, Overlappable {
	public static final int RENDERING_SIZE = 60;
	
	protected final SpriteManager spriteManager;
	protected boolean movable = true;

	public Hero(Canvas defaultCanvas) {
		spriteManager = new SpriteManagerDefaultImpl("images/hero.png", defaultCanvas, RENDERING_SIZE, 9, 13);
		spriteManager.setTypes("", "", "", "static", "strike_up", "strike_left", "strike_down", "strike_right", "up", "left", "down", "right");
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;
	
		if (tmp.getX() == 1) {
			spriteType += "right";
		} else if (tmp.getX() == -1) {
			spriteType += "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
		} else if (tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType = "static";
			spriteManager.reset();
			movable = false;
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
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
	}
}