package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.method.IRollParser;

public final class NwodParser implements IRollParser<NwodRequest>
{
	public NwodRequest parse(final String input) throws CannotParseRollException
	{
		try
		{
			return new NwodRequest(Integer.parseInt(input));
		}
		catch (final NumberFormatException exception)
		{
			throw new CannotParseRollException(input + " is not a number");
		}
	}
}
