package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public class NwodWriter implements IRollWriter<NwodResult>
{
	@Override
	public void render(final IFormattedBufferedOutput writer, final NwodResult output) throws CannotRenderRollException
	{
		int successes = 0;
		int botches = 0;
		
		final boolean chanceRoll = output.getRequest().getDicePool() <= 0;
		
		try
		{
			for (final Integer result : output.getResults())
			{
				successes += result > 7 ? 1 : 0;
				botches += result == 1 ? 1 : 0;
				
				writer.append(result).append(" ");
				
				if (result == 10)
				{
					writer.with("style/fontFamily", "arial black, sans serif");
				}
				else if (result > 7)
				{
					writer.with("style/fontSize", "0.75em");
				}
				else
				{
					writer.with("style/fontWeight", "bold");
				}
			}
			
			writer.append("= ");
			
			if (successes > 0)
			{
				writer.append(successes).append(" ").append(successes > 1 ? "successes" : "success");
			}
			else if (chanceRoll && botches > 0 && successes == 0)
			{
				writer.append("dramatic failure").with("style/fontWeight", "bold");
			}
			else
			{
				writer.append("failure");
			}
		}
		catch (final OutputException exception)
		{
			throw CannotRenderRollException.wrap(exception);
		}
	}
}
