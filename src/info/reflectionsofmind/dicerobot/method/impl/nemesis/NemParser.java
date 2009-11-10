package info.reflectionsofmind.dicerobot.method.impl.nemesis;

import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.parse.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.method.IRollParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NemParser implements IRollParser<NemRequest>
{
	private static final Pattern GROUP = Pattern.compile("(\\d+)(d|td|)");
	
	public NemRequest parse(final String rawInput) throws CannotParseRollException
	{
		final NemRequest request = new NemRequest();
		
		for (final String token : rawInput.replaceAll("\\s", "").split("\\+"))
		{
			final Matcher matcher = GROUP.matcher(token);
			
			if (matcher.matches())
			{
				final int value = Integer.parseInt(matcher.group(1));
				final String code = matcher.group(2);
				
				if ("d".equals(code))
					request.addStandard(value);
				else if ("td".equals(code))
					request.addTrump(value);
				else
				{
					if (value < 1 || value > 10)
						throw new CannotParseRollException("expert dice must be between 1 and 10");
					
					if (request.getExpertDice().contains(value))
						throw new CannotParseRollException("expert dice must have different values");
					
					request.addExpert(value);
				}
			}
			else
			{
				throw new InvalidRollFormatException();
			}
		}
		
		return request;
	}
}
