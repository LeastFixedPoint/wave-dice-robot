package info.reflectionsofmind.dicerobot;

import static org.junit.Assert.assertEquals;
import info.reflectionsofmind.dicerobot.method.MockOutput;
import info.reflectionsofmind.dicerobot.method.impl.DefaultMethodFactory;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import org.junit.Test;

public class LimitsTest
{
	@Test
	public void shouldNotAllowTooLongRequests() throws Exception
	{
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxRequestLength(4);
		final MockOutput output = new MockOutput();
		
		robot.executeRequest(new RollRequest(output, "sum", "2d6+5"), null);
		
		assertEquals("request too long (max 4)", output.getString());
	}
	
	@Test
	public void shouldNotAllowTooLongResults() throws Exception
	{
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxResultLength(4);
		final MockOutput output = new MockOutput();
		
		robot.executeRequest(new RollRequest(output, "sum", "1+2+3"), null);
		
		assertEquals("result too long (max 4)", output.getString());
	}
	
	@Test
	public void shouldNotAllowTooManyRolls() throws Exception
	{
		final DiceRobot robot = new DiceRobot(new DefaultMethodFactory()).setMaxNumberOfRolls(3);
		final MockOutput output = new MockOutput();
		
		robot.executeRequest(new RollRequest(output, "sum", "2d6+2d8"), null);
		
		assertEquals("too many rolls (max 3)", output.getString());
	}
}
