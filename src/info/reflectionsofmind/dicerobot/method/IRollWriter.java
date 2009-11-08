package info.reflectionsofmind.dicerobot.method;

import info.reflectionsofmind.dicerobot.exception.CannotRenderRollException;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

public interface IRollWriter<TRollResult extends IRollResult<?>>
{
	void render(IFormattedBufferedOutput output, TRollResult result) throws CannotRenderRollException;
}
