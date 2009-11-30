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

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb><green>+</green></xb> = <xb><green>+1</green></xb>", output.getFormatted());
	}

	@Test
	public void shouldMakeNegativeRolls() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 1, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "f", output);

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb><red>−</red></xb> = <xb><red>−1</red></xb>", output.getFormatted());
	}

	@Test
	public void shouldMakeZeroRolls() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 2, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "f", output);

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb>0</xb> = <xb>0</xb>", output.getFormatted());
	}

	@Test
	public void shouldMakeCountedRolls() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, null, "5", output);

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb><green>+</green></xb> <xb><red>−</red></xb> = <xb>0</xb>", output.getFormatted());
	}

	@Test
	public void shouldMakeCountedRollsWithNdF() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "5dF", output);

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb><green>+</green></xb> <xb><red>−</red></xb> = <xb>0</xb>", output.getFormatted());
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
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "−1", output);
	}

	@Test
	public void shouldRollAdjectivesLargerThanRequest() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 3, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "fair", output);

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb><green>+</green></xb> = <xb><green>good</green></xb>", output.getFormatted());
	}

	@Test
	public void shouldRollAdjectivesSameAsRequest() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 2, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "fair", output);

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb>0</xb> = <xb>fair</xb>", output.getFormatted());
	}

	@Test
	public void shouldRollAdjectivesLesserThanRequest() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 1, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.EXPAND), "fair", output);

		assertEquals("<xb><green>+</green></xb> <xb><red>−</red></xb> <xb>0</xb> <xb><red>−</red></xb> = <xb><red>mediocre</red></xb>", output.getFormatted());
	}

	@Test
	public void shouldWriteResultOnly() throws Exception
	{
		final IDieRollerFactory rolls = TestingUtil.mockRolls(3, 1, 2, 1, 1, 2);
		final MockOutput output = new MockOutput();
		new Fudge().writeResult(rolls, new FudgeConfig(Grouping.RESULT), "fair", output);

		assertEquals("<xb><red>mediocre</red></xb>", output.getFormatted());
	}
}
