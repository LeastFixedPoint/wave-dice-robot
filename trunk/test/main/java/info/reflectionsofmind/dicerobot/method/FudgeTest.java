package info.reflectionsofmind.dicerobot.method;

import static org.junit.Assert.*;
import info.reflectionsofmind.dicerobot.MockOutput;
import info.reflectionsofmind.dicerobot.TestingUtil;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.method.impl.fudge.Fudge;
import info.reflectionsofmind.dicerobot.method.impl.fudge.FudgeConfig;
import info.reflectionsofmind.dicerobot.method.impl.fudge.FudgeConfig.Grouping;

import org.junit.Test;

public class FudgeTest
{
	@Test
	public void shouldMakePositiveRolls() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "f", output);

		assertEquals("<b><green>+</green></b> <b><red>-</red></b> <b>0</b> <b><green>+</green></b> = <b><green>+1</green></b>", output.getFormatted());
	}

	@Test
	public void shouldMakeNegativeRolls() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 1, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "f", output);

		assertEquals("<b><green>+</green></b> <b><red>-</red></b> <b>0</b> <b><red>-</red></b> = <b><red>-1</red></b>", output.getFormatted());
	}

	@Test
	public void shouldMakeZeroRolls() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 2, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "f", output);

		assertEquals("<b><green>+</green></b> <b><red>-</red></b> <b>0</b> <b>0</b> = <b>0</b>", output.getFormatted());
	}

	@Test
	public void shouldMakeCountedRolls() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, null, "5", output);

		assertEquals("<b><green>+</green></b> <b><red>-</red></b> <b>0</b> <b><green>+</green></b> <b><red>-</red></b> = <b>0</b>", output.getFormatted());
	}

	@Test
	public void shouldMakeCountedRollsWithNdF() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "5dF", output);

		assertEquals("<b><green>+</green></b> <b><red>-</red></b> <b>0</b> <b><green>+</green></b> <b><red>-</red></b> = <b>0</b>", output.getFormatted());
	}

	@Test(expected = CannotParseRollException.class)
	public void shouldComplainOnZeroDice() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "0", output);
	}

	@Test(expected = CannotParseRollException.class)
	public void shouldComplainOnNegativeDice() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "-1", output);
	}
}
