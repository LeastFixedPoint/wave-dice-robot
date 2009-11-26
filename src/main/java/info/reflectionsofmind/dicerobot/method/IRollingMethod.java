package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.FatalException;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

import java.util.Map;

public interface IRollingMethod
{
	void writeResult(IDieRollerFactory factory, IRollConfig config, String input, IFormattedBufferedOutput output) throws UserReadableException, FatalException;

	String getName();

	IRollConfig createConfig(Map<String, String> map);
}
