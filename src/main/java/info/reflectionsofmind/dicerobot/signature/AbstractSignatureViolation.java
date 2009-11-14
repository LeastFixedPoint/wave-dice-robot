package info.reflectionsofmind.dicerobot.signature;

public abstract class AbstractSignatureViolation implements ISignatureViolation
{
	private final String signature;
	
	public AbstractSignatureViolation(final String signature)
	{
		this.signature = signature;
	}
	
	public String getExpectedSignature()
	{
		return this.signature;
	}
}
