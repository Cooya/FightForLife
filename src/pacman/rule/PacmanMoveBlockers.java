package pacman.rule;

import gameframework.moves_rules.IllegalMoveException;
import gameframework.moves_rules.MoveBlockerRulesApplierDefaultImpl;
import pacman.entity.Ghost;
import pacman.entity.Wall;

public class PacmanMoveBlockers extends MoveBlockerRulesApplierDefaultImpl {

	public void moveBlockerRule(Ghost g, Wall w) throws IllegalMoveException {
		// The default case is when a ghost is active and not able to cross a
		// wall
		if (g.isActive()) { // inactif = en cours de respawn à la prison
			throw new IllegalMoveException();
			// When a ghost is not active, it is able to cross a wall
		}
	}
}
