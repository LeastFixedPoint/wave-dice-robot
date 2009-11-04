package info.reflectionsofmind.dicerobot;

import com.google.wave.api.AbstractRobotServlet;
import com.google.wave.api.RobotMessageBundle;

public class DiceRobotServlet extends AbstractRobotServlet
{
	private final String defaultRollingMethod;
	private final DiceRobot robot = new DiceRobot(this.defaultRollingMethod);
	private final EventProcessor processor = new EventProcessor();
	
	public DiceRobotServlet()
	{
		this(null);
	}
	
	public DiceRobotServlet(final String defaultRollingMethod)
	{
		this.defaultRollingMethod = defaultRollingMethod;
	}
	
	@Override
	public void processEvents(final RobotMessageBundle bundle)
	{
		this.processor.convert(bundle).dispatchAll(this.robot);
	}
}
