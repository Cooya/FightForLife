package fightforlife.rules;

import fightforlife.entities.Arrow;
import fightforlife.entities.Hero;
import fightforlife.entities.Troll;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;
import soldier.core.Unit;

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
		Unit trollUnit = troll.getTrollUnit();
		
		trollUnit.parry(arrow.getHero().getHeroUnit().strike());
		if(troll.getTrollUnit().getHealthPoints() <= 0)
			this.universe.removeGameEntity(troll);
		this.universe.removeGameEntity(arrow);
		System.out.println("troll touché : " + trollUnit.getName() + " " + trollUnit.getHealthPoints());
	}
}