package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

import java.util.regex.Pattern;

public class DogsInTheVineyard implements IRollingMethod
{
	private final static Pattern PATTERN = Pattern.compile("\\d+[dк]\\d+(\\+\\d+[dк]\\d+)*");
	private final IDieRollerFactory factory;
	
	public DogsInTheVineyard(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	@Override
	public void writeResult(final String input, final IFormattedBufferedOutput output) throws CannotMakeRollException
	{
		// final DiceRoller diceRoller = new DiceRoller(this.factory.createDieRoller());
		// final List<DieRollResult> results = new ArrayList<DieRollResult>();
		// final Matcher matcher = PATTERN.matcher(input);
		//		
		// if (!matcher.matches())
		// {
		// output.append("invalid roll").with("style/color", "red");
		// return;
		// }
		//		
		// for (final String roll : input.split("\\+"))
		// {
		// final String[] pair = roll.split("d");
		// final int number = Integer.parseInt(pair[0]);
		// final int die = Integer.parseInt(pair[1]);
		// results.addAll(diceRoller.roll(number, die));
		// }
		//		
		// Collections.sort(results, DieRollResult.RESULT_COMPARATOR);
		// Collections.reverse(results);
		//		
		// for (int i = 0; i < results.size(); i++)
		// {
		// final DieRollResult result = results.get(i);
		// if (i > 0) output.append(" ");
		// output.append(result.result.toString()).with("style/fontFamily", "arial black, sans serif");
		// output.append(result.dieSize.toString()).with("style/fontSize", "0.66em");
		// }
		
		new Writer().render(output, new Roller().setDieRollerFactory(this.factory).makeRoll(new Parser().parse(input)));
	}
	
	public String getName()
	{
		return "Dogs in the Vineyard";
	}
}