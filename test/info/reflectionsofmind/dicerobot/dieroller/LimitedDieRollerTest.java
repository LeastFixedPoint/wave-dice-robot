package info.reflectionsofmind.dicerobot.dieroller;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.LimitedRandomBasedDieRoller;
import info.reflectionsofmind.dicerobot.exception.RollLimitReachedException;

import org.junit.Test;

public class LimitedDieRollerTest
{
	@Test(expected = RollLimitReachedException.class)
	public void testLimitReached() throws Exception
	{
		final IDieRoller roller = new LimitedRandomBasedDieRoller(10);
		
		for (int i = 0; i < 11; i++)
		{
			roller.roll(6);
		}
	}
	
	@Test
	public void testLimitNotReached() throws Exception
	{
		final IDieRoller roller = new LimitedRandomBasedDieRoller(10);
		
		for (int i = 0; i < 10; i++)
		{
			roller.roll(6);
		}
	}
}
