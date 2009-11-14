package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public interface IRollingMethod
{
	IRollingMethod setDieRollerFactory(IDieRollerFactory factory);
	
	void writeResult(String input, IFormattedBufferedOutput output) throws UserReadableException;
	
	String getName();
}
