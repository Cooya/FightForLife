package fightforlife.managers;

import java.awt.Canvas;
import java.lang.reflect.Field;

import gameframework.core.SpriteManagerDefaultImpl;

public class CustomSpriteManager extends SpriteManagerDefaultImpl {

	public CustomSpriteManager(String filename, Canvas canvas, int renderingSize, int maxSpriteNumber) {
		super(filename, canvas, renderingSize, maxSpriteNumber);
	}
	
	public CustomSpriteManager(String filename, Canvas canvas, int renderingSize, int spriteNumber, int maxSpriteNumber) {
		super(filename, canvas, renderingSize, maxSpriteNumber);
		try {
			Field field = this.getClass().getSuperclass().getDeclaredField("maxSpriteNumber");
			field.setAccessible(true);
			field.set(this, spriteNumber);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
}