package info.reflectionsofmind.dicerobot.exception.output;

import info.reflectionsofmind.dicerobot.exception.DiceRobotException;

public abstract class OutputException extends DiceRobotException
{
	public OutputException()
	{
		super("Output failed");
	}
}
