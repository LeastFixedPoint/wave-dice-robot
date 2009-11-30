package info.reflectionsofmind.dicerobot.method.impl.fudge;

import static info.reflectionsofmind.dicerobot.util.StringUtil.MINUS;
import static info.reflectionsofmind.dicerobot.util.StringUtil.PLUS;
import static java.lang.Math.abs;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.CannotMakeRollException;
import info.reflectionsofmind.dicerobot.exception.CannotParseRollException;
import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.method.impl.AbstractRoll;
import info.reflectionsofmind.dicerobot.method.impl.fudge.FudgeConfig.Grouping;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.IScale;
import info.reflectionsofmind.dicerobot.method.impl.fudge.scale.Scale;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.JoiningWriter;
import info.reflectionsofmind.dicerobot.output.Style;

public class FudgeRoll extends AbstractRoll<FudgeConfig>
{
	private int numberOfDice;
	private int[] rolls;
	private int sum;

	private IScale base;

	@Override
	protected void parse(final String rawInput) throws CannotParseRollException
	{
		final String input = rawInput.toLowerCase().replaceAll("\\s", "");

		final IScale scale = Scale.findByAdjective(input);

		if (scale != null)
		{
			this.numberOfDice = 4;
			this.base = scale;
		}
		else if (input.endsWith("df"))
			this.numberOfDice = Integer.parseInt(input.substring(0, input.length() - 2));
		else if (input.equalsIgnoreCase("f"))
			this.numberOfDice = 4;
		else
			this.numberOfDice = Integer.parseInt(input);

		if (this.numberOfDice <= 0)
			throw new CannotParseRollException("can roll only positive number of dice");
	}

	@Override
	protected void roll(final IDieRollerFactory factory) throws CannotMakeRollException
	{
		this.rolls = new int[this.numberOfDice];
		this.sum = 0;

		for (int i = 0; i < this.numberOfDice; ++i)
		{
			this.rolls[i] = factory.createDieRoller().roll(3) - 2;
			this.sum += this.rolls[i];
		}
	}

	@Override
	protected void write(final IFormattedBufferedOutput output) throws CannotRenderRollException, OutputException
	{
		if (getGrouping() == Grouping.EXPAND)
		{
			final JoiningWriter joiner = new JoiningWriter(output, " ");

			for (final int roll : this.rolls)
				appendRoll(joiner, roll);

			output.append(" = ");
		}

		if (this.base != null)
			output.append(this.base.adjust(this.sum).getAdjective());
		else if (this.sum > 0)
			output.append(PLUS + this.sum);
		else if (this.sum < 0)
			output.append(MINUS + abs(this.sum));
		else
			output.append("0");

		if (this.sum > 0)
			output.with(Style.GREEN).with(Style.EXTRA_BOLD);
		else if (this.sum < 0)
			output.with(Style.RED).with(Style.EXTRA_BOLD);
		else
			output.with(Style.EXTRA_BOLD);
	}

	private Grouping getGrouping()
	{
		return getConfig() != null ? getConfig().getGrouping() : Grouping.EXPAND;
	}

	public static void appendRoll(final IFormattedBufferedOutput output, final int result) throws OutputException
	{
		if (result == 0)
		{
			output.append("0");
		}
		else if (result < 0)
		{
			output.append(MINUS).with(Style.RED);
		}
		else
		{
			output.append(PLUS).with(Style.GREEN);
		}

		output.with(Style.EXTRA_BOLD);
	}
}
