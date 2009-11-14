package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.method.IRollResult;
import info.reflectionsofmind.dicerobot.method.IRollRoller;

public abstract class AbstractRollRoller<TRollRequest extends IRollRequest, TRollOutput extends IRollResult<TRollRequest>> implements IRollRoller<TRollRequest, TRollOutput>
{
	private final IDieRollerFactory factory;
	
	public AbstractRollRoller(final IDieRollerFactory factory)
	{
		this.factory = factory;
	}
	
	public IDieRoller createDieRoller()
	{
		return this.factory.createDieRoller();
	}
	
	public IDieRollerFactory getFactory()
	{
		return this.factory;
	}
}
