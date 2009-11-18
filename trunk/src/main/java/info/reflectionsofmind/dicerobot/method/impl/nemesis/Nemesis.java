package info.reflectionsofmind.dicerobot.method.impl.nemesis;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class Nemesis extends SimplePipelinedMethod<NemParser, NemRequest, NemRoller, NemResult, NemWriter>
{
	@Override
	protected NemParser createParser(IRollConfig config)
	{
		return new NemParser();
	}
	
	@Override
	protected NemRoller createRoller(IRollConfig config, final IDieRollerFactory factory)
	{
		return new NemRoller(factory);
	}
	
	@Override
	protected NemWriter createWriter(IRollConfig config)
	{
		return new NemWriter();
	}
	
	@Override
	public String getName()
	{
		return "Nemesis (One Roll Engine)";
	}
}
