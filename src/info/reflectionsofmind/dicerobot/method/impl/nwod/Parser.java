package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.method.IRollParser;
import info.reflectionsofmind.dicerobot.method.impl.ditv.CannotParseRollException;

public final class Parser implements IRollParser<Request>
{
	public Request parse(final String input) throws CannotParseRollException
	{
		try
		{
			return new Request(Integer.parseInt(input));
		}
		catch (final NumberFormatException exception)
		{
			throw new CannotParseRollException(exception);
		}
	}
}
