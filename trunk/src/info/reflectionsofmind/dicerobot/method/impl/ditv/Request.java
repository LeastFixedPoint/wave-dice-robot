package info.reflectionsofmind.dicerobot.method.impl.ditv;

import info.reflectionsofmind.dicerobot.method.IRollInput;
import info.reflectionsofmind.dicerobot.method.impl.RollRequest;

import java.util.ArrayList;
import java.util.List;

public final class Request implements IRollInput
{
	private final List<RollRequest> rolls = new ArrayList<RollRequest>();
	
	public Request add(final int count, final int dieSize)
	{
		this.rolls.add(new RollRequest(count, dieSize));
		return this;
	}
	
	public List<RollRequest> getRollRequests()
	{
		return this.rolls;
	}
}
