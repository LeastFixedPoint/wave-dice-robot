package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.FatalException;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public abstract class AbstractStagedMethod<TConfig extends IRollConfig> extends AbstractRollingMethod
{
	public abstract IRollInstance<TConfig> createRoll();

	@Override
	public final void writeResult(final IDieRollerFactory factory, final IRollConfig config, final String input, final IFormattedBufferedOutput output) throws UserReadableException, FatalException
	{
		createRoll().execute((TConfig) config, input, factory, output);
	}
}
