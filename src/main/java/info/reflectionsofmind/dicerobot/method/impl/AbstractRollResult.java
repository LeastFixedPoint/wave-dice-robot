package info.reflectionsofmind.dicerobot.method.impl;

import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.method.IRollResult;

public abstract class AbstractRollResult<TRollRequest extends IRollRequest> implements IRollResult<TRollRequest>
{
	private final TRollRequest request;
	
	public AbstractRollResult(final TRollRequest request)
	{
		this.request = request;
	}
	
	public TRollRequest getRequest()
	{
		return this.request;
	}
}
