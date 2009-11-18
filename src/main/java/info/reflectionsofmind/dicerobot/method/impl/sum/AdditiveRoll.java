package info.reflectionsofmind.dicerobot.method.impl.sum;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.impl.SimplePipelinedMethod;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumConfig.Grouping;

import java.util.Map;

public class AdditiveRoll extends SimplePipelinedMethod<SumParser, SumRequest, SumRoller, SumResult, SumWriter>
{
	public static final String SUM_GROUPING_KEY = "sumGrouping";
	
	@Override
	protected SumParser createParser(final IRollConfig config)
	{
		return new SumParser();
	}
	
	@Override
	protected SumRoller createRoller(final IRollConfig config, final IDieRollerFactory factory)
	{
		return new SumRoller(factory);
	}
	
	@Override
	protected SumWriter createWriter(final IRollConfig config)
	{
		final Grouping grouping = config != null ? ((SumConfig) config).getGrouping() : Grouping.GROUPED;
		return new SumWriter().setCollapseMode(grouping);
	}
	
	@Override
	public IRollConfig createConfig(final Map<String, String> map)
	{
		final Grouping grouping;
		
		if (map.containsKey(SUM_GROUPING_KEY))
		{
			grouping = Grouping.valueOf(map.get(SUM_GROUPING_KEY).toUpperCase());
		}
		else
		{
			grouping = Grouping.GROUPED;
		}
		
		return new SumConfig(grouping);
	}
	
	public String getName()
	{
		return "Sum of all rolls";
	}
}
