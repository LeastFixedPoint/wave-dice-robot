<?xml version="1.0" encoding="UTF-8" ?>
<%@ page isELIgnored="false" %>
<%@ page import=
	"info.reflectionsofmind.dicerobot.method.impl.sum.AdditiveRoll,
	info.reflectionsofmind.dicerobot.method.impl.*, 
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
						font-size: small; 
						background-image: url('http://dice-y.appspot.com/images/dicebg.png');
						background-repeat: repeat-y; 
						background-position: center right;
					}
					div.left {
						width: 240px;
						float: left;
						border-right: 1px solid black;
					}
					div.right { 
						margin-left: 240px;
						padding-left: 12px;
					}
					div#config-sum {
						display: none;
					}
					div.examples {
						font-style: italic;
					}
					div.examples ul {
						margin-top: 0px;
						padding-left: 24px;
						list-style-type: circle;
					}
					div.examples ul li {
					}
				</style>
			</head>
			<body>
				<script type="text/javascript" src="http://wave-api.appspot.com/public/wave.js"></script>
				<script type="text/javascript" src="http://jqueryjs.googlecode.com/files/jquery-1.3.2.min.js"></script>
				
				<div class="left">
					<b>Default rolling method</b><br/>
		
					<table style="font-size: small;">
						<%
							for (Map.Entry<String, IRollingMethod> entry : new DefaultMethodFactory().getMethods().entrySet()) 
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
					<br/>
					<a href="http://code.google.com/p/wave-dice-robot/wiki/RollingMethods" target="_blank">Click here for instructions</a>
				</div>
				<div class="right">
					<div id="config-default">
						<b>This method cannot be configured</b>
					</div>
					<div id="config-sum">
						<b>Sum of all rolls</b><br/>
						<br/>
						Grouping method<br/>
						<input type="radio" name="config-sum-grouping" 
							id="config-sum-grouping-expanded" value="expanded" />
						<label for="config-sum-grouping-expanded">[2d6+1d6 = 3 + 4 + 2 = 9]</label>
						<br/>
						
						<input type="radio" name="config-sum-grouping" 
							id="config-sum-grouping-grouped" value="grouped" />
						<label for="config-sum-grouping-grouped">[2d6+1d6 = 7 + 2 = 9]</label>
						<br/>
						
						<input type="radio" name="config-sum-grouping" 
							id="config-sum-grouping-result" value="result" />
						<label for="config-sum-grouping-result">[2d6+1d6 = 9]</label>
						<br/>
						<br/>
						<div class="examples">
							Examples: 
							<ul>
								<li>[2d6-2-1d8+3]</li>
								<li>[d20-6]</li>
								<li>[2+2]</li>
							</ul>
						</div>
					</div>
					<div id="config-d20">
						<b>D20 System</b><br/>
						<br/>
						<div class="examples">
							Examples: 
							<ul>
								<li>[cg] - generate character</li>
								<li>[d20-6]</li>
								<li>[2d6+1d8+3]</li>
								<li>[d100]</li>
							</ul>
						</div>
					</div>
				</div>
				
				<script type="text/javascript"> 
					var drmKey = "<%= DiceRobotServlet.DEFAULT_METHOD_KEY %>";
					
					function getCheckedMethod() {
						return $("input[name='defaultMethod']:checked").val();
					}
					
					function setCheckedMethod(method) {
						$("#" + method).attr("checked", "checked");
					}
					
					var sumGroupingKey = "<%= AdditiveRoll.SUM_GROUPING_KEY %>";
					
					function getSumGrouping() {
						return $("input[name='config-sum-grouping']:checked").val();
					}
					
					function setSumGrouping(grouping) {
						$("#config-sum-grouping-" + grouping).attr("checked", "checked");
					}
					
					function getState(key) {
						return wave.getState().get(key);
					}
					
					function setState(key, value) {
						var delta = {};
						delta[key] = value;
						wave.getState().submitDelta(delta);
					}
					
					function updateConfigPanel() {
						var method = getCheckedMethod();
						$("div.right > div:visible").hide();
						
						if ($("div#config-" + method).size() > 0) {
							$("div#config-" + method).show();
						} else {
							$("div#config-default").show();
						}
					}
					
					function onStateChange() {
						if (!getState(drmKey)) {
							setState(drmKey, "sum"); // For first load - initialize state value
							updateConfigPanel();
						}
						
						if (!getState(sumGroupingKey)) {
							setState(sumGroupingKey, "grouped");
						}
						
						if (getState(drmKey) != getCheckedMethod()) {
							setCheckedMethod(getState(drmKey)); // For any load - set correct radio 
							updateConfigPanel();
						}
						
						if (getState(sumGroupingKey) != getSumGrouping()) {
							setSumGrouping(getState(sumGroupingKey));
						}
					}
					
					function onDefaultMethodChanged() {
						setState(drmKey, getCheckedMethod()); // For click on radio - set correct state
						updateConfigPanel();
					}
					
					function onSumGroupingChanged() {
						setState(sumGroupingKey, getSumGrouping());
					}
					
					function init() {
						$("input[name='defaultMethod']").change(onDefaultMethodChanged);
						$("input[name='config-sum-grouping']").change(onSumGroupingChanged);
					
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