package info.reflectionsofmind.dicerobot.method.impl.sum;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class AdditiveRoll extends SimplePipelinedMethod<SumParser, SumRequest, SumRoller, SumResult, SumWriter>
{
	@Override
	protected SumParser createParser()
	{
		return new SumParser();
	}
	
	@Override
	protected SumRoller createRoller(final IDieRollerFactory factory)
	{
		return new SumRoller(factory);
	}
	
	@Override
	protected SumWriter createWriter()
	{
		return new SumWriter();
	}
	
	public String getName()
	{
		return "Sum of all rolls";
	}
}
