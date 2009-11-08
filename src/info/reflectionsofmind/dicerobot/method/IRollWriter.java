package info.reflectionsofmind.dicerobot.method;

public interface IRollWriter<TRollOutput extends IRollResult<?>>
{
	void render(IFormattedBufferedOutput writer, TRollOutput output) throws CannotWriteRollException;
}
