package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class NewWorldOfDarkness extends SimplePipelinedMethod<NwodParser, NwodRequest, NwodRoller, NwodResult, NwodWriter>
{
	@Override
	protected NwodParser createParser()
	{
		return new NwodParser();
	}
	
	@Override
	protected NwodRoller createRoller(final IDieRollerFactory factory)
	{
		return new NwodRoller(factory);
	}
	
	@Override
	protected NwodWriter createWriter()
	{
		return new NwodWriter();
	}
	
	@Override
	public String getName()
	{
		return "New World of Darkness";
	}
}
