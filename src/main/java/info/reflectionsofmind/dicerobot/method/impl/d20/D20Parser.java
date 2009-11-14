package info.reflectionsofmind.dicerobot.method.impl.d20;

import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.method.IRollParser;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumParser;

public class D20Parser implements IRollParser<D20Request>
{
	public D20Request parse(final String input) throws CannotParseRollException
	{
		if (input.equals("cg"))
		{
			return new D20Request.CharGen();
		}
		else
		{
			return new D20Request.SumAllRolls(new SumParser().parse(input));
		}
	}
}
