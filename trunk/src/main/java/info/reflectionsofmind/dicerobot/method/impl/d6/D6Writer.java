package info.reflectionsofmind.dicerobot.method.impl.d6;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Token;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public class D6Writer implements IRollWriter<D6Result>
{
	@Override
	public void render(final IFormattedBufferedOutput output, final D6Result result) throws CannotRenderRollException, OutputException
	{
		boolean first = true;
		for (final Token token : result.getRequest().getTokens())
		{
			if (!first)
				output.append(" + ");
			else
				first = false;
			
			token.render(output, result);
		}
	}
}
