package fightforlife;

import java.awt.Canvas;
import java.awt.event.KeyEvent;

import gameframework.core.GameUniverse;
import gameframework.moves_rules.MoveStrategyKeyboard;

public class MoveStrategyKeyboardExtended extends MoveStrategyKeyboard {
	private Canvas canvas;
	private GameUniverse universe;
	private Hero hero;
	
	public MoveStrategyKeyboardExtended(Canvas canvas, GameUniverse universe, Hero hero) {
		this.canvas = canvas;
		this.universe = universe;
		this.hero = hero;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		super.keyPressed(event);
		switch(event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				this.universe.addGameEntity(new Arrow(this.canvas, this.hero));
				break;
			}
		}
}