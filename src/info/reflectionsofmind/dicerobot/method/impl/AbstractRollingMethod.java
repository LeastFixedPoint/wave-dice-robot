package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.diceroller.RandomBasedDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

public abstract class AbstractRollingMethod implements IRollingMethod
{
	private IDieRollerFactory factory = new RandomBasedDieRollerFactory();
	
	public IRollingMethod setDieRollerFactory(final IDieRollerFactory factory)
	{
		this.factory = factory;
		return this;
	}
	
	public IDieRollerFactory getDieRollerFactory()
	{
		return this.factory;
	}
}
