package info.reflectionsofmind.dicerobot.diceroller;

public class LimitedRandomBasedDieRollerFactory implements IDieRollerFactory
{
	private final int maximumRollCount;
	
	public LimitedRandomBasedDieRollerFactory(final int maximumRollCount)
	{
		this.maximumRollCount = maximumRollCount;
	}
	
	public synchronized IDieRoller createDieRoller()
	{
		return new LimitedRandomBasedDieRoller(this.maximumRollCount);
	}
}
