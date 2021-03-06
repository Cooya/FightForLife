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

public class Troll extends GameMovable implements Drawable, GameEntity, Overlappable {
	private static final int RENDERING_SIZE = 32;
	private static final int BOUNDING_BOX_SIZE = 40;
	
	private final SpriteManager spriteManager;
	private boolean movable = true;
	private Unit troll;

	public Troll(Canvas canvas) {
		this.spriteManager = new CustomSpriteManager("images/troll.png", canvas, RENDERING_SIZE, 9, 13);
		this.spriteManager.setTypes("", "", "", "", "strike_up", "strike_left", "strike_down", "strike_right", "up", "left", "down", "right");
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
		return new Rectangle(0, 0, BOUNDING_BOX_SIZE, BOUNDING_BOX_SIZE);
	}
	
	public Unit getTrollUnit() {
		return troll;
	}

	public void setTrollUnit(Unit troll) {
		this.troll = troll;
	}
}