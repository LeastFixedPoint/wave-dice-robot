package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollInput;
import info.reflectionsofmind.dicerobot.method.IRollOutput;
import info.reflectionsofmind.dicerobot.method.IRollRoller;

public abstract class AbstractRollRoller<TRollInput extends IRollInput, TRollOutput extends IRollOutput> implements IRollRoller<TRollInput, TRollOutput>
{
	private IDieRollerFactory factory;
	
	@Override
	public IRollRoller<TRollInput, TRollOutput> setDieRollerFactory(final IDieRollerFactory factory)
	{
		this.factory = factory;
		return this;
	}
	
	public IDieRoller createDieRoller()
	{
		return this.factory.createDieRoller();
	}
}
