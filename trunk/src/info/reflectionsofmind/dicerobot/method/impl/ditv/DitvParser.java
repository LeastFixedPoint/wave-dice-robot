package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.parse.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.method.IRollParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DitvParser implements IRollParser<DitvRequest>
{
	private final static Pattern PATTERN = Pattern.compile("\\d+[dк]\\d+(\\+\\d+[dк]\\d+)*");
	
	public DitvRequest parse(final String rawText) throws CannotParseRollException
	{
		final String text = rawText.replaceAll("\\s", "");
		final Matcher matcher = PATTERN.matcher(text);
		
		if (!matcher.matches())
			throw new InvalidRollFormatException();
		
		final DitvRequest input = new DitvRequest();
		
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
