package info.reflectionsofmind.dicerobot.method;

import static info.reflectionsofmind.dicerobot.TestingUtil.assertWrite;
import static info.reflectionsofmind.dicerobot.TestingUtil.mockRolls;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Parser;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Request;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Result;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Roller;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6System;
import info.reflectionsofmind.dicerobot.method.impl.d6.D6Writer;

import org.junit.Test;

public class D6SystemTest
{
	@Test
	public void shouldParse() throws Exception
	{
		final D6Request request = new D6Parser().parse("3D+2+2D+3");
		assertEquals(5, request.getNumberOfDice());
		assertEquals(asList(2, 3), request.getPips());
	}

	@Test
	public void shouldRoll() throws Exception
	{
		final D6Request request = new D6Request().addDice(4).addPips(3);
		final D6Result result = new D6Roller(mockRolls(3, 6, 4, 4)).makeRoll(request);

		assertEquals(asList(3, 6, 4), result.getDiceRolls());
		assertEquals(asList(4), result.getWildRolls());
	}

	@Test
	public void shouldExtendWildDice() throws Exception
	{
		final D6Request request = new D6Request().addDice(1);
		final D6Result result = new D6Roller(mockRolls(6, 6, 2)).makeRoll(request);

		assertEquals(asList(6, 6, 2), result.getWildRolls());
	}

	@Test
	public void shouldCheckOnesOnWildDice() throws Exception
	{
		final D6Request request = new D6Request().addDice(1);
		final D6Result result = new D6Roller(mockRolls(1, 2)).makeRoll(request);

		assertEquals(asList(1, 2), result.getWildRolls());
	}

	@Test
	public void shouldWriteResults() throws Exception
	{
		final D6Request request = new D6Request().addDice(4).addPips(2);
		final D6Result result = new D6Result(request).addDice(3, 6, 4).addWild(5);

		assertWrite(new D6Writer(), result, "3 + 6 + 4 + <xb>5</xb> + 2 = <xb>20</xb>");
	}

	@Test
	public void shouldWriteWildExtension() throws Exception
	{
		final D6Request request = new D6Request().addDice(1);
		final D6Result result = new D6Result(request).addWild(6, 6, 2);

		assertWrite(new D6Writer(), result, "<xb>6</xb> + <xb>6</xb> + <xb>2</xb> = <xb>14</xb>");
	}

	@Test
	public void shouldWriteWildFailure() throws Exception
	{
		final D6Request request = new D6Request().addDice(4);
		final D6Result result = new D6Result(request).addDice(3, 6, 2).addWild(1, 2);

		assertWrite(new D6Writer(), result, "3 + <red><x>6</x></red> + 2 + <xb>1</xb> + <red><x><xb>2</xb></x></red> = <xb>6</xb>");
	}

	@Test
	public void shouldWriteWildCritical() throws Exception
	{
		final D6Request request = new D6Request().addDice(4);
		final D6Result result = new D6Result(request).addDice(3, 6, 2).addWild(1, 1);

		assertWrite(new D6Writer(), result, "3 + <red><x>6</x></red> + 2 + <xb>1</xb> + <red><x><xb>1</xb></x></red> = <red><xb>critical failure</xb></red>");
	}
}
