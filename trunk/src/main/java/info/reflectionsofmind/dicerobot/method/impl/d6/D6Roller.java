package info.reflectionsofmind.dicerobot.method.impl.d6;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRollRoller2;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Die;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Token;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Wild;

public class D6Roller extends AbstractRollRoller2<D6Request, D6Result>
{
	public D6Roller()
	{
		super(D6Request.class, D6Result.class);
	}
	
	@Override
	protected void doMakeRoll(final D6Request request, final D6Result result, final IDieRoller roller) throws CannotMakeRollException
	{
		for (final Token token : request.getTokens())
		{
			if (token instanceof Die)
			{
				for (int i = 0; i < token.getValue(); i++)
					result.add((Die) token, roller.roll(6));
			}
			else if (token instanceof Wild)
			{
				int roll = roller.roll(6);
				result.add((Wild) token, roll);
				
				if (roll == 1)
				{
					final int nextRoll = roller.roll(6);
					result.add((Wild) token, nextRoll);
				}
				
				while (roll == 6)
				{
					roll = roller.roll(6);
					result.add((Wild) token, roll);
				}
			}
		}
	}
}
