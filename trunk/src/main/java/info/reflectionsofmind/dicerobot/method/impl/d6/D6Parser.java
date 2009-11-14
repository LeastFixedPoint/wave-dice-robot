package info.reflectionsofmind.dicerobot.method.impl.d6;

import static java.lang.Integer.parseInt;
import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.parse.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.method.IRollParser;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Die;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Pip;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Wild;

public class D6Parser implements IRollParser<D6Request>
{
	@Override
	public D6Request parse(final String input) throws CannotParseRollException
	{
		final String[] tokens = input.split("\\+");
		final D6Request request = new D6Request();
		
		for (final String token : tokens)
		{
			try
			{
				if (token.toLowerCase().endsWith("d"))
				{
					request.add(new Die(parseInt(token.substring(0, token.length() - 1))));
				}
				else if (token.toLowerCase().equals("w"))
				{
					request.add(new Wild());
				}
				else
				{
					request.add(new Pip(parseInt(token)));
				}
			}
			catch (final NumberFormatException exception)
			{
				throw new InvalidRollFormatException();
			}
		}
		
		return request;
	}
}
