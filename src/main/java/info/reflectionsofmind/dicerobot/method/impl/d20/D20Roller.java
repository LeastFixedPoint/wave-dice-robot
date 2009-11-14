package info.reflectionsofmind.dicerobot.method.impl.d20;

import static java.util.Collections.min;
import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRequest;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumResult;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRoller;

import java.util.ArrayList;
import java.util.List;

public class D20Roller extends AbstractRollRoller<D20Request, D20Result>
{
	public D20Roller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	public D20Result makeRoll(final D20Request request) throws CannotMakeRollException
	{
		if (request instanceof D20Request.CharGen)
		{
			final D20Result.CharGen result = new D20Result.CharGen((D20Request.CharGen) request);
			final IDieRoller roller = createDieRoller();
			
			for (int i = 0; i < 6; i++)
			{
				final List<Integer> values = new ArrayList<Integer>();
				int sum = 0;
				
				for (int j = 0; j < 4; j++)
				{
					final int d = roller.roll(6);
					values.add(d);
					sum += d;
				}
				
				result.addValues(sum - min(values));
			}
			
			return result;
		}
		
		if (request instanceof D20Request.SumAllRolls)
		{
			final SumRequest sumRequest = ((D20Request.SumAllRolls) request).getRequest();
			final SumResult sumResult = new SumRoller(getFactory()).makeRoll(sumRequest);
			return new D20Result.SumAllRolls((D20Request.SumAllRolls) request, sumResult);
		}
		
		throw new CannotMakeRollException("Unknown request " + request);
	}
}
