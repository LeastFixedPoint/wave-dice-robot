package info.reflectionsofmind.dicerobot.method.impl.d6;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class D6System extends SimplePipelinedMethod<D6Parser, D6Request, D6Roller, D6Result, D6Writer>
{
	@Override
	protected D6Parser createParser(IRollConfig config)
	{
		return new D6Parser();
	}
	
	@Override
	protected D6Roller createRoller(IRollConfig config, final IDieRollerFactory factory)
	{
		return new D6Roller(factory);
	}
	
	@Override
	protected D6Writer createWriter(IRollConfig config)
	{
		return new D6Writer();
	}
	
	@Override
	public String getName()
	{
		return "D6 System";
	}
}
