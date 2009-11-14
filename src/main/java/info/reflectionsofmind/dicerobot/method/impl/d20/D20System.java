package info.reflectionsofmind.dicerobot.method.impl.d20;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class D20System extends SimplePipelinedMethod<D20Parser, D20Request, D20Roller, D20Result, D20Writer>
{
	@Override
	protected D20Parser createParser()
	{
		return new D20Parser();
	}
	
	@Override
	protected D20Roller createRoller(final IDieRollerFactory factory)
	{
		return new D20Roller(factory);
	}
	
	@Override
	protected D20Writer createWriter()
	{
		return new D20Writer();
	}
	
	@Override
	public String getName()
	{
		return "D20 System";
	}
}
