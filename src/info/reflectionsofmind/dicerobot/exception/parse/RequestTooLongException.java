package info.reflectionsofmind.dicerobot.exception.parse;

import info.reflectionsofmind.dicerobot.exception.UserReadableException;

public class RequestTooLongException extends UserReadableException
{
	public RequestTooLongException(final int length, final int maxLength)
	{
		super("request too long (max " + maxLength + ")");
	}
}
