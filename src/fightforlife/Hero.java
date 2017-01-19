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
	private static final int RENDERING_SIZE = 32;
	
	private final SpriteManager spriteManager;
	private boolean movable = true;
	private int slant;

	public Hero(Canvas canvas) {
		this.spriteManager = new SpriteManagerDefaultImpl("images/hero.png", canvas, RENDERING_SIZE, 9, 13);
		this.spriteManager.setTypes("", "", "", "static", "strike_up", "strike_left", "strike_down", "strike_right", "up", "left", "down", "right");
		this.slant = 0;
	}
	
	public int getSlant() {
		return this.slant;
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		this.movable = true;
	
		if(tmp.getX() == 1) {
			spriteType += "right";
			this.slant = 0;
		} else if(tmp.getX() == -1) {
			spriteType += "left";
			this.slant = 1;
		} else if(tmp.getY() == 1) {
			spriteType += "down";
			this.slant = 2;
		} else if(tmp.getY() == -1) {
			spriteType += "up";
			this.slant = 3;
		} else {
			spriteType = "static";
			this.slant = 0;
			this.spriteManager.reset();
			this.movable = false;
		}
		this.spriteManager.setType(spriteType);
		this.spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if(this.movable)
			this.spriteManager.increment();
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
	}
}