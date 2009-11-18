package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

import java.util.Map;

public abstract class AbstractRollingMethod implements IRollingMethod
{
	@Override
	public IRollConfig createConfig(final Map<String, String> map)
	{
		return null;
	}
}
