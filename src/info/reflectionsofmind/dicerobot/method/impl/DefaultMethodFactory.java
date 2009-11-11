package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.exception.MethodNotFoundException;
import info.reflectionsofmind.dicerobot.method.IMethodFactory;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DogsInTheVineyard;
import info.reflectionsofmind.dicerobot.method.impl.exa.Exalted;
import info.reflectionsofmind.dicerobot.method.impl.nemesis.Nemesis;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NewWorldOfDarkness;
import info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultMethodFactory implements IMethodFactory
{
	private final Map<String, IRollingMethod> methods = new LinkedHashMap<String, IRollingMethod>();
	
	public DefaultMethodFactory()
	{
		this.methods.put("sum", new AdditiveRoll());
		this.methods.put("ditv", new DogsInTheVineyard());
		this.methods.put("nwod", new NewWorldOfDarkness());
		this.methods.put("nem", new Nemesis());
		this.methods.put("exa", new Exalted());
	}
	
	public IRollingMethod createMethod(final String config) throws MethodNotFoundException
	{
		if (!this.methods.containsKey(config))
			throw new MethodNotFoundException(config);
		
		return this.methods.get(config);
	}
	
	public Map<String, IRollingMethod> getMethods()
	{
		return this.methods;
	}
}
