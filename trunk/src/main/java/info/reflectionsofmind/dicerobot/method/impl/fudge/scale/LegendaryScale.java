package info.reflectionsofmind.dicerobot.method.impl.fudge.scale;

import static info.reflectionsofmind.dicerobot.util.StringUtil.PLUS;

public class LegendaryScale implements IScale
{
	private final int rate;

	public LegendaryScale(final int rate)
	{
		this.rate = rate;
	}

	@Override
	public IScale adjust(final int adjustment)
	{
		return Scale.LEGENDARY.adjust(adjustment + this.rate);
	}

	@Override
	public String getAdjective()
	{
		return Scale.LEGENDARY.getAdjective() + " " + PLUS + this.rate;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof LegendaryScale && ((LegendaryScale) obj).rate == this.rate;
	}
}