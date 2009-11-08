package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.exception.RollingPipelineException;

public interface IRollingMethod
{
	void writeResult(String input, IFormattedBufferedOutput output) throws RollingPipelineException;
	
	String getName();
}
