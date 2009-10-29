package info.reflectionsofmind.dicerobot.event;

import info.reflectionsofmind.dicerobot.DiceRobot;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import com.google.wave.api.Event;

public class RollEvent extends AbstractDiceRobotEvent
{
	private final RollRequest request;
	
	public RollEvent(final Event event, final RollRequest request)
	{
		super(event);
		this.request = request;
	}
	
	public RollRequest getRequest()
	{
		return this.request;
	}
	
	@Override
	public void dispatch(final DiceRobot diceRobot)
	{
		diceRobot.onEvent(this);
	}
}
