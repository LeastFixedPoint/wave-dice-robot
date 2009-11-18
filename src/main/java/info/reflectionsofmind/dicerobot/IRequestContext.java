package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.method.IRollConfig;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

public interface IRequestContext
{
	String getDefaultMethodCode();
	
	IRollConfig getConfig(IRollingMethod method);
}
