package fightforlife.rules;

import fightforlife.entities.Arrow;
import fightforlife.entities.Hero;
import fightforlife.entities.Troll;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

public class FightForLifeOverlapRules extends OverlapRulesApplierDefaultImpl {
	private final ObservableValue<Boolean> endOfGame;
	private GameUniverse universe;
	
	public FightForLifeOverlapRules(ObservableValue<Boolean> endOfGame) {
		this.endOfGame = endOfGame;
	}

	@Override
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}
	
	public void overlapRule(Hero hero, Troll troll) {
		this.endOfGame.setValue(true);
	}
	
	public void overlapRule(Arrow arrow, Troll troll) {
		this.universe.removeGameEntity(arrow);
		this.universe.removeGameEntity(troll);
	}
}