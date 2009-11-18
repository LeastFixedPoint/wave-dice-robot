package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;

public class DogsInTheVineyard extends SimplePipelinedMethod<DitvParser, DitvRequest, DitvRoller, DitvResult, DitvWriter>
{
	@Override
	protected DitvParser createParser(IRollConfig config)
	{
		return new DitvParser();
	}
	
	@Override
	protected DitvRoller createRoller(IRollConfig config, final IDieRollerFactory factory)
	{
		return new DitvRoller(factory);
	}
	
	@Override
	protected DitvWriter createWriter(IRollConfig config)
	{
		return new DitvWriter();
	}
	
	public String getName()
	{
		return "Dogs in the Vineyard";
	}
}