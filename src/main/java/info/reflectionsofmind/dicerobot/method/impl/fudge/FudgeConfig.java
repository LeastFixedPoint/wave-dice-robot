package info.reflectionsofmind.dicerobot.method.impl.fudge;

import info.reflectionsofmind.dicerobot.method.IRollConfig;

public class FudgeConfig implements IRollConfig
{
	public enum Grouping
	{
		EXPAND, GROUP, RESULT
	}

	private final Grouping grouping;

	public FudgeConfig(final Grouping grouping)
	{
		this.grouping = grouping;
	}

	public Grouping getGrouping()
	{
		return this.grouping;
	}
}
