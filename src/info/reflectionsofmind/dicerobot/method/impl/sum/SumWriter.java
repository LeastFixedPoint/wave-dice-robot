package info.reflectionsofmind.dicerobot.method.impl.sum;

import static java.lang.Math.abs;
import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public class SumWriter implements IRollWriter<SumResult>
{
	public enum CollapseMode
	{
		NONE, // 3d6+2 = 6 + 3 + 5 + 2 = 16
		GROUPS, // 3d6+2 = 14 + 2 = 16
		ALL, // 3d6+2 = 16
	}
	
	private CollapseMode collapseMode = CollapseMode.GROUPS;
	
	@Override
	public void render(final IFormattedBufferedOutput output, final SumResult result) throws CannotRenderRollException
	{
		if (result.getResults().isEmpty())
		{
			throw new CannotRenderRollException("no roll results");
		}
		
		int sum = 0;
		int writtenValues = 0;
		
		try
		{
			for (final SumRequest.Token token : result.getRequest().getTokens())
			{
				if (token instanceof SumRequest.Number)
				{
					if (this.collapseMode != CollapseMode.ALL)
						write(output, writtenValues++, ((SumRequest.Number) token).getNumber());
					
					sum += ((SumRequest.Number) token).getNumber();
				}
				else if (token instanceof SumRequest.Roll)
				{
					int groupSum = 0;
					
					for (final Integer value : result.getResults((SumRequest.Roll) token))
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
					throw new CannotRenderRollException("Invalid token " + token);
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
		catch (final OutputException exception)
		{
			throw CannotRenderRollException.wrap(exception);
		}
	}
	
	private void write(final IFormattedBufferedOutput output, final int index, final int number) throws OutputException
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
	
	public SumWriter setCollapseMode(final CollapseMode collapseMode)
	{
		this.collapseMode = collapseMode;
		return this;
	}
}
