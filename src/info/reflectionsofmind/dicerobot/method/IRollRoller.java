package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;

public interface IRollRoller<TRollRequest extends IRollRequest, TRollResult extends IRollResult<TRollRequest>>
{
	TRollResult makeRoll(TRollRequest request) throws CannotMakeRollException;
}
