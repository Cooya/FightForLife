package fightforlife.entities;

import gameframework.core.Drawable;
import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;
import gameframework.moves_rules.MoveBlocker;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Tree implements Drawable, MoveBlocker, GameEntity {
	private static final int RENDERING_SIZE = 16;
	private static DrawableImage image = null;
	
	int x, y;

	public Tree(Canvas canvas, int xx, int yy) {
		if(image == null)
			image = new DrawableImage("images/tree3.png", canvas);
		this.x = xx;
		this.y = yy;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), this.x, this.y, RENDERING_SIZE, RENDERING_SIZE, null);
	}

	public Point getPos() {
		return (new Point(this.x, this.y));
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(this.x, this.y, RENDERING_SIZE, RENDERING_SIZE));
	}
}