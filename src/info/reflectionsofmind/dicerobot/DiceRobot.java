package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactory;
import info.reflectionsofmind.dicerobot.diceroller.IDieRollerFactorySource;
import info.reflectionsofmind.dicerobot.diceroller.LimitedRandomBasedDieRollerFactory;
import info.reflectionsofmind.dicerobot.diceroller.RandomBasedDieRollerFactory;
import info.reflectionsofmind.dicerobot.exception.FatalException;
import info.reflectionsofmind.dicerobot.exception.UserReadableException;
import info.reflectionsofmind.dicerobot.exception.output.OutputException;
import info.reflectionsofmind.dicerobot.exception.parse.RequestTooLongException;
import info.reflectionsofmind.dicerobot.method.IMethodFactory;
import info.reflectionsofmind.dicerobot.output.IFormattedBufferedOutput;
import info.reflectionsofmind.dicerobot.output.LimitingWriter;
import info.reflectionsofmind.dicerobot.output.WrappingWriter;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceRobot implements IDiceRobotRoller, IDieRollerFactorySource, Serializable
{
	private static final Pattern DIE_ROLL_PATTERN = Pattern.compile("\\[(?:(\\w+)\\:)?([^\\]=]+)\\]");
	
	private Integer maxRequestLength = null;
	private Integer maxResultLength = null;
	private Integer maxNumberOfRolls = null;
	
	private final IMethodFactory factory;
	
	public DiceRobot(final IMethodFactory factory)
	{
		this.factory = factory;
	}
	
	public List<RollRequest> extractRequests(final IWritableText text)
	{
		final Matcher matcher = DIE_ROLL_PATTERN.matcher(text.getText());
		final List<RollRequest> requests = new ArrayList<RollRequest>();
		
		while (matcher.find())
		{
			final String config = matcher.group(1);
			final String request = matcher.group(2);
			requests.add(new RollRequest(text.createOutput(matcher.end(2)), config, request));
		}
		
		return requests;
	}
	
	public void executeRequest(final RollRequest request, final IRollExecutionContext context) throws FatalException
	{
		try
		{
			final String config = request.getConfig() != null ? request.getConfig() : context.getDefaultConfig();
			final String input = request.getRequest();
			
			try
			{
				if (requestTooLong(input))
					throw new RequestTooLongException(input.length(), getMaxRequestLength());
				
				final IFormattedBufferedOutput wrappedOutput = wrapOutput(request);
				
				this.factory.createMethod(config)
						.setDieRollerFactory(createDieRollerFactory())
						.writeResult(input, wrappedOutput);
				
				wrappedOutput.flush();
			}
			catch (final UserReadableException exception)
			{
				request.getOutput().append(exception.getMessage()).with("style/color", "red");
			}
		}
		catch (final OutputException exception)
		{
			throw new FatalException(exception);
		}
	}
	
	private boolean requestTooLong(final String input)
	{
		return getMaxRequestLength() != null && input.length() > getMaxRequestLength();
	}
	
	private IFormattedBufferedOutput wrapOutput(final RollRequest request)
	{
		final IFormattedBufferedOutput output = getMaxResultLength() == null
				? new WrappingWriter(request.getOutput())
				: new LimitingWriter(request.getOutput(), getMaxResultLength());
		
		return output;
	}
	
	public Integer getMaxRequestLength()
	{
		return this.maxRequestLength;
	}
	
	public Integer getMaxResultLength()
	{
		return this.maxResultLength;
	}
	
	public Integer getMaxNumberOfRolls()
	{
		return this.maxNumberOfRolls;
	}
	
	public DiceRobot setMaxRequestLength(final Integer maxRequestLength)
	{
		this.maxRequestLength = maxRequestLength;
		return this;
	}
	
	public DiceRobot setMaxResultLength(final Integer maxResultLength)
	{
		this.maxResultLength = maxResultLength;
		return this;
	}
	
	public DiceRobot setMaxNumberOfRolls(final Integer maxNumberOfRolls)
	{
		this.maxNumberOfRolls = maxNumberOfRolls;
		return this;
	}
	
	public IDieRollerFactory createDieRollerFactory()
	{
		if (getMaxNumberOfRolls() == null)
		{
			return new RandomBasedDieRollerFactory();
		}
		else
		{
			return new LimitedRandomBasedDieRollerFactory(getMaxNumberOfRolls());
		}
	}
}
