package info.reflectionsofmind.dicerobot.method.impl.sum;

import static java.lang.Math.abs;
import info.reflectionsofmind.dicerobot.diceroller.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;

public class Roller extends AbstractRollRoller<Request, Result>
{
	public Roller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	@Override
	public Result makeRoll(final Request request) throws CannotMakeRollException
	{
		final IDieRoller roller = createDieRoller();
		final Result result = new Result(request);
		
		for (final Request.Token token : request.getTokens())
		{
			if (token instanceof Request.Roll)
			{
				final int count = ((Request.Roll) token).getCount();
				final int dieSize = ((Request.Roll) token).getDieSize();
				final int sign = count > 0 ? +1 : -1;
				
				final Integer[] results = new Integer[abs(count)];
				
				for (int i = 0; i < abs(count); i++)
				{
					results[i] = sign * roller.roll(dieSize);
				}
				
				result.add((Request.Roll) token, results);
			}
			else if (token instanceof Request.Number)
			{
				// Do nothing
			}
			else if (token == null)
			{
				throw new CannotMakeRollException("Token is null");
			}
			else
			{
				throw new CannotMakeRollException("Token " + token + " had invalid class " + token.getClass());
			}
		}
		
		return result;
	}
}
