package info.reflectionsofmind.dicerobot.method;


public interface IFormattedBufferedOutput
{
	IFormattedBufferedOutput append(Object object);
	
	IFormattedBufferedOutput append(Object object, String annotation, String value);
	
	void flush();
}
