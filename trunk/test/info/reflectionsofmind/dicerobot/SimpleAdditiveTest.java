package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleAdditiveTest
{
	public IDieRollerFactory mockDieRollerFactory(final Integer first, final Integer... answers)
	{
		final IDieRoller roller = Mockito.mock(IDieRoller.class);
		Mockito.when(roller.roll(Mockito.anyInt())).thenReturn(first, answers);
		
		final IDieRollerFactory factory = Mockito.mock(IDieRollerFactory.class);
		Mockito.when(factory.createDieRoller()).thenReturn(roller);
		
		return factory;
	}
	
	@Test
	public void testSingleDieRoll()
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		final MockOutput output = new MockOutput();
		method.writeResult("3d6", output);
		Assert.assertEquals("10", output.getString());
	}
	
	@Test
	public void testSingleNumber()
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		final MockOutput output = new MockOutput();
		method.writeResult("5", output);
		Assert.assertEquals("5", output.getString());
	}
	
	@Test
	public void testAddition()
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		final MockOutput output = new MockOutput();
		method.writeResult("3d6+3", output);
		Assert.assertEquals("10 + 3 = 13", output.getString());
	}
	
	@Test
	public void testSubtraction()
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		final MockOutput output = new MockOutput();
		method.writeResult("3d6-3", output);
		Assert.assertEquals("10 - 3 = 7", output.getString());
	}
	
	@Test
	public void testBigExpression()
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3, 2, 3, 1, 3));
		final MockOutput output = new MockOutput();
		method.writeResult("3d6-3+2d3-4-5+2d4", output);
		Assert.assertEquals("10 - 3 + 5 - 4 - 5 + 4 = 7", output.getString());
	}
}
