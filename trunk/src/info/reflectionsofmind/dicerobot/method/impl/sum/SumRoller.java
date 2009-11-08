package info.reflectionsofmind.dicerobot.method.impl.sum;

import static java.lang.Math.abs;
import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;

public class SumRoller extends AbstractRollRoller<SumRequest, SumResult>
{
	public SumRoller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	@Override
	public SumResult makeRoll(final SumRequest request) throws CannotMakeRollException
	{
		final IDieRoller roller = createDieRoller();
		final SumResult result = new SumResult(request);
		
		for (final SumRequest.Token token : request.getTokens())
		{
			if (token instanceof SumRequest.Roll)
			{
				final int count = ((SumRequest.Roll) token).getCount();
				final int dieSize = ((SumRequest.Roll) token).getDieSize();
				final int sign = count > 0 ? +1 : -1;
				
				final Integer[] results = new Integer[abs(count)];
				
				for (int i = 0; i < abs(count); i++)
				{
					results[i] = sign * roller.roll(dieSize);
				}
				
				result.add((SumRequest.Roll) token, results);
			}
			else if (token instanceof SumRequest.Number)
			{
				// Do nothing
			}
			else if (token == null)
			{
				throw new CannotMakeRollException("unknown error");
			}
			else
			{
				throw new CannotMakeRollException("unknown error");
			}
		}
		
		return result;
	}
}
