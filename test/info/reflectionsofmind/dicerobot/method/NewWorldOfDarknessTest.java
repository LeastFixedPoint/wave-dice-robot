package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertRoll;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockDieRollerFactory;
import info.reflectionsofmind.dicerobot.method.IRollingMethod;
import info.reflectionsofmind.dicerobot.method.impl.nwod.NewWorldOfDarkness;

import org.junit.Test;

public class NewWorldOfDarknessTest
{
	@Test
	public void testOneDieRoll() throws Exception
	{
		final IRollingMethod method = new NewWorldOfDarkness(mockDieRollerFactory(8));
		assertRoll(method, "1", "8 = 1 success");
	}
	
	@Test
	public void testSimpleRoll() throws Exception
	{
		final IRollingMethod method = new NewWorldOfDarkness(mockDieRollerFactory(1, 6, 10, 8, 9, 3));
		assertRoll(method, "5", "1 6 10 8 9 3 = 3 successes");
	}
	
	@Test
	public void testNoSuccesses() throws Exception
	{
		final IRollingMethod method = new NewWorldOfDarkness(mockDieRollerFactory(1, 6, 3, 7, 2));
		assertRoll(method, "5", "1 6 3 7 2 = failure");
	}
	
	@Test
	public void testDramaticFailure() throws Exception
	{
		final IRollingMethod method = new NewWorldOfDarkness(mockDieRollerFactory(1));
		assertRoll(method, "0", "1 = dramatic failure");
	}
}
