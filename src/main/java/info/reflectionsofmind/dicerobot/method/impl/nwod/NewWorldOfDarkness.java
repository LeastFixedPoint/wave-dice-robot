package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class NewWorldOfDarkness extends SimplePipelinedMethod<NwodParser, NwodRequest, NwodRoller, NwodResult, NwodWriter>
{
	@Override
	protected NwodParser createParser(IRollConfig config)
	{
		return new NwodParser();
	}
	
	@Override
	protected NwodRoller createRoller(IRollConfig config, final IDieRollerFactory factory)
	{
		return new NwodRoller(factory);
	}
	
	@Override
	protected NwodWriter createWriter(IRollConfig config)
	{
		return new NwodWriter();
	}
	
	@Override
	public String getName()
	{
		return "New World of Darkness";
	}
}
