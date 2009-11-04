package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;

public interface IRollingMethod
{
	void writeResult(String input, IFormattedBufferedOutput output) throws CannotMakeRollException;
	
	String getName();
}
