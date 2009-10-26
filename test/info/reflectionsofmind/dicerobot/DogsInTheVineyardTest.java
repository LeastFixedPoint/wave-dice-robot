package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.diceroller.IDieRoller;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.impl.ditv.DogsInTheVineyard;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DogsInTheVineyardTest
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
	public void testSingleRoll()
	{
		final DogsInTheVineyard method = new DogsInTheVineyard(mockDieRollerFactory(1, 6, 3));
		final MockOutput output = new MockOutput();
		method.writeResult("3d6", output);
		Assert.assertEquals("66 36 16", output.getString());
	}
	
	@Test
	public void testMultipleRoll()
	{
		final DogsInTheVineyard method = new DogsInTheVineyard(mockDieRollerFactory(2, 6, 4, 9));
		final MockOutput output = new MockOutput();
		method.writeResult("2d6+2d10", output);
		Assert.assertEquals("910 66 410 26", output.getString());
	}
}
