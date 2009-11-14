package info.reflectionsofmind.dicerobot.method.impl.exa;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;
import info.reflectionsofmind.dicerobot.method.impl.exa.ExaRequest.Group;

public class ExaRoller extends AbstractRollRoller<ExaRequest, ExaResult>
{
	public ExaRoller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	public ExaResult makeRoll(final ExaRequest request) throws CannotMakeRollException
	{
		final ExaResult result = new ExaResult(request);
		final IDieRoller roller = createDieRoller();
		
		for (final Group group : request.getGroups())
		{
			final Integer[] rolls = new Integer[group.getNumberOfDice()];
			
			for (int i = 0; i < group.getNumberOfDice(); ++i)
				rolls[i] = roller.roll(10);
			
			result.add(group.getTargetNumber(), rolls);
		}
		
		return result;
	}
}
