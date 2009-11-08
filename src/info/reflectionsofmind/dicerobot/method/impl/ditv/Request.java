package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.method.impl.Roll;

import java.util.ArrayList;
import java.util.List;

public final class Request implements IRollRequest
{
	private final List<Roll> rolls = new ArrayList<Roll>();
	
	public Request add(final int count, final int dieSize)
	{
		this.rolls.add(new Roll(count, dieSize));
		return this;
	}
	
	public List<Roll> getRolls()
	{
		return this.rolls;
	}
}
