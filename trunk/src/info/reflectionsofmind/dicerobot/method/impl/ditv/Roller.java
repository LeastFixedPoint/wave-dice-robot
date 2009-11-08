package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.diceroller.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;
import info.reflectionsofmind.dicerobot.method.impl.Roll;

public class Roller extends AbstractRollRoller<Request, Result>
{
	public Roller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	public Result makeRoll(final Request request) throws CannotMakeRollException
	{
		final IDieRoller dieRoller = createDieRoller();
		final Result output = new Result(request);
		
		for (final Roll roll : request.getRolls())
		{
			final int dieSize = roll.getDieSize();
			final int[] results = new int[roll.getCount()];
			
			for (int i = 0; i < roll.getCount(); i++)
				results[i] = dieRoller.roll(dieSize);
			
			output.add(dieSize, results);
		}
		
		return output;
	}
}
