package fightforlife.rules;

import java.util.Iterator;

import com.sun.xml.internal.ws.client.sei.ResponseBuilder.Composite;

import fightforlife.FightForLifeGameLevel;
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
		boolean inComposite=false;
		troll.getTrollUnit().parry(arrow.getHero().getHeroUnit().strike());
		Iterator<Unit> it=FightForLifeGameLevel.composite.subUnits();
		while(it.hasNext())
			if(it.next().equals(troll)){
				inComposite=true;
				break;
			}
		if(inComposite=true)
			FightForLifeGameLevel.composite.parry(arrow.getHero().getHeroUnit().strike());

		if(troll.getTrollUnit().getHealthPoints()<=0){
			this.universe.removeGameEntity(troll);
			if(inComposite=true)
				FightForLifeGameLevel.composite.removeUnit((Unit)troll);
		}
		this.universe.removeGameEntity(arrow);
	}
}