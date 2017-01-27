package fightforlife;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Iterator;
import java.util.Random;

import fightforlife.entities.Arrow;
import fightforlife.entities.Hero;
import fightforlife.entities.PowerPlus;
import fightforlife.entities.Tree;
import fightforlife.entities.Troll;
import fightforlife.entities.Troll2;
import fightforlife.rules.FightForLifeMoveBlockers;
import fightforlife.rules.FightForLifeOverlapRules;
import fightforlife.strategies.MoveStrategyDynamicTarget;
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
import gameframework.moves_rules.ObjectWithBoundedBox;
import gameframework.moves_rules.OverlapProcessor;
import gameframework.moves_rules.OverlapProcessorDefaultImpl;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import soldier.units.UnitBikerMan;
import soldier.units.UnitCenturion;
import soldier.units.UnitHorseMan;
import soldier.util.DeadUnitCounterObserver;
import soldier.weapon.WeaponShield;
import soldier.weapon.WeaponSword;

public class FightForLifeGameLevel extends GameLevelDefaultImpl {
	private static final int NB_ROWS = 48;
	private static final int NB_COLUMNS = 64;
	private static final int SPRITE_SIZE = 16; // taille d'un élément
	private static final int NUMBER_OF_TROLLS = 10;
	private static final int TROLLS_SPEED = 4;
	private static final int TROLLS_GENERATION_INTERVAL = 5000;
	private static final String BACKGROUND_IMAGE = "images/floor.jpg";
	private static final Random random = new Random();		
	private static Unit trolls = new UnitGroup("trolls");
	private Canvas canvas;
	private int[][] map;
	private int trollsCounter;

	public FightForLifeGameLevel(GameDefaultImpl game) {
		super(game);
		this.canvas = game.getCanvas();
		
		this.map = new int[NB_ROWS][NB_COLUMNS];
		for(int i = 0; i < map.length; ++i)
			for(int j = 0; j < map[i].length; ++j)
				this.map[i][j] = 0;
		
		this.trollsCounter = 0;
	}

	@Override
	protected void init() {
		
		// instanciation du vérificateur des mouvements et mise en place de nos règles de mouvements
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();
		FightForLifeMoveBlockers moveBlockers = new FightForLifeMoveBlockers();
		moveBlockerChecker.setMoveBlockerRules(moveBlockers);
		
		// instanciation du processeur des chevauchements et mise en place de nos règles de chevauchements
		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		OverlapRulesApplierDefaultImpl overlapRules = new FightForLifeOverlapRules(super.endOfGame,life[0],score[0]);
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
		
		// ajout des différentes entités statiques à l'univers
		for(int i = 0; i < this.map.length; ++i) {
			for(int j = 0; j < this.map[0].length; ++j) {
				switch(this.map[i][j]) {
					case 1: universe.addGameEntity(new Tree(this.canvas, j * SPRITE_SIZE, i * SPRITE_SIZE));
					
				}
			}
		}
		
		// instanciation et positionnement du héros
		Hero hero = newHero(moveBlockerChecker);
		
		// instanciation et positionnement des trolls
		for(int i = 0; i < NUMBER_OF_TROLLS; ++i)
			newTroll(moveBlockerChecker, hero);
		
		// ajout d'une épée à tous les trolls
		trolls.addEquipment(new WeaponShield());
		
		// chargement de la classe Arrow pour éviter un petit lag au premier tir du joueur
		new Arrow(canvas, hero);
		
		// lancement de la boucle de génération de trolls
		runTrollsGenerationLoop(moveBlockerChecker, hero);
	}
	
	private Hero newHero(MoveBlockerChecker moveBlockerChecker) {
		Hero hero = new Hero(this.canvas, moveBlockerChecker);
		setHeroPosition(hero);
		this.universe.addGameEntity(new PowerPlus(canvas, new Point(50,50)));
		
		GameMovableDriverDefaultImpl pacDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboardExtended(this.canvas, this.universe, hero);
		pacDriver.setStrategy(keyStr);
		pacDriver.setmoveBlockerChecker(moveBlockerChecker);
		this.canvas.addKeyListener(keyStr);
		hero.setDriver(pacDriver);
		this.universe.addGameEntity(hero);
		hero.setHeroUnit(new UnitHorseMan("Billy"));
		hero.getHeroUnit().addEquipment(new WeaponSword());
		return hero;
	}
	
	private Troll newTroll(MoveBlockerChecker moveBlockerChecker, Hero hero) {
		Troll troll;
		GameMovableDriverDefaultImpl driver = null;
		Unit unitTroll;
		driver = new GameMovableDriverDefaultImpl();
		troll = new Troll(this.canvas);
		setTrollPosition(troll, hero);
		driver.setStrategy(new MoveStrategyDynamicTarget(troll.getPosition(), hero.getTarget(), TROLLS_SPEED));
		driver.setmoveBlockerChecker(moveBlockerChecker);
		troll.setDriver(driver);
		this.universe.addGameEntity(troll);
		unitTroll = new UnitCenturion("troll " + trollsCounter++);
		troll.setTrollUnit(unitTroll);
		trolls.addUnit(unitTroll);
		System.out.println("New troll created : " + unitTroll.getName() + ".");
		return troll;
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

	private void setHeroPosition(Hero hero) {
		Point randomPoint = new Point();
		do {
			randomPoint.x = random.nextInt(this.map[0].length) * 16;
			randomPoint.y = random.nextInt(this.map.length) * 16;
			hero.setPosition(randomPoint);
		}
		while(overlapAnotherEntity(hero));
		hero.setTarget(); // nécessite que la position soit fixée
	}
	
	private void setTrollPosition(Troll troll, Hero hero) {
		Point heroPosition = hero.getPosition();
		Point randomPoint = new Point();
		do {
			randomPoint.x = random.nextInt(this.map[0].length) * 16;
			randomPoint.y = random.nextInt(this.map.length) * 16;
			troll.setPosition(randomPoint);
		}
		while(overlapAnotherEntity(troll) || randomPoint.distance(heroPosition) < 100);
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
			if(shapeIntersection(shape, shape2))
				return true;
		}
		return false;
	}

	private static boolean shapeIntersection(Shape shapeA, Shape shapeB) {
	   Area areaA = new Area(shapeA);
	   areaA.intersect(new Area(shapeB));
	   return !areaA.isEmpty();
	}
	
	private void runTrollsGenerationLoop(MoveBlockerChecker moveBlockerChecker, Hero hero) {
		int interval = TROLLS_GENERATION_INTERVAL;
		new Thread() {
			public void run() {
				while(true) {
					try {
						synchronized(this) {
							wait(interval - 100);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					newTroll(moveBlockerChecker, hero);
				}
			}	
		}.start();
	}
}