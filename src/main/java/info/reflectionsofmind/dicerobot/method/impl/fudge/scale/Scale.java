package info.reflectionsofmind.dicerobot.method.impl.fudge.scale;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public enum Scale implements IScale
{
	TERRIBLE("terrible"), POOR("poor"), MEDIOCRE("mediocre"), FAIR("fair"), GOOD("good"), GREAT("great"), SUPERB("superb");

	private final String adjective;

	private Scale(final String adjective)
	{
		this.adjective = adjective;
	}

	public String getAdjective()
	{
		return this.adjective;
	}

	public IScale adjust(final int adjustment)
	{
		final int newIndex = this.ordinal() + adjustment;

		if (newIndex >= Scale.values().length)
			return new SuperbScale(newIndex - values().length + 1);
		else if (newIndex < 0)
			return new TerribleScale(abs(newIndex));
		else
			return values()[newIndex];
	}

	public static IScale findByAdjective(final String adjective)
	{
		final String adjWithoutSpaces = adjective.replaceAll("\\s", "").toLowerCase();

		for (final Scale scale : values())
			if (scale.getAdjective().equals(adjWithoutSpaces))
				return scale;

		final int sep = max(adjWithoutSpaces.indexOf("+"), adjWithoutSpaces.indexOf("-"));

		if (sep == -1) return null;

		final int bonus;

		if (adjWithoutSpaces.charAt(sep) == '+')
			bonus = Integer.parseInt(adjWithoutSpaces.substring(sep + 1));
		else
			bonus = Integer.parseInt(adjWithoutSpaces.substring(sep));

		final IScale baseScale = findByAdjective(adjWithoutSpaces.substring(0, sep));

		return baseScale == null ? null : baseScale.adjust(bonus);
	}
}