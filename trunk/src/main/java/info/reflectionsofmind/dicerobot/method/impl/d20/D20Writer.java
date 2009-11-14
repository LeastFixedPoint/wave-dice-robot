package info.reflectionsofmind.dicerobot.method.impl.d20;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;

public class D20Writer implements IRollWriter<D20Result>
{
	@SuppressWarnings("deprecation")
	public void render(final IFormattedBufferedOutput output, final D20Result result) throws CannotRenderRollException, OutputException
	{
		if (result instanceof D20Result.CharGen)
		{
			output.append(StringUtil.join(((D20Result.CharGen) result).getValues(), ", "));
		}
		else if (result instanceof D20Result.SumAllRolls)
		{
			new SumWriter().render(output, ((D20Result.SumAllRolls) result).getResult());
		}
		else
		{
			throw new CannotRenderRollException("Invalid roll result " + result);
		}
	}
}
