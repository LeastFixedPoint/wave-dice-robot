package info.reflectionsofmind.dicerobot.method.impl.sw;

import info.reflectionsofmind.dicerobot.method.impl.AbstractStagedMethod;
import info.reflectionsofmind.dicerobot.method.impl.IRollInstance;

public class SavageWorlds extends AbstractStagedMethod<SavageWorldsConfig>
{
	@Override
	public IRollInstance<SavageWorldsConfig> createRoll()
	{
		return new SavageWorldsRoll();
	}

	@Override
	public String getName()
	{
		return "Savage Worlds";
	}

}
