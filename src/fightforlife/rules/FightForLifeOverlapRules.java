package fightforlife.rules;

import fightforlife.entities.Arrow;
import fightforlife.entities.Dragon;
import fightforlife.entities.Hero;
import fightforlife.entities.PowerPlus;
import fightforlife.entities.Troll;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import gameframework.moves_rules.OverlapRulesApplierDefaultImpl;
import soldier.core.Unit;
import soldier.weapon.WeaponSword;

public class FightForLifeOverlapRules extends OverlapRulesApplierDefaultImpl {
	private GameUniverse universe;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	
	public FightForLifeOverlapRules(ObservableValue<Integer> life, ObservableValue<Integer> score) {
		this.score=score;
		this.life=life;
	}

	@Override
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}
	
	public void overlapRule(Hero hero, PowerPlus power) {
			this.life.setValue(this.life.getValue() + 1);
			hero.getHeroUnit().addEquipment(new WeaponSword());
			this.universe.removeGameEntity(power);
	}
	
	public void overlapRule(Hero hero, Troll troll) {
		this.life.setValue(this.life.getValue() - 1);
		if(this.life.getValue() < 0)
			this.life.setValue(0);
		this.universe.removeGameEntity(troll);
	}
	
	public void overlapRule(Hero hero, Dragon dragon) {
		this.life.setValue(this.life.getValue() - 2);
		if(this.life.getValue() < 0)
			this.life.setValue(0);
		this.universe.removeGameEntity(dragon);
	}
	
	public  void overlapRule(Arrow arrow, Dragon dragon) {
		score.setValue(score.getValue() + 2);
		dragon.getTrollUnit().parry(arrow.getHero().getHeroUnit().strike());
		if(dragon.getTrollUnit().getHealthPoints()<=0)
			this.universe.removeGameEntity(dragon);
		this.universe.removeGameEntity(arrow);
	}
	
	public void overlapRule(Arrow arrow, Troll troll) {
		Unit trollUnit = troll.getTrollUnit();

		score.setValue(score.getValue()+1);
		trollUnit.parry(arrow.getHero().getHeroUnit().strike());
		if(trollUnit.getHealthPoints()<=0)
			this.universe.removeGameEntity(troll);
		this.universe.removeGameEntity(arrow);
	}
}