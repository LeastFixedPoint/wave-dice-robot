package info.reflectionsofmind.dicerobot.method.impl.sum;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.RollingPipelineException;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

public class AdditiveRoll implements IRollingMethod
{
	private final IDieRollerFactory factory;
	
	public AdditiveRoll(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	public void writeResult(final String input, final IFormattedBufferedOutput output) throws RollingPipelineException
	{
		new Writer().render(output, new Roller(this.factory).makeRoll(new Parser().parse(input)));
	}
	
	public String getName()
	{
		return "Sum of all rolls";
	}
}
