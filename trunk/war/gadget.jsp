<?xml version="1.0" encoding="UTF-8" ?>
<%@ page isELIgnored="false" %>
<%@ page import=
	"info.reflectionsofmind.dicerobot.wrapper.*, 
	info.reflectionsofmind.dicerobot.method.*, 
	info.reflectionsofmind.dicerobot.*, 
	java.util.*" 
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<Module>
	<ModulePrefs title="Dicy">
		<Require feature="wave" /> 
		<Require feature="dynamic-height" />
	</ModulePrefs>
	<Content type="html">
	<![CDATA[
		<html>
			<head>
				<style type="text/css">
					body { 
					}
					p { 
						background-image: url('http://dice-y.appspot.com/images/dicebg.png');
						background-repeat: repeat-y; 
						background-position: center right;
						border: 3px solid black; 
						padding: 6px; 
						margin: 0px;
					}
				</style>
			</head>
			<body>
				<script type="text/javascript" src="http://wave-api.appspot.com/public/wave.js"></script>
				<script type="text/javascript" src="http://jqueryjs.googlecode.com/files/jquery-1.3.2.min.js"></script>
		
				<p style="font-size: small;">
					Default rolling method:<br/>
		
					<table style="font-size: small;">
						<%
							for (Map.Entry<String, IRollingMethod> entry : DiceRobot.ROLLING_METHODS.entrySet()) 
							{
								String code = entry.getKey();
								String name = entry.getValue().getName();
						%>
						<tr>
							<td>
								<input type="radio" name="defaultMethod" value="<%=code%>" id="<%=code%>" />
							</td>
							<td><label for="<%=code%>">[<%=code%>]</label></td>
							<td><label for="<%=code%>"><%=name%></label></td>
						</tr>
						<% 
							} 
						%>
					</table>
				</p>
				
				<script type="text/javascript"> 
					var drmKey = "<%= DiceRobotGadget.DEFAULT_ROLLING_METHOD_FIELD %>";
					
					function getCheckedMethod() {
						return $("input[@name='defaultMethod']:checked").val();
					}
					
					function setCheckedMethod(method) {
						$("#" + method).attr("checked", "checked");
					}
					
					function getStateMethod() {
						return wave.getState().get(drmKey);
					}
					
					function setStateMethod(method) {
						var delta = {};
						delta[drmKey] = method;
						wave.getState().submitDelta(delta);
					}
					
					function onStateChange() {
						if (!getStateMethod()) {
							setStateMethod("sum"); // For first load - initialize state value
						}
						
						if (getStateMethod() != getCheckedMethod()) {
							setCheckedMethod(getStateMethod()); // For any load - set correct radio 
						}
					}
					
					function onRadioChecked() {
						setStateMethod(getCheckedMethod()); // For click on radio - set correct state
					}
					
					function init() {
						$("input[name='defaultMethod']").change(onRadioChecked);
					
						if (wave && wave.isInWaveContainer()) {
							wave.setStateCallback(onStateChange);
						}
		
						gadgets.window.adjustHeight();
					}
				
					gadgets.util.registerOnLoadHandler(init);
				</script>
			</body>
		</html>
	]]>
	</Content>
</Module>