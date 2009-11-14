package info.reflectionsofmind.dicerobot.exception;

public class MethodNotFoundException extends UserReadableException
{
	public MethodNotFoundException(final String method)
	{
		super("invalid method (" + method + ")");
	}
}
