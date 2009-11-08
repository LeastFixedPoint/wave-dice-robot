package info.reflectionsofmind.dicerobot.exception;

import info.reflectionsofmind.dicerobot.diceroller.CannotMakeRollException;

public class RollLimitReachedException extends CannotMakeRollException
{
	public RollLimitReachedException(final int maximumRollCount)
	{
		super("Too many rolls per request. Maximum allowed is " + maximumRollCount);
	}
}
