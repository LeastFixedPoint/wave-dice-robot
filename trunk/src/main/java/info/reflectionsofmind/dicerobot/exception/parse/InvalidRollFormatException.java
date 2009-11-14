package info.reflectionsofmind.dicerobot.exception.parse;

import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;

public class InvalidRollFormatException extends CannotParseRollException
{
	public InvalidRollFormatException()
	{
		super("invalid roll format");
	}
}
