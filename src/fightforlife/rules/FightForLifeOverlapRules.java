package fightforlife.rules;

import fightforlife.entities.Arrow;
import fightforlife.entities.Hero;
import fightforlife.entities.PowerPlus;
import fightforlife.entities.Troll;
import fightforlife.entities.Troll2;
import gameframework.core.GameUniverse;
import gameframework.core.ObservableValue;
import soldier.core.Unit;
import soldier.core.BehaviorSoldier;
import soldier.core.Weapon;
import soldier.weapon.WeaponShield;
import soldier.weapon.WeaponSword;
import soldier.weapon.WeaponVisitor;

public class FightForLifeOverlapRules extends OverlapRulesApplierDefaultImpl {
	private final ObservableValue<Boolean> endOfGame;
	private GameUniverse universe;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	
	public FightForLifeOverlapRules(ObservableValue<Boolean> endOfGame,ObservableValue<Integer> life,ObservableValue<Integer> score) {
		this.endOfGame = endOfGame;
		this.score=score;
		this.life=life;
	}

	@Override
	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}
	
	public void overlapRule(Hero hero, PowerPlus power) {
			this.life.setValue(this.life.getValue()+1);
			hero.getHeroUnit().addEquipment(new WeaponSword());
			this.universe.removeGameEntity(power);
	}
	
	public void overlapRule(Hero hero, Troll troll) {
		if(this.life.getValue()==0)
			this.endOfGame.setValue(true);
		else{ 
			this.life.setValue(this.life.getValue()-1);
			this.universe.removeGameEntity(troll);
		}
	}
	public void overlapRule(Hero hero, Troll2 troll) {
		if(this.life.getValue()==0)
			this.endOfGame.setValue(true);
		else{ 
			this.life.setValue(this.life.getValue()-1);
			this.universe.removeGameEntity(troll);
		}
	}
	public  void overlapRule(Arrow arrow, Troll2 troll) {
		score.setValue(score.getValue()+1);
		troll.getTrollUnit().parry(arrow.getHero().getHeroUnit().strike());
		if(troll.getTrollUnit().getHealthPoints()<=0)
			this.universe.removeGameEntity(troll);
		this.universe.removeGameEntity(arrow);
		System.out.println("life "+troll.getTrollUnit().getHealthPoints());
	}
	public void overlapRule(Arrow arrow, Troll troll) {
		Unit trollUnit = troll.getTrollUnit();

		score.setValue(score.getValue()+1);
		troll.getTrollUnit().parry(arrow.getHero().getHeroUnit().strike());
		if(troll.getTrollUnit().getHealthPoints()<=0)
			this.universe.removeGameEntity(troll);
		this.universe.removeGameEntity(arrow);
	}
}