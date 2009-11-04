package info.reflectionsofmind.dicerobot.method;

public interface IRollWriter<TRollOutput extends IRollOutput>
{
	void render(IFormattedBufferedOutput writer, TRollOutput output);
}
