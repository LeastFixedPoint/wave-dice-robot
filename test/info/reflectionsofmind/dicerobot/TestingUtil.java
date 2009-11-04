package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;

import org.junit.Assert;
import org.mockito.Mockito;

public class TestingUtil
{
	public static IDieRollerFactory mockDieRollerFactory(final Integer first, final Integer... answers)
	{
		final IDieRoller roller = Mockito.mock(IDieRoller.class);
		Mockito.when(roller.roll(Mockito.anyInt())).thenReturn(first, answers);
		
		final IDieRollerFactory factory = Mockito.mock(IDieRollerFactory.class);
		Mockito.when(factory.createDieRoller()).thenReturn(roller);
		
		return factory;
	}
	
	public static void assertRoll(final IRollingMethod method, final String input, final String expectedOutput) throws Exception
	{
		final MockOutput output = new MockOutput();
		method.writeResult(input, output);
		Assert.assertEquals(expectedOutput, output.getString());
	}
}
