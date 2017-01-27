package fightforlife;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Iterator;
import java.util.Random;

import fightforlife.entities.Hero;
import fightforlife.entities.Tree;
import fightforlife.entities.Troll;
import fightforlife.rules.FightForLifeMoveBlockers;
import fightforlife.rules.FightForLifeOverlapRules;
import fightforlife.strategies.MoveStrategyKeyboardExtended;
import gameframework.core.CanvasDefaultImpl;
import gameframework.core.GameDefaultImpl;
import gameframework.core.GameEntity;
import gameframework.core.GameLevelDefaultImpl;
import gameframework.core.GameMovable;
import gameframework.core.GameMovableDriverDefaultImpl;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.core.GameUniverseViewPortDefaultImpl;
import gameframework.core.Movable;
import gameframework.moves_rules.IntersectTools;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.MoveBlockerCheckerDefaultImpl;
import gameframework.moves_rules.MoveStrategyKeyboard;
import gameframework.moves_rules.MoveStrategyRandom;
import gameframework.moves_rules.MoveStrategyStraightLine;
import gameframework.moves_rules.ObjectWithBoundedBox;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.units.UnitCenturion;
import soldier.weapon.WeaponGun;
import soldier.weapon.WeaponShield;
import soldier.weapon.WeaponSword;

public class FightForLifeGameLevel extends GameLevelDefaultImpl {
	private static final int NB_ROWS = 48;
	private static final int NB_COLUMNS = 64;
	private static final int SPRITE_SIZE = 16; // taille d'un élément
	private static final int NUMBER_OF_TROLLS = 10;
	private static final String BACKGROUND_IMAGE = "images/floor.jpg";
	private static final Random random = new Random();
	 public static UnitGroup composite=new UnitGroup("monstres");
	private Canvas canvas;
	private int[][] map;

	public FightForLifeGameLevel(GameDefaultImpl game) {
		super(game);
		this.canvas = game.getCanvas();
		
		this.map = new int[NB_ROWS][NB_COLUMNS];
		for(int i = 0; i < map.length; ++i)
			for(int j = 0; j < map[i].length; ++j)
				this.map[i][j] = 0;
	}

	@Override
	protected void init() {
		
		// instanciation du vérificateur des mouvements et mise en place de nos règles de mouvements
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		FightForLifeMoveBlockers moveBlockers = new FightForLifeMoveBlockers();
		moveBlockerChecker.setMoveBlockerRules(moveBlockers);
		
		// instanciation du processeur des chevauchements et mise en place de nos règles de chevauchements
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		OverlapRulesApplierDefaultImpl overlapRules = new FightForLifeOverlapRules(super.endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		// instanciation de l'univers du jeu et ajout aux règles
		this.universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		moveBlockers.setUniverse(this.universe);
		overlapRules.setUniverse(this.universe);

		// instanciation de la game board et association avec le canvas
		this.gameBoard = new GameUniverseViewPortDefaultImpl(this.canvas, this.universe);
		((GameUniverseViewPortDefaultImpl) this.gameBoard).setBackground(BACKGROUND_IMAGE);
		((CanvasDefaultImpl) this.canvas).setDrawingGameBoard(this.gameBoard);
		
		// génération des positions des murs
		generateWalls();
		
		// ajout des différentes entités à l'univers
		for(int i = 0; i < this.map.length; ++i) {
			for(int j = 0; j < this.map[0].length; ++j) {
				switch(this.map[i][j]) {
					case 1: universe.addGameEntity(new Tree(this.canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
				}
			}
		}
		
		// instanciation et positionnement du héros
		Hero hero = new Hero(this.canvas, moveBlockerChecker);
		setPosition(hero);
		GameMovableDriverDefaultImpl pacDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboardExtended(this.canvas, this.universe, hero);
		pacDriver.setStrategy(keyStr);
		pacDriver.setmoveBlockerChecker(moveBlockerChecker);
		this.canvas.addKeyListener(keyStr);
		hero.setDriver(pacDriver);
		this.universe.addGameEntity(hero);
		hero.setHeroUnit(new UnitCenturion("Billy"));
		hero.getHeroUnit().addEquipment(new WeaponSword());

		
		// instanciation et positionnement des trolls
		Troll troll;
		GameMovableDriverDefaultImpl driver = null;
		Unit unitTroll;
		
		for(int i = 0; i < NUMBER_OF_TROLLS; ++i) {
			driver = new GameMovableDriverDefaultImpl();
			driver.setStrategy(new MoveStrategyRandom());
			driver.setmoveBlockerChecker(moveBlockerChecker);
			troll = new Troll(this.canvas);
			troll.setDriver(driver);
			setPosition(troll);
			this.universe.addGameEntity(troll);
			troll.setTrollUnit(new UnitCenturion("troll"+i));
			unitTroll=troll.getTrollUnit();
			unitTroll.addEquipment(new WeaponShield());
		}
		//composite troll
		for(int i = 0; i < 3; ++i) {
			troll = new Troll(this.canvas);
			troll.setDriver(driver);
			setPosition(troll);
			this.universe.addGameEntity(troll);
			troll.setTrollUnit(new UnitCenturion("troll"+i));
			unitTroll=troll.getTrollUnit();
			composite.addUnit(unitTroll);
			composite.addEquipment(new WeaponGun());
		}
	}
	
	private void generateWalls() {
		for(int i = 0; i < this.map.length; i += this.map.length - 1) 
			for(int j = 0; j < this.map[0].length; ++j)
				this.map[i][j] = 1;
		
		for(int i = 0; i < this.map[0].length; i += this.map[0].length - 1)
			for(int j = 0; j < this.map.length; ++j)
				this.map[j][i] = 1;
		
		int nbWalls = 30;
		int wallSize;
		int randomX;
		int randomY;
		boolean direction;
		for(int i = 0; i < nbWalls; ++i) {
			randomX = random.nextInt(this.map[0].length);
			randomY = random.nextInt(this.map.length);
			direction = random.nextBoolean();
			wallSize = random.nextInt(20);
			for(int j = 0; j < wallSize; ++j) {
				if(direction && randomX + j < this.map[0].length)
					this.map[randomY][randomX + j] = 1;
				else if(!direction && randomY + j < this.map.length)
					this.map[randomY + j][randomX] = 1;
				else
					break;
			}
		}
	}
	
	private void setPosition(GameMovable movable) {
		Point randomPoint = new Point();
		do {
			randomPoint.x = random.nextInt(this.map[0].length) * 16;
			randomPoint.y = random.nextInt(this.map.length) * 16;
			movable.setPosition(randomPoint);
		}
		while(overlapAnotherEntity(movable));
	}
	
	private boolean overlapAnotherEntity(GameMovable movable) {
		Shape shape = IntersectTools.getIntersectShape(movable, movable.getSpeedVector());
		Shape shape2;
		
		Iterator<GameEntity> it = this.universe.gameEntities();
		GameEntity entity;
		while(it.hasNext()) {
			entity = it.next();
			if(entity instanceof Movable) {
				movable = (GameMovable) entity; 
				shape2 = IntersectTools.getIntersectShape(movable, movable.getSpeedVector());
			}
			else
				shape2 = ((ObjectWithBoundedBox) entity).getBoundingBox();
			if(shape.intersects((Rectangle) shape2))
				return true;
		}
		return false;
	}
}