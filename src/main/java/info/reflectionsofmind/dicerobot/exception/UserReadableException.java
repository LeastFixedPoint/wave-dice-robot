package info.reflectionsofmind.dicerobot.exception;

public abstract class UserReadableException extends DiceRobotException
{
	public UserReadableException(final String string)
	{
		super(string);
	}

	public UserReadableException(final String string, final Exception exception)
	{
		super(string, exception);
	}
}
