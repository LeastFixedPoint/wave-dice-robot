package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller;

import java.util.ArrayList;
import java.util.List;

public class NwodRoller extends AbstractRollRoller<NwodRequest, NwodResult>
{
	private static final int DIE_SIZE = 10;
	
	public NwodRoller(final IDieRollerFactory factory)
	{
		super(factory);
	}
	
	public NwodResult makeRoll(final NwodRequest request) throws CannotMakeRollException
	{
		final Roll roll = (request.getDicePool() > 0) ? new NormalRoll(request.getDicePool()) : new ChanceRoll();
		return new NwodResult(request, roll.make());
	}
	
	private abstract class Roll
	{
		public abstract List<Integer> make() throws CannotMakeRollException;
	}
	
	private class NormalRoll extends Roll
	{
		private final int dicePool;
		
		public NormalRoll(final int dicePool)
		{
			this.dicePool = dicePool;
		}
		
		@Override
		public List<Integer> make() throws CannotMakeRollException
		{
			final IDieRoller roller = createDieRoller();
			final List<Integer> results = new ArrayList<Integer>();
			
			for (int i = 0; i < this.dicePool; ++i)
			{
				while (true)
				{
					final int result = roller.roll(DIE_SIZE);
					results.add(result);
					if (result < 10) break;
				}
			}
			
			return results;
		}
	}
	
	private class ChanceRoll extends Roll
	{
		@Override
		public List<Integer> make() throws CannotMakeRollException
		{
			final IDieRoller roller = createDieRoller();
			final List<Integer> results = new ArrayList<Integer>();
			
			while (true)
			{
				final int result = roller.roll(DIE_SIZE);
				results.add(result);
				if (result < 10) break;
			}
			
			return results;
		}
	}
}
