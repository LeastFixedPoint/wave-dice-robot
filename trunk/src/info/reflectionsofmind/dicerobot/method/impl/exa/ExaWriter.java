package info.reflectionsofmind.dicerobot.method.impl.exa;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaResult.Group;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public class ExaWriter implements IRollWriter<ExaResult>
{
	public void render(final IFormattedBufferedOutput output, final ExaResult result) throws CannotRenderRollException, OutputException
	{
		for (final Group group : result.getGroups())
		{
			for (final Integer roll : group.getRolls())
			{
				final String color = (roll >= group.getTargetNumber()) ? "green" : "red";
				output.append(roll).with("style/color", color).append(" ");
			}
		}
		
		output.append("= ");
		
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
			output.append("success").with("style/fontFamily", "arial black, sans serif").with("style/color", "green");
		}
		else if (successes > result.getRequest().getDifficulty())
		{
			final int threshold = successes - result.getRequest().getDifficulty();
			output.append("success +" + threshold).with("style/fontFamily", "arial black, sans serif").with("style/color", "green");
		}
		else if (successes > 0 || ones == 0)
		{
			final int threshold = result.getRequest().getDifficulty() - successes;
			output.append("failure -" + threshold).with("style/fontFamily", "arial black, sans serif").with("style/color", "black");
		}
		else
		{
			output.append("BOTCH").with("style/fontFamily", "arial black, sans serif").with("style/color", "red");
		}
	}
}
