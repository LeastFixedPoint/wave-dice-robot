package info.reflectionsofmind.dicerobot.method.impl.nemesis;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;

public class NemRoller extends AbstractRollRoller<NemRequest, NemResult>
{
	public NemRoller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	public NemResult makeRoll(final NemRequest request) throws CannotMakeRollException
	{
		final NemResult result = new NemResult(request);
		final IDieRoller roller = createDieRoller();
		
		for (int i = 0; i < request.getStandardDice(); ++i)
			result.add(roller.roll(10));
		
		return result;
	}
}
