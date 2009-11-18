package info.reflectionsofmind.dicerobot.method.impl.exa;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class Exalted extends SimplePipelinedMethod<ExaParser, ExaRequest, ExaRoller, ExaResult, ExaWriter>
{
	@Override
	protected ExaParser createParser(IRollConfig config)
	{
		return new ExaParser();
	}
	
	@Override
	protected ExaRoller createRoller(IRollConfig config, final IDieRollerFactory factory)
	{
		return new ExaRoller(factory);
	}
	
	@Override
	protected ExaWriter createWriter(IRollConfig config)
	{
		return new ExaWriter();
	}
	
	@Override
	public String getName()
	{
		return "Exalted 2nd edition";
	}
}
