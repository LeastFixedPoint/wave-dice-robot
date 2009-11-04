package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;

public interface IRollRoller<TRollInput extends IRollInput, TRollOutput extends IRollOutput>
{
	TRollOutput makeRoll(TRollInput input) throws CannotMakeRollException;
	
	IRollRoller<TRollInput, TRollOutput> setDieRollerFactory(IDieRollerFactory factory);
}
