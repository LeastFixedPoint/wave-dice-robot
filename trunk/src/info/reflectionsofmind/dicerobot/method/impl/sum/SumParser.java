package info.reflectionsofmind.dicerobot.method.impl.sum;

import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.parse.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.method.IRollParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SumParser implements IRollParser<SumRequest>
{
	private final static Pattern ROLL = Pattern.compile("\\d*d\\d+");
	private final static Pattern NUMBER = Pattern.compile("\\d+");
	
	public static List<String> split(final String string, final Pattern pattern)
	{
		final Matcher matcher = pattern.matcher(string);
		final List<String> strings = new ArrayList<String>();
		
		int position = 0;
		while (matcher.find())
		{
			if (matcher.start() > 0)
				strings.add(string.substring(position, matcher.start()));
			
			position = matcher.start();
		}
		
		strings.add(string.substring(position));
		
		return strings;
	}
	
	@Override
	public SumRequest parse(final String input) throws CannotParseRollException
	{
		final List<String> tokens = split(input.replaceAll("\\s", ""), Pattern.compile("[-+]"));
		final SumRequest request = new SumRequest();
		
		for (final String signed : tokens)
		{
			final String token;
			final int multiplier;
			
			if (signed.charAt(0) == '-')
			{
				multiplier = -1;
				token = signed.substring(1);
			}
			else if (signed.charAt(0) == '+')
			{
				multiplier = +1;
				token = signed.substring(1);
			}
			else
			{
				multiplier = +1;
				token = signed;
			}
			
			if (NUMBER.matcher(token).matches())
			{
				request.add(multiplier * Integer.parseInt(token));
			}
			else if (ROLL.matcher(token).matches())
			{
				final int pos = token.indexOf('d');
				final int count = (pos == 0) ? 1 : Integer.parseInt(token.substring(0, pos));
				final int dieSize = Integer.parseInt(token.substring(pos + 1));
				request.add(multiplier * count, dieSize);
			}
			else
			{
				throw new InvalidRollFormatException();
			}
		}
		
		return request;
	}
}
