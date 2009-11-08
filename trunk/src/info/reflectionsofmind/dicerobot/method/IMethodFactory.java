package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.exception.MethodNotFoundException;

import java.io.Serializable;

public interface IMethodFactory extends Serializable
{
	IRollingMethod createMethod(String config) throws MethodNotFoundException;
}
