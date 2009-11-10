package info.reflectionsofmind.dicerobot.method.impl.exa;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class Exalted extends SimplePipelinedMethod<ExaParser, ExaRequest, ExaRoller, ExaResult, ExaWriter>
{
	@Override
	protected ExaParser createParser()
	{
		return new ExaParser();
	}
	
	@Override
	protected ExaRoller createRoller(final IDieRollerFactory factory)
	{
		return new ExaRoller(factory);
	}
	
	@Override
	protected ExaWriter createWriter()
	{
		return new ExaWriter();
	}
	
	@Override
	public String getName()
	{
		return "Exalted 2nd edition";
	}
}
