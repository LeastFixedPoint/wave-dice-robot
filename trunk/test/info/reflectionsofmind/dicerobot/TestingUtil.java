package info.reflectionsofmind.dicerobot;

import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollOutput;
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
	
	public static <TRollWriter extends IRollWriter<TRollOutput>, TRollOutput extends IRollOutput> void assertWrite(
			final TRollWriter rollWriter, final TRollOutput rollOutput, final String expectedTextOutput) throws Exception
	{
		final MockOutput writer = new MockOutput();
		rollWriter.render(writer, rollOutput);
		assertEquals(expectedTextOutput, writer.getString());
	}
}
