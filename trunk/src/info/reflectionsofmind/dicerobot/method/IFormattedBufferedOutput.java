package info.reflectionsofmind.dicerobot.method;

public interface IFormattedBufferedOutput
{
	IFormattedBufferedOutput append(Object object);
	
	IFormattedBufferedOutput with(String annotation, String value);
	
	void flush();
}
