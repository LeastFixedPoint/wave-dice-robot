package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.event.IDiceRollerEvent;

import java.util.ArrayList;

public class EventList extends ArrayList<IDiceRollerEvent>
{
	public void dispatchAll(final DiceRobot target)
	{
		for (final IDiceRollerEvent event : this)
		{
			event.dispatch(target);
		}
	}
}
