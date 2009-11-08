package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;

public class DitvRoller extends AbstractRollRoller<DitvRequest, DitvResult>
{
	public DitvRoller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	public DitvResult makeRoll(final DitvRequest request) throws CannotMakeRollException
	{
		final IDieRoller dieRoller = createDieRoller();
		final DitvResult output = new DitvResult(request);
		
		for (final DitvRequest.Roll roll : request.getRolls())
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
