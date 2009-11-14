package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.method.IRollParser;

public final class NwodParser implements IRollParser<NwodRequest>
{
	public NwodRequest parse(final String input) throws CannotParseRollException
	{
		try
		{
			final int count = Integer.parseInt(input);
			return new NwodRequest(count);
		}
		catch (final NumberFormatException exception)
		{
			throw new CannotParseRollException(input + " is not a number");
		}
	}
}
