package info.reflectionsofmind.dicerobot.diceroller;

import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.exception.RollLimitReachedException;

public class LimitedRandomBasedDieRoller extends RandomBasedDieRoller
{
	private final int maximumRollcount;
	private int currentRollCount = 0;
	
	public LimitedRandomBasedDieRoller(final int maximumRollcount)
	{
		this.maximumRollcount = maximumRollcount;
	}
	
	@Override
	public int roll(final int dieSize) throws CannotMakeRollException
	{
		this.currentRollCount++;
		
		if (this.currentRollCount > this.maximumRollcount)
		{
			throw new RollLimitReachedException(this.maximumRollcount);
		}
		
		return super.roll(dieSize);
	}
}
