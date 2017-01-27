package fightforlife.rules;

import java.awt.Point;

import fightforlife.entities.Arrow;
import fightforlife.entities.Tree;
import fightforlife.entities.Troll;
import gameframework.core.GameUniverse;
import gameframework.moves_rules.IllegalMoveException;
import gameframework.moves_rules.MoveBlockerRulesApplierDefaultImpl;

public class FightForLifeMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {
	private GameUniverse universe;
	
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	public void moveBlockerRule(Arrow arrow, Tree tree) throws IllegalMoveException {
		this.universe.removeGameEntity(arrow);
		throw new IllegalMoveException();
	}
	
	public void moveBlockerRule(Troll troll, Tree tree) throws IllegalMoveException {
		Point direction = troll.getSpeedVector().getDirection();
		troll.getSpeedVector().setDirection(new Point(-direction.x, -direction.y));
		throw new IllegalMoveException();
	}
}