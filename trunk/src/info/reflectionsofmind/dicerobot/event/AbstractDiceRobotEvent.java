package info.reflectionsofmind.dicerobot.event;

import info.reflectionsofmind.dicerobot.DiceRobot;
import info.reflectionsofmind.dicerobot.wrapper.DiceRobotWavelet;

import com.google.wave.api.Event;

public abstract class AbstractDiceRobotEvent
{
	private final Event event;
	
	public AbstractDiceRobotEvent(final Event event)
	{
		this.event = event;
	}
	
	public DiceRobotWavelet getWavelet()
	{
		return new DiceRobotWavelet(this.event.getWavelet());
	}
	
	public abstract void dispatch(DiceRobot diceRobot);
}
