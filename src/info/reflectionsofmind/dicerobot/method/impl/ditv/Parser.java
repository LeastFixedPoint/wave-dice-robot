package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.method.IRollParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements IRollParser<Request>
{
	private final static Pattern PATTERN = Pattern.compile("\\d+[dк]\\d+(\\+\\d+[dк]\\d+)*");
	
	public Request parse(final String text) throws CannotParseRollException
	{
		final Matcher matcher = PATTERN.matcher(text);
		
		if (!matcher.matches())
			throw new CannotParseRollException();
		
		final Request input = new Request();
		
		for (final String roll : text.split("\\+"))
		{
			final String[] pair = roll.split("d");
			final int number = Integer.parseInt(pair[0]);
			final int die = Integer.parseInt(pair[1]);
			input.add(number, die);
		}
		
		return input;
	}
}
