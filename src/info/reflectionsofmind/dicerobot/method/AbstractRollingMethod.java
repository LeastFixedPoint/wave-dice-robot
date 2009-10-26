package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.diceroller.DiceRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractRollingMethod implements IRollingMethod
{
	private final static Pattern PATTERN = Pattern.compile("\\d+d\\d+(\\+\\d+d\\d+)*");
	private final IDieRollerFactory factory;
	
	public AbstractRollingMethod(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	public void writeResult(final String input, final IFormattedBufferedOutput inserter)
	{
		final DiceRoller diceRoller = new DiceRoller(this.factory.createDieRoller());
		final List<DiceRoller.DieRollResult> results = new ArrayList<DiceRoller.DieRollResult>();
		final Matcher matcher = PATTERN.matcher(input);
		
		if (matcher.matches())
		{
			for (final String roll : input.split("\\+"))
			{
				final String[] pair = roll.split("d");
				final int number = Integer.parseInt(pair[0]);
				final int die = Integer.parseInt(pair[1]);
				results.addAll(diceRoller.roll(number, die));
			}
			
			writeResult(results, inserter);
		}
	}
	
	public abstract void writeResult(final List<DiceRoller.DieRollResult> results, IFormattedBufferedOutput inserter);
}
