package info.reflectionsofmind.dicerobot;

import info.reflectionsofmind.dicerobot.exception.FatalException;
import info.reflectionsofmind.dicerobot.wrapper.RollRequest;

import java.util.List;

public interface IDiceRobotRoller
{
	List<RollRequest> extractRequests(IWritableText text);
	
	/** Executes roll and writes results. Does not flush output. */
	void executeRequest(RollRequest request, IRollExecutionContext context) throws FatalException;
}
