package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.RollingPipelineException;
import info.reflectionsofmind.dicerobot.method.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

public class DogsInTheVineyard implements IRollingMethod
{
	private final IDieRollerFactory factory;
	
	public DogsInTheVineyard(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	@Override
	public void writeResult(final String input, final IFormattedBufferedOutput output) throws RollingPipelineException
	{
		new Writer().render(output, new Roller(this.factory).makeRoll(new Parser().parse(input)));
	}
	
	public String getName()
	{
		return "Dogs in the Vineyard";
	}
}