package info.reflectionsofmind.dicerobot.diceroller;

import info.reflectionsofmind.dicerobot.exception.RollingPipelineException;

public class CannotMakeRollException extends RollingPipelineException
{
	public CannotMakeRollException(final String string)
	{
		super(string);
	}
}
