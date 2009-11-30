package info.reflectionsofmind.dicerobot.exception;

public class DiceRobotException extends Exception
{
	public DiceRobotException(final Throwable cause)
	{
		super(cause);
	}

	public DiceRobotException(final String message)
	{
		super(message);
	}

	public DiceRobotException(final String string, final Exception exception)
	{
		super(string, exception);
	}
}
