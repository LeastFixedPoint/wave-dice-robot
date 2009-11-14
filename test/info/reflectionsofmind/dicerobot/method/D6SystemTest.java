package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.mockDieRollerFactory;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Parser;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Result;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Roller;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Die;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Pip;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request.Wild;

import org.junit.Test;

public class D6SystemTest
{
	@Test
	public void shouldParse() throws Exception
	{
		final D6Request request = new D6Parser().parse("3D+2+W");
		assertEquals(asList(new Die(3), new Pip(2), new Wild()), request.getTokens());
	}
	
	@Test
	public void shouldRoll() throws Exception
	{
		final D6Request request = new D6Request().add(new Die(3), new Pip(2), new Wild());
		final D6Result result = new D6Roller().setFactory(mockDieRollerFactory(3, 6, 4, 4)).makeRoll(request);
		
		final Die die = (Die) request.getTokens().get(0);
		assertEquals(asList(3, 6, 4), result.getRolls(die));
		
		final Wild wild = (Wild) request.getTokens().get(2);
		assertEquals(asList(4), result.getRolls(wild));
	}
	
	@Test
	public void shouldExtendWildDice() throws Exception
	{
		final D6Request request = new D6Request().add(new Wild());
		final D6Result result = new D6Roller().setFactory(mockDieRollerFactory(6, 6, 2, 4)).makeRoll(request);
		
		final Wild wild = (Wild) request.getTokens().get(0);
		assertEquals(asList(6, 6, 2), result.getRolls(wild));
	}
	
	@Test
	public void shouldCheckOnesOnWildDice() throws Exception
	{
		final D6Request request = new D6Request().add(new Wild());
		final D6Result result = new D6Roller().setFactory(mockDieRollerFactory(1, 1, 2)).makeRoll(request);
		
		final Wild wild = (Wild) request.getTokens().get(0);
		assertEquals(asList(1, 1), result.getRolls(wild));
	}
}
