package info.reflectionsofmind.dicerobot.exception;

public class CannotParseRollException extends UserReadableException
{
	public CannotParseRollException(final String string)
	{
		super(string);
	}

	public CannotParseRollException(final String string, final Exception exception)
	{
		super(string, exception);
	}
}
