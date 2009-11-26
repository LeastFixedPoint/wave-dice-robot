package info.reflectionsofmind.dicerobot.method.impl.sum;

import info.reflectionsofmind.dicerobot.method.IRollConfig;

public class SumConfig implements IRollConfig
{
	public enum Grouping
	{
		EXPANDED, // 3d6+2 = 6 + 3 + 5 + 2 = 16
		GROUPED, // 3d6+2 = 14 + 2 = 16
		RESULT, // 3d6+2 = 16
	}
	
	private final Grouping grouping;
	
	public SumConfig(final Grouping grouping)
	{
		this.grouping = grouping;
	}
	
	public Grouping getGrouping()
	{
		return this.grouping;
	}
}
