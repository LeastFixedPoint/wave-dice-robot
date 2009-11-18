package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.IRollParser;
import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.method.IRollResult;
import info.reflectionsofmind.dicerobot.method.IRollRoller;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public abstract class SimplePipelinedMethod<TRollParser extends IRollParser<TRollRequest>, TRollRequest extends IRollRequest, TRollRoller extends IRollRoller<TRollRequest, TRollResult>, TRollResult extends IRollResult<TRollRequest>, TRollWriter extends IRollWriter<TRollResult>> extends AbstractRollingMethod
{
	@Override
	public final void writeResult(final IDieRollerFactory factory, final IRollConfig config, final String input, final IFormattedBufferedOutput output) throws UserReadableException
	{
		final TRollRequest request = createParser(config).parse(input);
		final TRollResult result = createRoller(config, factory).makeRoll(request);
		
		try
		{
			createWriter(config).render(output, result);
		}
		catch (final OutputException exception)
		{
			throw CannotRenderRollException.wrap(exception);
		}
	}
	
	abstract protected TRollParser createParser(IRollConfig config);
	
	abstract protected TRollRoller createRoller(IRollConfig config, IDieRollerFactory factory);
	
	abstract protected TRollWriter createWriter(IRollConfig config);
}
