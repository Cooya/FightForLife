package gameframework.moves_rules;

public class MoveStrategyDefaultImpl implements MoveStrategy {
	@Override
	public SpeedVector getSpeedVector() {
		return SpeedVectorDefaultImpl.createNullVector();
	}
}
