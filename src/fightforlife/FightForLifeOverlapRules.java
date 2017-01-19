package fightforlife;

import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;

public class FightForLifeOverlapRules extends OverlapRulesApplierDefaultImpl {
	private final ObservableValue<Boolean> endOfGame;
	//private GameUniverse universe;
	
	public FightForLifeOverlapRules(ObservableValue<Boolean> endOfGame) {
		this.endOfGame = endOfGame;
	}

	@Override
	public void setUniverse(GameUniverse universe) {
		//this.universe = universe;
	}
	
	public void overlapRule(Hero hero, Troll troll) {
		endOfGame.setValue(true);
	}
}