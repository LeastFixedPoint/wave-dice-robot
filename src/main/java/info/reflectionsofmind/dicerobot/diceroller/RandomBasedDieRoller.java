package info.reflectionsofmind.dicerobot.diceroller;

import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;

import java.util.Random;

public class RandomBasedDieRoller implements IDieRoller
{
	private final Random random = new Random();
	
	public Integer roll(final Integer dieSize) throws CannotMakeRollException
	{
		return this.random.nextInt(dieSize) + 1;
	}
}
