package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.exception.InvalidRollFormatException;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

public class NewWorldOfDarkness implements IRollingMethod
{
	private static final int TARGET_NUMBER = 8;
	private final IDieRollerFactory factory;
	
	public NewWorldOfDarkness(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	@Override
	public void writeResult(final String input, final IFormattedBufferedOutput output) throws CannotMakeRollException
	{
		// TODO Rewrite this completely
		
		final int number;
		
		try
		{
			number = Integer.parseInt(input);
		}
		catch (final NumberFormatException exception)
		{
			throw new InvalidRollFormatException();
		}
		
		final boolean chanceRoll = (number <= 0);
		boolean dramaticFailure = false;
		
		final IDieRoller roller = this.factory.createDieRoller();
		int successes = 0;
		
		for (int i = 0; i < (chanceRoll ? 1 : number); i++)
		{
			while (true)
			{
				final int result = roller.roll(10);
				
				if (result == 10)
				{
					output.append(result).with("style/fontFamily", "arial black, sans serif").append(" ");
					++successes;
				}
				else if (result >= TARGET_NUMBER)
				{
					output.append(result).append(" ").with("style/fontWeight", "bold");
					++successes;
					break;
				}
				else if (chanceRoll && result == 1)
				{
					dramaticFailure = true;
					output.append(result).with("style/fontSize", "0.75em").append(" ");
					break;
				}
				else
				{
					output.append(result).with("style/fontSize", "0.75em").append(" ");
					break;
				}
			}
		}
		
		output.append("= ");
		
		if (successes > 0)
		{
			output.append(successes).append(" ").append(getSuccessForm(successes));
		}
		else if (dramaticFailure)
		{
			output.append("dramatic failure").with("style/textWeight", "bold");
		}
		else
		{
			output.append("failure");
		}
	}
	
	public static String getSuccessForm(final int successes)
	{
		return successes == 1 ? "success" : "successes";
	}
	
	@Override
	public String getName()
	{
		return "New World of Darkness";
	}
}
