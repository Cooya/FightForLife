package fightforlife.entities;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.core.Overlappable;
import gameframework.moves_rules.MoveBlocker;

public class PowerPlus implements Drawable,Overlappable, GameEntity  {
	private static final int RENDERING_SIZE = 16;
	private static DrawableImage image = null;
	
	int x, y;

	public PowerPlus(Canvas canvas, Point p) {
		if(image == null)
			image = new DrawableImage("images/power.png", canvas);
		this.x = p.x;
		this.y = p.y;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), this.x, this.y, RENDERING_SIZE, RENDERING_SIZE, null);
	}

	

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(this.x, this.y, RENDERING_SIZE, RENDERING_SIZE));
	}

	@Override
	public Point getPosition() {
		// TODO Auto-generated method stub
		return (new Point(this.x, this.y));
	}


	



}
