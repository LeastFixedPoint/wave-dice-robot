package info.reflectionsofmind.dicerobot.method.impl.nwod;

import info.reflectionsofmind.dicerobot.method.IRollRequest;

public final class Request implements IRollRequest
{
	private final int dicePool;
	
	public Request(final int dicePool)
	{
		this.dicePool = dicePool;
	}
	
	public int getDicePool()
	{
		return this.dicePool;
	}
}
