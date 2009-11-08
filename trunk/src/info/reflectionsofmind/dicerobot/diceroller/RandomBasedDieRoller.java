package info.reflectionsofmind.dicerobot.diceroller;

import java.util.Random;

public class RandomBasedDieRoller implements IDieRoller
{
	private final Random random = new Random();
	
	public int roll(final int dieSize) throws CannotMakeRollException
	{
		return this.random.nextInt(dieSize) + 1;
	}
}
