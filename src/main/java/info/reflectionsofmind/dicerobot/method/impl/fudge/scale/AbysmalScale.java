package info.reflectionsofmind.dicerobot.method.impl.fudge.scale;

import static info.reflectionsofmind.dicerobot.util.StringUtil.MINUS;

public class AbysmalScale implements IScale
{
	private final int rate;

	public AbysmalScale(final int rate)
	{
		this.rate = rate;
	}

	@Override
	public IScale adjust(final int adjustment)
	{
		return Scale.ABYSMAL.adjust(adjustment - this.rate);
	}

	@Override
	public String getAdjective()
	{
		return Scale.ABYSMAL.getAdjective() + " " + MINUS + this.rate;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return obj instanceof AbysmalScale && ((AbysmalScale) obj).rate == this.rate;
	}
}