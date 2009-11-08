package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.output.DocumentWriter;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;

import com.google.wave.api.Blip;

public class BlipDocument implements IWritableText
{
	private final Blip blip;
	
	public BlipDocument(final Blip blip)
	{
		this.blip = blip;
	}
	
	public IFormattedBufferedOutput createOutput(final int start)
	{
		return new DocumentWriter(this.blip.getDocument(), start);
	}
	
	public String getText()
	{
		return this.blip.getDocument().getText();
	}
}
