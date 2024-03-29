package info.reflectionsofmind.dicerobot.method.impl.exa;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaResult.Group;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.IStyle;
import info.reflectionsofmind.dicerobot.output.JoiningWriter;
import info.reflectionsofmind.dicerobot.output.Style;

public class ExaWriter implements IRollWriter<ExaResult>
{
	public void render(final IFormattedBufferedOutput output, final ExaResult result) throws CannotRenderRollException, OutputException
	{
		final JoiningWriter joiner = new JoiningWriter(output, " ");
		
		for (final Group group : result.getGroups())
		{
			for (final Integer roll : group.getRolls())
			{
				final IStyle style = (roll >= group.getTargetNumber()) ? Style.GREEN : Style.RED;
				joiner.append(roll).with(style);
			}
		}
		
		output.append(" = ");
		
		int successes = 0;
		int ones = 0;
		
		for (final Group group : result.getGroups())
		{
			for (final Integer roll : group.getRolls())
			{
				if (roll >= group.getTargetNumber())
				{
					++successes;
					
					if (roll == 10 && result.getRequest().isTensExplosive())
						++successes;
				}
				
				if (roll == 1) ++ones;
			}
		}
		
		if (successes == result.getRequest().getDifficulty())
		{
			output.append("success").with(Style.EXTRA_BOLD).with(Style.GREEN);
		}
		else if (successes > result.getRequest().getDifficulty())
		{
			final int threshold = successes - result.getRequest().getDifficulty();
			output.append("success +" + threshold).with(Style.EXTRA_BOLD).with(Style.GREEN);
		}
		else if (successes > 0 || ones == 0)
		{
			final int threshold = result.getRequest().getDifficulty() - successes;
			output.append("failure -" + threshold).with(Style.EXTRA_BOLD);
		}
		else
		{
			output.append("BOTCH").with(Style.EXTRA_BOLD).with(Style.RED);
		}
	}
}
