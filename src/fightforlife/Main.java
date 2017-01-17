package fightforlife;

import java.util.ArrayList;

import gameframework.core.GameDefaultImpl;
import gameframework.core.GameLevel;

public class Main {
	public static void main(String[] args) {
		GameDefaultImpl game = new GameDefaultImpl();
		ArrayList<GameLevel> levels = new ArrayList<>();

		levels.add(new FightForLifeGameLevel(game));
		
		game.setLevels(levels);
		game.start();
	}
}