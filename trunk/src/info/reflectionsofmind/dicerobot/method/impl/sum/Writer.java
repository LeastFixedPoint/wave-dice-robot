package info.reflectionsofmind.dicerobot.method.impl.sum;

import static java.lang.Math.abs;
import info.reflectionsofmind.dicerobot.method.CannotWriteRollException;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollWriter;

public class Writer implements IRollWriter<Result>
{
	public enum CollapseMode
	{
		NONE, // 3d6+2 = 6 + 3 + 5 + 2 = 16
		GROUPS, // 3d6+2 = 14 + 2 = 16
		ALL, // 3d6+2 = 16
	}
	
	private CollapseMode collapseMode = CollapseMode.GROUPS;
	
	@Override
	public void render(final IFormattedBufferedOutput output, final Result result) throws CannotWriteRollException
	{
		if (result.getResults().isEmpty())
		{
			throw new CannotWriteRollException("No results");
		}
		
		int sum = 0;
		int writtenValues = 0;
		
		for (final Request.Token token : result.getRequest().getTokens())
		{
			if (token instanceof Request.Number)
			{
				if (this.collapseMode != CollapseMode.ALL)
					write(output, writtenValues++, ((Request.Number) token).getNumber());
				
				sum += ((Request.Number) token).getNumber();
			}
			else if (token instanceof Request.Roll)
			{
				int groupSum = 0;
				
				for (final Integer value : result.getResults((Request.Roll) token))
				{
					if (this.collapseMode == CollapseMode.NONE)
						write(output, writtenValues++, value);
					
					groupSum += value;
				}
				
				if (this.collapseMode == CollapseMode.GROUPS)
					write(output, writtenValues++, groupSum);
				
				sum += groupSum;
			}
			else
			{
				throw new CannotWriteRollException("Invalid token " + token);
			}
		}
		
		if (this.collapseMode == CollapseMode.ALL)
		{
			output.append(sum);
			return;
		}
		else if (writtenValues > 1)
		{
			output.append(" = ").append(sum);
		}
	}
	
	private void write(final IFormattedBufferedOutput output, final int index, final int number)
	{
		if (index > 0)
		{
			output.append(number >= 0 ? " + " : " - ").append(abs(number));
		}
		else if (index == 0 && number < 0)
		{
			output.append("-").append(abs(number));
		}
		else
		{
			output.append(number); // index == 0 && number > 0
		}
	}
	
	public Writer setCollapseMode(final CollapseMode collapseMode)
	{
		this.collapseMode = collapseMode;
		return this;
	}
}
