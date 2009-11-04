package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;
import info.reflectionsofmind.dicerobot.method.impl.RollRequest;

public class Roller extends AbstractRollRoller<Request, Result>
{
	public Result makeRoll(final Request input) throws CannotMakeRollException
	{
		final IDieRoller dieRoller = createDieRoller();
		
		final Result output = new Result();
		
		for (final RollRequest request : input.getRollRequests())
		{
			for (int i = 0; i < request.getCount(); i++)
			{
				final int dieSize = request.getDieSize();
				output.add(dieRoller.roll(dieSize), dieSize);
			}
		}
		
		return output;
	}
}
