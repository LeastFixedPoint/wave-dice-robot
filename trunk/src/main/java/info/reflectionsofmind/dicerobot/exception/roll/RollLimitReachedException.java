package info.reflectionsofmind.dicerobot.exception.roll;

import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;

public class RollLimitReachedException extends CannotMakeRollException
{
	public RollLimitReachedException(final int maximumRollCount)
	{
		super("too many rolls (max " + maximumRollCount + ")");
	}
}
