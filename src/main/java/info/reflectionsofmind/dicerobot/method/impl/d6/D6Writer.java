package info.reflectionsofmind.dicerobot.method.impl.d6;

import static java.util.Arrays.asList;
import static java.util.Collections.max;
import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.JoiningWriter;
import info.reflectionsofmind.dicerobot.output.Style;

public class D6Writer implements IRollWriter<D6Result>
{
	@Override
	public void render(final IFormattedBufferedOutput output, final D6Result result) throws CannotRenderRollException, OutputException
	{
		final JoiningWriter joiner = new JoiningWriter(output, " + ");
		
		final int sum = writeDice(result, joiner) + writeWild(result, joiner) + writePips(result, joiner);
		
		output.append(" = ");
		
		writeResult(output, result, sum);
	}
	
	private void writeResult(final IFormattedBufferedOutput output, final D6Result result, final int sum) throws OutputException
	{
		if (asList(1, 1).equals(result.getWildRolls()))
		{
			output.append("critical failure").with(Style.EXTRA_BOLD).with(Style.RED);
		}
		else
		{
			output.append(sum).with(Style.EXTRA_BOLD);
		}
	}
	
	private int writePips(final D6Result result, final JoiningWriter joiner) throws OutputException
	{
		int sum = 0;
		
		for (final Integer pip : result.getRequest().getPips())
		{
			joiner.append(pip);
			sum += pip;
		}
		
		return sum;
	}
	
	private int writeWild(final D6Result result, final JoiningWriter joiner) throws OutputException
	{
		int sum = 0;
		
		if (result.getWildRolls().get(0) == 1)
		{
			joiner.append(result.getWildRolls().get(0)).with(Style.EXTRA_BOLD);
			joiner.append(result.getWildRolls().get(1)).with(Style.EXTRA_BOLD).with(Style.STRIKEOUT).with(Style.RED);
			sum += 1;
		}
		else
		{
			for (final Integer roll : result.getWildRolls())
			{
				joiner.append(roll).with(Style.EXTRA_BOLD);
				sum += roll;
			}
		}
		return sum;
	}
	
	private int writeDice(final D6Result result, final JoiningWriter joiner) throws OutputException
	{
		final int removedIndex = getRemovedDieIndex(result);
		int sum = 0;
		
		for (int i = 0; i < result.getDiceRolls().size(); i++)
		{
			final Integer roll = result.getDiceRolls().get(i);
			joiner.append(roll);
			
			if (i == removedIndex)
				joiner.with(Style.STRIKEOUT).with(Style.RED);
			else
				sum += roll;
		}
		
		return sum;
	}
	
	private int getRemovedDieIndex(final D6Result result)
	{
		final int removedIndex;
		if (result.getWildRolls().get(0) == 1)
		{
			removedIndex = result.getDiceRolls().indexOf(max(result.getDiceRolls()));
		}
		else
		{
			removedIndex = -1;
		}
		return removedIndex;
	}
}
