package info.reflectionsofmind.dicerobot.output;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Test;

import com.google.wave.api.Range;
import com.google.wave.api.TextView;

public class DocumentWriterTest
{
	@Test
	public void shouldNotAppendImmediately() throws Exception
	{
		final TextView textView = mock(TextView.class);
		final DocumentWriter writer = new DocumentWriter(textView, 0);
		
		writer.append("test");
		
		verifyZeroInteractions(textView);
	}
	
	@Test
	public void shouldAppendOnFlush() throws Exception
	{
		final TextView textView = mock(TextView.class);
		final DocumentWriter writer = new DocumentWriter(textView, 10);
		
		writer.append("first").append("second").flush();
		
		verify(textView).insert(eq(10), eq("firstsecond"));
		verifyNoMoreInteractions(textView);
	}
	
	@Test
	public void shouldNotAnnotateImmediately() throws Exception
	{
		final TextView textView = mock(TextView.class);
		final DocumentWriter writer = new DocumentWriter(textView, 0);
		
		writer.append("test").with(Style.RED);
		
		verifyZeroInteractions(textView);
	}
	
	@Test
	public void shouldAnnotateOnFlush() throws Exception
	{
		final TextView textView = mock(TextView.class);
		final DocumentWriter writer = new DocumentWriter(textView, 10);
		
		writer.append("test").with(Style.RED).flush();
		
		verify(textView).setAnnotation(eq(new Range(10, 14)), eq("style/color"), eq("red"));
		verify(textView, atMost(1)).setAnnotation(any(Range.class), anyString(), anyString());
	}
	
	@Test
	public void shouldAnnotateOnlyPreviousAppend() throws Exception
	{
		final TextView textView = mock(TextView.class);
		final DocumentWriter writer = new DocumentWriter(textView, 10);
		
		writer.append("before").append("test").with(Style.RED).append("after").flush();
		
		verify(textView).setAnnotation(eq(new Range(16, 20)), eq("style/color"), eq("red"));
		verify(textView, atMost(1)).setAnnotation(any(Range.class), anyString(), anyString());
	}
}
