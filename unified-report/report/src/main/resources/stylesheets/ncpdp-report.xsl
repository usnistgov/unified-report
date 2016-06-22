<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) 
	at Thu Aug 13 15:26:44 EDT 2015 -->
<xsl:stylesheet exclude-result-prefixes="map" version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:message="http://www.nist.gov/healthcare/validation/message"
	xmlns:report="http://www.nist.gov/healthcare/validation/message/hl7/v2/report"
	xmlns:map="urn:internal"
	xmlns:context="http://www.nist.gov/healthcare/validation/message/hl7/v2/context"
	xmlns:profile="http://www.nist.gov/healthcare/profile">
	<xsl:output method="html" />
	<xsl:key name="categs"
		match="/report:HL7V2MessageValidationReport/report:SpecificReport/report:AssertionList/report:Assertion"
		use="concat(@Type,'+',@Result)" />
	<xsl:param name="excluded" />
	<xsl:param name="msgWithSeparators" />
	<xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
	<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
	<xsl:template match="/report:HL7V2MessageValidationReport">
		<html>
			<head>
				<link rel="stylesheet" type="text/css" href="report.css" />
				<script type="text/javascript">
					function toggle_visibility(id, elm) {
					var e =
					document.getElementById(id);
					if (elm.checked == true)
					e.style.display = '';
					else
					e.style.display = 'none';
					};
					function
					toggle_visibilityC(cls, elm) {
					var e =
					document.getElementsByClassName(cls);
					for(var i = e.length - 1; i >=
					0; i--) {
					if (elm.checked == true)
					e[i].style.display = '';
					else
					e[i].style.display = 'none';
					}
					};

					function fi(id) {
					var div =
					document.getElementById(id);
					if (div.style.display !== 'none') {
					document.getElementById("btn").childNodes[0].nodeValue = "View";
					div.style.display = 'none';
					}
					else {
					document.getElementById("btn").childNodes[0].nodeValue = "Hide";
					div.style.display = '';
					}
					};
					
					function ShowSep() {
					var tabC = document.getElementById("msgC");
					var tabS = document.getElementById("msgS");
					tabC.style.display = 'none';
					tabS.style.display = '';
					};
					function HideSep() {
					var tabC = document.getElementById("msgC");
					var tabS = document.getElementById("msgS");
					tabC.style.display = '';
					tabS.style.display = 'none';
					};
				</script>
			</head>
			<body>
				<xsl:apply-templates select="report:HeaderReport" />
				<xsl:apply-templates select="report:SpecificReport" />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="report:HeaderReport">
		<div class="report-section">
			<table class="forumline title-background" width="100%"
				cellspacing="1" cellpadding="10">
				<tbody class="cf-tbody">
					<tr>
						<td class="row1 border_right">
							<span class="maintitle">Message Validation Report</span>
						</td>
						<td class="row2" style="font-weight:bold">
							<center>
								<xsl:call-template name="dateTransformer">
									<xsl:with-param name="myDate" select="message:DateOfTest" />
									<xsl:with-param name="myTime" select="message:TimeOfTest" />
								</xsl:call-template>
							</center>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="report-section">
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2">
				<tbody class="cf-tbody">
					<tr>
						<td class="row1 border_right">Validation Type</td>
						<td class="row2">
							<center>
								<xsl:value-of select="message:Type" />
							</center>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="report-section">
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2">
				<tbody class="cf-tbody border_right">
					<tr class="border_bottom">
						<td class="row1 border_right" valign="top" rowspan="2">Testing Tool</td>
						<td class="row2 border_right ">Name</td>
						<td class="row3 ">
							<xsl:value-of select="message:ServiceName" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right ">Validation Version</td>
						<td class="row3 ">
							<xsl:value-of select="message:ServiceVersion" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<xsl:apply-templates select="message:TestCaseReference" />
	</xsl:template>
	<xsl:template match="report:SpecificReport">
		<xsl:apply-templates select="report:MetaData/report:Profile" />
		<xsl:apply-templates select="report:MetaData/report:Message" />
		<xsl:apply-templates
			select="report:MetaData/report:Context/context:FailureInterpretation" />
		<xsl:call-template name="Summary" />
		<xsl:call-template name="Assertions">
			<xsl:with-param name="classification" select="'error'" />
			<xsl:with-param name="count"
				select="../report:HeaderReport/message:ErrorCount" />
			<xsl:with-param name="color" select="'red'" />
			<xsl:with-param name="msg" select="'Errors'" />
		</xsl:call-template>
		<xsl:call-template name="Assertions">
			<xsl:with-param name="classification" select="'alert'" />
			<xsl:with-param name="count"
				select="../report:HeaderReport/message:AlertCount" />
			<xsl:with-param name="color" select="'maroon'" />
			<xsl:with-param name="msg" select="'Alerts'" />
		</xsl:call-template>
		<xsl:call-template name="Assertions">
			<xsl:with-param name="classification" select="'warning'" />
			<xsl:with-param name="count"
				select="../report:HeaderReport/message:WarningCount" />
			<xsl:with-param name="color" select="'#FF9933'" />
			<xsl:with-param name="msg" select="'Warnings'" />
		</xsl:call-template>
		<xsl:call-template name="Assertions">
			<xsl:with-param name="classification" select="'affirmative'" />
			<xsl:with-param name="count"
				select="../report:HeaderReport/message:AffirmCount" />
			<xsl:with-param name="color" select="'green'" />
			<xsl:with-param name="msg" select="'Affirmatives'" />
		</xsl:call-template>
		<!-- <xsl:call-template name="Assertions"> -->
		<!-- <xsl:with-param name="classification" select="'informational'"/> -->
		<!-- <xsl:with-param name="count" select="../report:HeaderReport/message:InfoCount"/> -->
		<!-- <xsl:with-param name="color" select="'blue'"/> -->
		<!-- <xsl:with-param name="msg" select="'Informationals'"/> -->
		<!-- </xsl:call-template> -->
	</xsl:template>
	<xsl:template name="Assertions">
		<xsl:param name="classification" />
		<xsl:param name="count" />
		<xsl:param name="color" />
		<xsl:param name="msg" />
		<xsl:if test="$excluded != $classification">
			<div class="report-section">
				<table class="forumline" width="100%" cellspacing="1"
					cellpadding="2">
					<tbody>
						<tr class="row1">
							<td>
								Validation
								<xsl:value-of select="$msg" />
							</td>
							<td align="right">
								<xsl:attribute name="style">
								color : <xsl:value-of select='$color' />;
								font-weight: bold;
							</xsl:attribute>
								Count :
								<xsl:value-of select="$count" />
								<input type="checkbox" style="margin-left : 10px;">
									<xsl:attribute name="onclick">toggle_visibility('<xsl:value-of
										select="$classification" />',this)</xsl:attribute>
									<xsl:if test="$classification='error'">
										<xsl:attribute name="checked">true</xsl:attribute>
									</xsl:if>
								</input>
							</td>
						</tr>
					</tbody>
				</table>
				<table class="forumline cf-report-category" width="100%"
					cellspacing="1" cellpadding="2">

					<xsl:if test="$classification!='error'">
						<xsl:attribute name="style">display : none;</xsl:attribute>
					</xsl:if>

					<xsl:attribute name="id"><xsl:value-of
						select='$classification' /></xsl:attribute>
					<xsl:apply-templates
						select="./report:AssertionList/report:Assertion[@Result = $classification][generate-id(.)=generate-id(key('categs',concat(@Type,'+',@Result))[1])]" />
				</table>
			</div>
		</xsl:if>
	</xsl:template>
	<xsl:template match="report:AssertionList/report:Assertion">
		<tbody>
			<tr>
				<td class="row5 border_bottom" colspan="3">
					<xsl:value-of select="@Type" />
				</td>
				<td class="row5 border_bottom" align="right">
					Count :
					<xsl:value-of select="count(key('categs',concat(@Type,'+',@Result)))" />
					<input type="checkbox" checked="true" style="margin-left : 10px;">
						<xsl:attribute name="onclick">toggle_visibilityC('<xsl:value-of
							select="@Result" /><xsl:value-of select="@Type" />',this)</xsl:attribute>
					</input>
				</td>
			</tr>
		</tbody>
		<xsl:for-each select="key('categs', concat(@Type,'+',@Result))">
			<tbody class="border_bottom">
				<xsl:attribute name="class">alternate<xsl:value-of
					select="position() mod 2" /> border_bottom <xsl:value-of
					select="@Result" /><xsl:value-of select="@Type" /></xsl:attribute>
				<tr class="border_bottom">
					<td class="row3 border_right border_bottom" rowspan="5"
						style="width:20px;">
						<xsl:value-of select="position()" />
					</td>
					<td class="row3 border_right">Type :</td>
					<td class="row3 border_bottom" colspan="2">
						<xsl:value-of select="@Type" />
					</td>
				</tr>
				<tr class="border_bottom">
					<td class="row3 border_right">Description:</td>
					<td class="row3 border_bottom" colspan="2">
						<xsl:value-of select="report:Description" />
					</td>
				</tr>
				<tr class="row3 border_bottom">
					<td rowspan="3" class="border_right border_bottom" style="width:100px;">
						Location:</td>
					<xsl:if test="report:Location/report:Line">
						<td colspan="2" style="font-weight: bold">
							Line:
							<xsl:value-of select="report:Location/report:Line" />
						</td>
					</xsl:if>
				</tr>
				<xsl:if test="report:Location/report:Column">
					<tr class="row3 border_bottom">
						<td colspan="2" style="font-weight: bold">
							Column:
							<xsl:value-of select="report:Location/report:Column" />
						</td>
					</tr>
				</xsl:if>
				<xsl:if test="report:Location/report:Path">
					<tr class="row3 border_bottom">
						<td colspan="2" style="font-weight: bold;" class="border_bottom">
							Path:
							<xsl:value-of select="report:Location/report:Path" />
						</td>
					</tr>
				</xsl:if>
			</tbody>
		</xsl:for-each>

	</xsl:template>
	<xsl:template match="report:HeaderReport/message:TestCaseReference">
		<div class="report-section">
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2">
				<tbody>
					<tr class="border_bottom">
						<td class="row1 border_right" valign="top" rowspan="4">Test Case</td>
						<td class="row2 border_right dark-gray">Test Plan</td>
						<td class="row3 ">
							<xsl:value-of select="report:TestPlan" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Test Group</td>
						<td class="row3 ">
							<xsl:value-of select="report:TestGroup" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Test Case</td>
						<td class="row3">
							<xsl:value-of select="report:TestCase" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Test Step</td>
						<td class="row3">
							<xsl:value-of select="report:TestStep" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
	<xsl:template match="report:MetaData/report:Profile">
		<div class="report-section">
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2">
				<tbody>
					<tr class="border_bottom">
						<td class="row1 border_right" valign="top" rowspan="9">Profile</td>
						<td class="row2 border_right dark-gray">Identifier</td>
						<td class="row3 ">
							<xsl:value-of select="@Identifier" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Name</td>
						<td class="row3 ">
							<xsl:value-of select="@Name" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Organization</td>
						<td class="row3 ">
							<xsl:value-of select="@Organization" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Type</td>
						<td class="row3">
							<xsl:value-of select="@Type" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Message Type</td>
						<td class="row3">
							<xsl:value-of select="@MessageType" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">XML Version</td>
						<td class="row3 ">
							<xsl:value-of select="@Version" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">XML Date</td>
						<td class="row3 ">
							<xsl:value-of select="@Date" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Specification</td>
						<td class="row3 ">
							<xsl:value-of select="@Specification" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">HL7 Version</td>
						<td class="row3 ">
							<xsl:value-of select="@HL7Version" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
	<xsl:template match="report:MetaData/report:Message">
		<div class="report-section">
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2" id="msgC">
				<tbody>
					<tr>
						<td class="row1 border_right" valign="top" rowspan="2">Message
						<button onclick="ShowSep()">Show Separators</button>
						</td>
						<td class="row2 border_right dark-gray">Encoding</td>
						<td class="row3 ">
							<xsl:value-of select="@Encoding" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Content</td>
						<td class="row3 ">
							<div style="text-align: center">
								<textarea cols="80" readonly="true" rows="10" wrap="off">
									<xsl:value-of select="report:Er7Message" />
								</textarea>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2" id="msgS" style="display: none;">
				<tbody>
					<tr>
						<td class="row1 border_right" valign="top" rowspan="2">Message 
						<button onclick="HideSep()">Hide Separators</button>
						</td>
						<td class="row2 border_right dark-gray">Encoding</td>
						<td class="row3 ">
							<xsl:value-of select="@Encoding" />
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row2 border_right dark-gray">Content</td>
						<td class="row3 ">
							<div style="text-align: center">
								<textarea cols="80" readonly="true" rows="10" wrap="off">
									<xsl:value-of select="$msgWithSeparators" />
								</textarea>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
	<xsl:template name="Summary">
		<div class="report-section">
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2">
				<tbody>
					<tr class="row1">
						<th style="border-bottom:2pt #005C99 solid" align="left">Summary</th>
					</tr>
					<tr class="border_bottom">
						<td class="row6 " style="color: red; font-weight: bold">
							<xsl:if test="$excluded != 'error'">
								<input checked="true" type="checkbox"
									onclick="toggle_visibility('error',this)" style="margin-left : 10px;" />
							</xsl:if>
							<xsl:value-of select="../report:HeaderReport/message:ErrorCount" />
							Errors
							<xsl:if test="$excluded = 'error'">
								(details not included)
							</xsl:if>
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row6 " style="color: maroon; font-weight: bold">
							<xsl:if test="$excluded != 'alert'">
								<input type="checkbox" onclick="toggle_visibility('alert',this)"
									style="margin-left : 10px;" />
							</xsl:if>
							<xsl:value-of select="../report:HeaderReport/message:AlertCount" />
							Alerts
							<xsl:if test="$excluded = 'alert'">
								(details not included)
							</xsl:if>
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row6 " style="color: #FF9933; font-weight: bold">
							<xsl:if test="$excluded != 'warning'">
								<input type="checkbox" onclick="toggle_visibility('warning',this)"
									style="margin-left : 10px;" />
							</xsl:if>
							<xsl:value-of select="../report:HeaderReport/message:WarningCount" />
							Warnings
							<xsl:if test="$excluded = 'warning'">
								(details not included)
							</xsl:if>
						</td>
					</tr>
					<tr class="border_bottom">
						<td class="row6" style="color: green; font-weight: bold">
							<xsl:if test="$excluded != 'affirmative'">
								<input type="checkbox" onclick="toggle_visibility('affirmative',this)"
									style="margin-left : 10px;" />
							</xsl:if>
							<xsl:value-of select="../report:HeaderReport/message:AffirmCount" />
							Affirmatives
							<xsl:if test="$excluded = 'affirmative'">
								(details not included)
							</xsl:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
	<xsl:template
		match="report:MetaData/report:Context/context:FailureInterpretation">
		<div class="report-section">
			<table class="forumline" width="100%" cellspacing="1"
				cellpadding="2">
				<tr class="row1">
					<th style="border-bottom:2pt #005C99 solid" align="left">Failures
						interpretation</th>
					<td align="right" style="border-bottom:2pt #005C99 solid">
						<button id="btn" onclick="fi('mfi')"> View </button>
					</td>
				</tr>
				<tbody id="mfi" style="display : none;">
					<tr>
						<td class="row5 border_bottom border_right">Category</td>
						<td class="row5 border_bottom">Classification</td>
					</tr>
					<xsl:for-each select="context:MessageFailure">
						<tr>
							<xsl:attribute name="class">border_bottom alternate<xsl:value-of
								select="position() mod 2" /></xsl:attribute>
							<td class="border_right">
								<xsl:value-of select="@Type" />
							</td>
							<td>
								<xsl:value-of select="@Result" />
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:template>
	<xsl:template name="dateTransformer">
		<xsl:param name="myDate" />
		<xsl:param name="myTime" />
		<xsl:variable name="year" select="substring-before($myDate, '-')" />
		<xsl:variable name="month"
			select="substring-before(substring-after($myDate, '-'), '-')" />
		<xsl:variable name="day"
			select="substring-before(substring-after(substring-after($myDate, '-'), '-'), '-')" />
		<xsl:choose>
			<xsl:when test="$month = 1">
				<xsl:text>January</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 2">
				<xsl:text>February</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 3">
				<xsl:text>March</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 4">
				<xsl:text>April</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 5">
				<xsl:text>May</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 6">
				<xsl:text>June</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 7">
				<xsl:text>July</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 8">
				<xsl:text>August</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 9">
				<xsl:text>September</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 10">
				<xsl:text>October</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 11">
				<xsl:text>November</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 12">
				<xsl:text>December</xsl:text>
			</xsl:when>
		</xsl:choose>

		<!-- <xsl:value-of select="$month" /> -->
		<xsl:text> </xsl:text>
		<xsl:value-of select="$day" />
		<xsl:text>, </xsl:text>
		<xsl:value-of select="$year" />
		<xsl:text> at </xsl:text>
		<xsl:value-of select="$myTime" />
	</xsl:template>
</xsl:stylesheet>
