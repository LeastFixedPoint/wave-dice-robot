package info.reflectionsofmind.dicerobot.method.impl.fudge;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.FatalException;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public interface IRollInstance<TConfig extends IRollConfig>
{
	void execute(TConfig config, String input, IDieRollerFactory factory, IFormattedBufferedOutput output) throws UserReadableException, FatalException;
}
