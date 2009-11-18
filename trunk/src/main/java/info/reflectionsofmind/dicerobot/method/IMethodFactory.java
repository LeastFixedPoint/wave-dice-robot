package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.exception.MethodNotFoundException;

import java.io.Serializable;

public interface IMethodFactory extends Serializable
{
	IRollingMethod getMethod(String config) throws MethodNotFoundException;
}
