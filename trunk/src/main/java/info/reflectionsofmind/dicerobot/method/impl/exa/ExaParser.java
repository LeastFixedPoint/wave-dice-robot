package info.reflectionsofmind.dicerobot.method.impl.exa;

import static java.lang.Integer.parseInt;
import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.parse.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.method.IRollParser;

import java.util.regex.Pattern;

public class ExaParser implements IRollParser<ExaRequest>
{
	
	private static final Pattern TOKEN = Pattern.compile("\\d+((tn|/)\\d+)?");
	private static final Pattern PATTERN = Pattern.compile("m?(" + TOKEN + ")(\\+" + TOKEN + ")*(vs\\d+)?");
	
	public ExaRequest parse(final String rawInput) throws CannotParseRollException
	{
		String input = rawInput.replaceAll("\\s", "");
		
		if (!PATTERN.matcher(input).matches())
			throw new InvalidRollFormatException();
		
		final ExaRequest request = new ExaRequest();
		
		if (input.startsWith("m"))
		{
			request.explodeTens(false);
			input = input.substring(1);
		}
		
		if (input.contains("vs"))
		{
			final int start = input.indexOf("vs");
			final int difficulty = Integer.parseInt(input.substring(start + 2));
			request.difficulty(difficulty);
			input = input.substring(0, start);
		}
		
		for (final String token : input.split("\\+"))
		{
			final String[] pair = token.split("tn|/");
			
			if (pair.length > 1)
				request.add(parseInt(pair[0]), parseInt(pair[1]));
			else
				request.add(parseInt(pair[0]));
		}
		
		return request;
	}
}
