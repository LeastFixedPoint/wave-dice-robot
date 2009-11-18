package info.reflectionsofmind.dicerobot.method.impl.d6;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;

public class D6Roller extends AbstractRollRoller<D6Request, D6Result>
{
	public D6Roller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	@Override
	public D6Result makeRoll(final D6Request request) throws CannotMakeRollException
	{
		final D6Result result = new D6Result(request);
		final IDieRoller roller = createDieRoller();
		
		for (int i = 0; i < result.getRequest().getNumberOfDice() - 1; ++i)
			result.addDice(roller.roll(6));
		
		int roll = roller.roll(6);
		result.addWild(roll);
		
		if (roll == 1)
		{
			result.addWild(roller.roll(6));
		}
		
		while (roll == 6)
		{
			roll = roller.roll(6);
			result.addWild(roll);
		}
		
		return result;
	}
}
