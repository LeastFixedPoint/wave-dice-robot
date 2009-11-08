package info.reflectionsofmind.dicerobot;

import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollResult;
import info.reflectionsofmind.dicerobot.method.IRollWriter;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;
import info.reflectionsofmind.dicerobot.method.MockOutput;

import org.junit.Assert;
import org.mockito.Mockito;

public class TestingUtil
{
	public static IDieRollerFactory mockDieRollerFactory(final Integer first, final Integer... answers) throws Exception
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
	
	public static <TRollWriter extends IRollWriter<TRollResult>, TRollResult extends IRollResult<?>> void assertWrite(
			final TRollWriter rollWriter, final TRollResult rollOutput, final String expectedTextOutput) throws Exception
	{
		final MockOutput writer = new MockOutput();
		rollWriter.render(writer, rollOutput);
		assertEquals(expectedTextOutput, writer.getString());
	}
	
	public static String replicate(final int count, final String string)
	{
		final StringBuilder builder = new StringBuilder(string.length() * count);
		for (int i = 0; i < count; i++)
			builder.append(string);
		return builder.toString();
	}
}