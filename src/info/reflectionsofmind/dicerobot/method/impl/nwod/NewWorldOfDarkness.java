package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.RollingPipelineException;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

public class NewWorldOfDarkness implements IRollingMethod
{
	private final IDieRollerFactory factory;
	
	public NewWorldOfDarkness(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	@Override
	public void writeResult(final String input, final IFormattedBufferedOutput output) throws RollingPipelineException
	{
		new Writer().render(output, new Roller(this.factory).makeRoll(new Parser().parse(input)));
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
