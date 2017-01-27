package fightforlife.entities;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManager;
import soldier.core.Unit;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import fightforlife.managers.CustomSpriteManager;

public class Dragon extends GameMovable implements Drawable, GameEntity, Overlappable {
	private static final int RENDERING_SIZE = 55;
	
	private final SpriteManager spriteManager;
	private boolean movable = true;
	private Unit troll;

	public Dragon(Canvas canvas) {
		this.spriteManager = new CustomSpriteManager("images/troll2.png", canvas, RENDERING_SIZE,1 , 4);
		this.spriteManager.setTypes("down", "left", "right", "up");
	}

	@Override
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

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE);
	}
	
	public Unit getTrollUnit() {
		return troll;
	}

	public void setTrollUnit(Unit troll) {
		this.troll = troll;
	}
}