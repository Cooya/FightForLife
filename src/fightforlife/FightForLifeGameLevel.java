package fightforlife;

import java.awt.Canvas;
import java.awt.Point;
import java.util.Random;

import gameframework.core.CanvasDefaultImpl;
import gameframework.core.GameDefaultImpl;
import gameframework.core.GameLevelDefaultImpl;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.core.GameUniverseViewPortDefaultImpl;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.MoveBlockerRulesApplierDefaultImpl;
import gameframework.moves_rules.MoveStrategyKeyboard;
import gameframework.moves_rules.MoveStrategyRandom;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

public class FightForLifeGameLevel extends GameLevelDefaultImpl {
	private static final int SPRITE_SIZE = 16; // taille d'un élément
	private static final int NUMBER_OF_TROLLS = 10;
	private static final String BACKGROUND_IMAGE = "images/floor.jpg";
	private static final Random random = new Random();
	
	private Canvas canvas;
	private int[][] map;

	public FightForLifeGameLevel(GameDefaultImpl game) {
		super(game);
		this.canvas = game.getCanvas();
		
		this.map = new int[GameDefaultImpl.getNbRows()][GameDefaultImpl.getNbColumns()];
		for(int i = 0; i < map.length; ++i)
			for(int j = 0; j < map[i].length; ++j)
				this.map[i][j] = 0;
		
		generateWalls();
	}
	
	private void generateWalls() {
		int nbWalls = 30;
		int wallSize;
		int randomX;
		int randomY;
		boolean direction;
		for(int i = 0; i < nbWalls; ++i) {
			randomX = random.nextInt(this.map.length);
			randomY = random.nextInt(this.map.length);
			direction = random.nextBoolean();
			wallSize = random.nextInt(20);
			for(int j = 0; j < wallSize; ++j) {
				if(direction && randomX + j < this.map.length)
					this.map[randomX + j][randomY] = 1;
				else if(!direction && randomY + j < this.map.length)
					this.map[randomX][randomY + j] = 1;
				else
					break;
			}
		}
	}
	
	private Point getMovableEntityPosition() {
		int randomX;
		int randomY;
		do {
			randomX = random.nextInt(this.map.length);
			randomY = random.nextInt(this.map.length);
		}
		while(this.map[randomX][randomY] != 1);
		return new Point(randomX * SPRITE_SIZE, randomY * SPRITE_SIZE);
	}

	@Override
	protected void init() {
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		moveBlockerChecker.setMoveBlockerRules(new MoveBlockerRulesApplierDefaultImpl());
		
		OverlapRulesApplierDefaultImpl overlapRules = new FightForLifeOverlapRules();
		overlapProcessor.setOverlapRules(overlapRules);

		this.universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(this.universe);

		this.gameBoard = new GameUniverseViewPortDefaultImpl(this.canvas, this.universe);
		((GameUniverseViewPortDefaultImpl) this.gameBoard).setBackground(BACKGROUND_IMAGE);
		((CanvasDefaultImpl) this.canvas).setDrawingGameBoard(this.gameBoard);
		
		for(int i = 0; i < this.map.length; ++i) {
			for(int j = 0; j < this.map[i].length; ++j) {
				switch(this.map[i][j]) {
					case 1: universe.addGameEntity(new Wall(this.canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}
			}
		}
		
		Hero hero = new Hero(canvas);
		GameMovableDriverDefaultImpl pacDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard();
		pacDriver.setStrategy(keyStr);
		pacDriver.setmoveBlockerChecker(moveBlockerChecker);
		canvas.addKeyListener(keyStr);
		hero.setDriver(pacDriver);
		hero.setPosition(getMovableEntityPosition());
		universe.addGameEntity(hero);
		
		Troll troll;
		GameMovableDriverDefaultImpl driver;
		for(int i = 0; i < NUMBER_OF_TROLLS; ++i) {
			driver = new GameMovableDriverDefaultImpl();
			driver.setStrategy(new MoveStrategyRandom());
			driver.setmoveBlockerChecker(moveBlockerChecker);
			troll = new Troll(canvas);
			troll.setDriver(driver);
			troll.setPosition(getMovableEntityPosition());
			universe.addGameEntity(troll);
			//overlapRules.addTroll(troll);
		}
	}
}