package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.method.impl.ditv.CannotParseRollException;

public interface IRollParser<TRollInput extends IRollInput>
{
	TRollInput parse(String input) throws CannotParseRollException;
}
