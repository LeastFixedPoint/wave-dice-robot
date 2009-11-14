package info.reflectionsofmind.dicerobot.exception;

import info.reflectionsofmind.dicerobot.signature.ISignatureViolation;

import java.util.List;

public class SignatureViolationException extends DiceRobotException
{
	private final List<ISignatureViolation> violations;
	
	public SignatureViolationException(final Throwable cause, final List<ISignatureViolation> violations)
	{
		super("Signatures violated");
		this.violations = violations;
	}
	
	public List<ISignatureViolation> getViolations()
	{
		return this.violations;
	}
}
