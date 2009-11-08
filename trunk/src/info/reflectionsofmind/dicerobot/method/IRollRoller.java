package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.diceroller.CannotMakeRollException;

public interface IRollRoller<TRollRequest extends IRollRequest, TRollResult extends IRollResult<TRollRequest>>
{
	TRollResult makeRoll(TRollRequest request) throws CannotMakeRollException;
}
