package info.reflectionsofmind.dicerobot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll;

import org.junit.Before;
import org.junit.Test;

/** Test of {@link AdditiveRoll}. */
public class SimpleAdditiveTest
{
	private MockOutput output;
	
	public IDieRollerFactory mockDieRollerFactory(final Integer first, final Integer... answers)
	{
		final IDieRoller roller = mock(IDieRoller.class);
		when(roller.roll(anyInt())).thenReturn(first, answers);
		
		final IDieRollerFactory factory = mock(IDieRollerFactory.class);
		when(factory.createDieRoller()).thenReturn(roller);
		
		return factory;
	}
	
	@Before
	public void setUp()
	{
		this.output = new MockOutput();
	}
	
	@Test
	public void testSingleDieRoll() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6", this.output);
		assertEquals("10", this.output.getString());
	}
	
	@Test
	public void testSingleNumber() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("5", this.output);
		assertEquals("5", this.output.getString());
	}
	
	@Test
	public void testAddition() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6+3", this.output);
		assertEquals("10 + 3 = 13", this.output.getString());
	}
	
	@Test
	public void testSubtraction() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3));
		method.writeResult("3d6-3", this.output);
		assertEquals("10 - 3 = 7", this.output.getString());
	}
	
	@Test
	public void testBigExpression() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 6, 3, 2, 3, 1, 3));
		method.writeResult("3d6-3+2d3-4-5+2d4", this.output);
		assertEquals("10 - 3 + 5 - 4 - 5 + 4 = 7", this.output.getString());
	}
	
	@Test
	public void testDieRollWithImplicitNumberOfDice() throws Exception
	{
		final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(15));
		method.writeResult("d20", this.output);
		assertEquals("15", this.output.getString());
	}
	
	// @Test
	// public void testFailureOnZeroDieSize()
	// {
	// final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 3, 6));
	// method.writeResult("1d0", this.output);
	// assertEquals("invalid roll", this.output.getString());
	// }
	//	
	// @Test
	// public void testFailureOnZeroDieCount()
	// {
	// final AdditiveRoll method = new AdditiveRoll(mockDieRollerFactory(1, 3, 6));
	// method.writeResult("0d1", this.output);
	// assertEquals("invalid roll", this.output.getString());
	// }
}
