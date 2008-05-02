<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div align="center">

<div style="background: #CCFFFF; width: 90%">
<display:table name="accounts" id="account"
	requestURI="/displayaccounts.htm">
	<display:column property="accountId" title="Account" />
	<display:column property="balance" title="Balance" />
</display:table>
</div>

<p>

<div style="background: #CCFFFF; width: 25%">
<form:form>

	<table>
		<tr>
			<td>From:</td>
			<td><form:select path="fromAccount">
				<c:forEach var="account" items="${accounts}">
					<form:option value="${account.accountId}"
						label="${account.accountId}" />
				</c:forEach>
			</form:select></td>
		</tr>

		<tr>
			<td>To:</td>
			<td><form:select path="toAccount">
				<c:forEach var="account" items="${accounts}">
					<form:option value="${account.accountId}"
						label="${account.accountId}" />
				</c:forEach>
			</form:select></td>
		</tr>

		<tr>
			<td>Amount:</td>
			<td><form:input path="amount" /></td>
		</tr>
	</table>

	<input type="submit" class="button" name="submit"
		value="Transfer">

</form:form></div>

</div>
