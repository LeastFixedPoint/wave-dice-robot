package info.reflectionsofmind.dicerobot.event;

import info.reflectionsofmind.dicerobot.DiceRobot;

import com.google.wave.api.Event;

public final class SetDefaultEvent extends AbstractDiceRobotEvent
{
	private final String defaultMethod;
	
	public SetDefaultEvent(final Event event, final String defaultMethod)
	{
		super(event);
		this.defaultMethod = defaultMethod;
	}
	
	public String getNewDefaultMethod()
	{
		return this.defaultMethod;
	}
	
	@Override
	public void dispatch(final DiceRobot diceRobot)
	{
		diceRobot.onEvent(this);
	}
}
