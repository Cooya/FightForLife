package fightforlife.rules;

import fightforlife.entities.Arrow;
import fightforlife.entities.Tree;
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
}