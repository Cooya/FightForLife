package fightforlife.entities;

import gameframework.core.Drawable;
import gameframework.core.GameEntity;
import gameframework.core.GameMovable;
import gameframework.core.Overlappable;
import gameframework.core.SpriteManager;
import gameframework.moves_rules.MoveBlockerChecker;
import observer_util.Observable;
import observer_util.Observer;
import soldier.core.Unit;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import fightforlife.CustomSpriteManager;
import fightforlife.Target;

public class Hero extends GameMovable implements Drawable, GameEntity, Overlappable, Observable<Point> {
	private static final int RENDERING_SIZE = 32;
	private static final int BOUNDING_BOX_SIZE = 20;
	
	private final SpriteManager spriteManager;
	private boolean movable = true;
	private int orientation; // 0, 1, 2 ou 3
	private MoveBlockerChecker moveBlocker;
	private Unit hero;
	private List<Observer<Point>> observers;
	private Target target;
	
	public Hero(Canvas canvas, MoveBlockerChecker moveBlocker) {
		this.spriteManager = new CustomSpriteManager("images/hero.png", canvas, RENDERING_SIZE, 9, 13);
		this.spriteManager.setTypes("", "", "", "static", "strike_up", "strike_left", "strike_down", "strike_right", "up", "left", "down", "right");
		this.orientation = 0;
		this.moveBlocker = moveBlocker;
		this.observers = new ArrayList<Observer<Point>>();
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		this.movable = true;
	
		if(tmp.getX() == 1) {
			spriteType += "right";
			this.orientation = 0;
		} else if(tmp.getX() == -1) {
			spriteType += "left";
			this.orientation = 1;
		} else if(tmp.getY() == 1) {
			spriteType += "down";
			this.orientation = 2;
		} else if(tmp.getY() == -1) {
			spriteType += "up";
			this.orientation = 3;
		} else {
			switch(this.orientation) {
				case 0 : spriteType += "right"; break;
				case 1 : spriteType += "left"; break;
				case 2 : spriteType += "down"; break;
				case 3 : spriteType += "up"; break;
			}
			this.spriteManager.reset();
			this.movable = false;
		}
		this.spriteManager.setType(spriteType);
		this.spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if(this.movable) {
			this.spriteManager.increment();
			notifyObservers(getPosition());
		}
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, BOUNDING_BOX_SIZE, BOUNDING_BOX_SIZE);
	}
	
	@Override
	public void addObserver(Observer<Point> observer) {
		this.observers.add(observer);
	}

	@Override
	public void removeObserver(Observer<Point> observer) {
		this.observers.remove(observer);
	}

	@Override
	public void notifyObservers(Point pos) {
		for(Observer<Point> observer : this.observers)
			observer.update(pos);
	}
	
	public int getOrientation() {
		return this.orientation;
	}
	
	public MoveBlockerChecker getMoveBlocker() {
		return this.moveBlocker;
	}
	
	public void setHeroUnit(Unit hero) {
		this.hero = hero;
	}
	
	public Unit getHeroUnit() {
		return this.hero;
	}
	
	public void setTarget() {
		this.target = new Target(getPosition());
		addObserver(target);
	}
	
	public Target getTarget() {
		return this.target;
	}
}