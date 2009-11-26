package info.reflectionsofmind.dicerobot.method.impl.fudge;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public abstract class AbstractRoll<TConfig extends IRollConfig> implements IRollInstance<TConfig>
{
	private TConfig config;

	public TConfig getConfig()
	{
		return this.config;
	}

	@Override
	public void execute(final TConfig config, final String input, final IDieRollerFactory factory, final IFormattedBufferedOutput output) throws UserReadableException
	{
		this.config = config;

		try
		{
			parse(input);
		}
		catch (final NumberFormatException exception)
		{
			throw new CannotParseRollException("invalid number");
		}

		roll(factory);

		try
		{
			write(output);
		}
		catch (final OutputException exception)
		{
			throw CannotRenderRollException.wrap(exception);
		}
	}

	protected abstract void parse(String input) throws CannotParseRollException;

	protected abstract void roll(final IDieRollerFactory factory) throws CannotMakeRollException;

	protected abstract void write(final IFormattedBufferedOutput output) throws CannotRenderRollException, OutputException;
}
