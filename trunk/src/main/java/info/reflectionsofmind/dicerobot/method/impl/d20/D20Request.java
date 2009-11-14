package info.reflectionsofmind.dicerobot.method.impl.d20;

import info.reflectionsofmind.dicerobot.method.IRollRequest;
import info.reflectionsofmind.dicerobot.method.impl.sum.SumRequest;

public abstract class D20Request implements IRollRequest
{
	
	public static class CharGen extends D20Request
	{
		
	}
	
	public static class SumAllRolls extends D20Request
	{
		private final SumRequest request;
		
		public SumAllRolls(final SumRequest request)
		{
			this.request = request;
		}
		
		public SumRequest getRequest()
		{
			return this.request;
		}
	}
}
