package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.event.RollEvent;
import info.reflectionsofmind.dicerobot.event.SelfAddedEvent;
import info.reflectionsofmind.dicerobot.event.SetDefaultEvent;
import info.reflectionsofmind.dicerobot.wrapper.DiceRobotBlip;
import info.reflectionsofmind.dicerobot.wrapper.DiceRobotGadget;
import info.reflectionsofmind.dicerobot.wrapper.DiceRobotHomeBlip;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import com.google.wave.api.Event;
import com.google.wave.api.RobotMessageBundle;

public class EventProcessor
{
	public EventList convert(final RobotMessageBundle bundle)
	{
		final EventList events = new EventList();
		
		if (bundle.wasSelfAdded())
		{
			events.add(new SelfAddedEvent(bundle.getWavelet()));
		}
		
		for (final Event event : bundle.getEvents())
		{
			final DiceRobotBlip blip = DiceRobotBlip.create(event.getBlip());
			
			if (blip instanceof DiceRobotHomeBlip)
			{
				final DiceRobotGadget gadget = ((DiceRobotHomeBlip) blip).getGadget();
				events.add(new SetDefaultEvent(event, gadget.getDefaultRollingMethod()));
			}
			else
			{
				for (final RollRequest request : blip.getRollRequests())
				{
					events.add(new RollEvent(event, request));
				}
			}
		}
		
		return events;
	}
}
