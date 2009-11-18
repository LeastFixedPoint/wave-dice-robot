package info.reflectionsofmind.dicerobot.method.impl.d6;

import static java.lang.Integer.parseInt;
import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.parse.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.method.IRollParser;

public class D6Parser implements IRollParser<D6Request>
{
	// public static void main(final String[] args) throws Exception
	// {
	// RT.loadResourceScript("info/reflectionsofmind/dicerobot/method/impl/d6/D6.clj");
	// final Object result = RT.var("info.reflectionsofmind.dicerobot.method.impl.d6", "parse").invoke("hello world");
	// System.out.println(result);
	// }

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
					request.addDice(parseInt(token.substring(0, token.length() - 1)));
				}
				else
				{
					request.addPips(parseInt(token));
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
