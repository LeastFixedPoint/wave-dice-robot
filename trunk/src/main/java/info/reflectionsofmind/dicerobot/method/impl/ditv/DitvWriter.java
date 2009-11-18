package info.reflectionsofmind.dicerobot.method.impl.ditv;

import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;
import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.method.impl.RollResult;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.Style;

import java.util.ArrayList;
import java.util.List;

public class DitvWriter implements IRollWriter<DitvResult>
{
	@Override
	public void render(final IFormattedBufferedOutput writer, final DitvResult output) throws CannotRenderRollException
	{
		final List<RollResult> results = new ArrayList<RollResult>(output.getResults());
		sort(results, reverseOrder(RollResult.RESULT_COMPARATOR));
		
		try
		{
			for (int i = 0; i < results.size(); i++)
			{
				final RollResult result = results.get(i);
				if (i > 0) writer.append(" ");
				writer.append(result.getResult()).with(Style.EXTRA_BOLD);
				writer.append(result.getDieSize()).with(Style.EXTRA_SMALL);
			}
		}
		catch (final OutputException exception)
		{
			throw CannotRenderRollException.wrap(exception);
		}
	}
}
