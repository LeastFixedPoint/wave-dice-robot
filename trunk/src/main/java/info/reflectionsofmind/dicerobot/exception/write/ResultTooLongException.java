package info.reflectionsofmind.dicerobot.exception.write;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;

public class ResultTooLongException extends CannotRenderRollException
{
	public ResultTooLongException(final int maxLength)
	{
		super("result too long (max " + maxLength + ")");
	}
}
