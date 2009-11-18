package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.JoiningWriter;
import info.reflectionsofmind.dicerobot.output.Style;

public class NwodWriter implements IRollWriter<NwodResult>
{
	@Override
	public void render(final IFormattedBufferedOutput output, final NwodResult result) throws CannotRenderRollException
	{
		int successes = 0;
		int botches = 0;
		
		final boolean chanceRoll = result.getRequest().getDicePool() <= 0;
		
		final JoiningWriter joiner = new JoiningWriter(output, " ");
		
		try
		{
			for (final Integer roll : result.getRolls())
			{
				successes += roll > 7 ? 1 : 0;
				botches += roll == 1 ? 1 : 0;
				
				joiner.append(roll);
				
				if (roll == 10)
					joiner.with(Style.BOLD);
				else if (roll < 8)
					joiner.with(Style.SMALL);
			}
			
			output.append(" = ");
			
			if (successes > 0)
			{
				output.append(successes).with(Style.EXTRA_BOLD).append(" ").append(successes > 1 ? "successes" : "success");
			}
			else if (chanceRoll && botches > 0 && successes == 0)
			{
				output.append("dramatic failure").with(Style.EXTRA_BOLD);
			}
			else
			{
				output.append("failure").with(Style.EXTRA_BOLD);
			}
		}
		catch (final OutputException exception)
		{
			throw CannotRenderRollException.wrap(exception);
		}
	}
}
