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

public class Troll extends GameMovable implements Drawable, GameEntity, Overlappable {
	private static final int RENDERING_SIZE = 32;
	
	private final SpriteManager spriteManager;
	private boolean movable = true;

	public Troll(Canvas canvas) {
		this.spriteManager = new SpriteManagerDefaultImpl("images/troll.png", canvas, RENDERING_SIZE, 9, 13);
		this.spriteManager.setTypes("", "", "", "", "strike_up", "strike_left", "strike_down", "strike_right", "up", "left", "down", "right");
	}

	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		this.movable = true;

		if(tmp.getX() == -1) {
			spriteType += "left";
		} else if(tmp.getY() == 1) {
			spriteType += "down";
		} else if(tmp.getY() == -1) {
			spriteType += "up";
		} else {
			spriteType += "right";
		}

		this.spriteManager.setType(spriteType);
		this.spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if(this.movable)
			this.spriteManager.increment();
	}

	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
	}
}