package info.reflectionsofmind.dicerobot.method.impl.fudge.scale;

import static info.reflectionsofmind.dicerobot.util.StringUtil.PLUS;

public class SuperbScale implements IScale
{
	private final int rate;

	public SuperbScale(final int rate)
	{
		this.rate = rate;
	}

	@Override
	public IScale adjust(final int adjustment)
	{
		return Scale.SUPERB.adjust(adjustment + this.rate);
	}

	@Override
	public String getAdjective()
	{
		return Scale.SUPERB.getAdjective() + " " + PLUS + this.rate;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof SuperbScale && ((SuperbScale) obj).rate == this.rate;
	}
}