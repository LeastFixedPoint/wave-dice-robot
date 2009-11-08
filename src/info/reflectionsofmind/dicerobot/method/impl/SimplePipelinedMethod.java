package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.method.IRollParser;
import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.method.IRollResult;
import info.reflectionsofmind.dicerobot.method.IRollRoller;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public abstract class SimplePipelinedMethod<TRollParser extends IRollParser<TRollRequest>, TRollRequest extends IRollRequest, TRollRoller extends IRollRoller<TRollRequest, TRollResult>, TRollResult extends IRollResult<TRollRequest>, TRollWriter extends IRollWriter<TRollResult>> extends AbstractRollingMethod
{
	@Override
	public final void writeResult(final String input, final IFormattedBufferedOutput output) throws UserReadableException
	{
		final TRollRequest request = createParser().parse(input);
		final TRollResult result = createRoller(getDieRollerFactory()).makeRoll(request);
		createWriter().render(output, result);
	}
	
	abstract protected TRollParser createParser();
	
	abstract protected TRollRoller createRoller(IDieRollerFactory factory);
	
	abstract protected TRollWriter createWriter();
}
