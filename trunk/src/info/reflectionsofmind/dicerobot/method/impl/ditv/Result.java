package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.method.IRollOutput;
import info.reflectionsofmind.dicerobot.method.impl.RollResult;

import java.util.ArrayList;
import java.util.List;

public final class Result implements IRollOutput
{
	private final List<RollResult> rolls = new ArrayList<RollResult>();
	
	public Result add(final int result, final int dieSize)
	{
		this.rolls.add(new RollResult(result, dieSize));
		return this;
	}
	
	public List<RollResult> getResults()
	{
		return this.rolls;
	}
}
