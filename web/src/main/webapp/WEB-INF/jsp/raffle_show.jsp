<%@ include file="/WEB-INF/jsp/include.jsp" %>

<h1>Show Raffle</h1>

<c:url var="url" value="/drawing/edit"/>
<form:form action="${url}" commandName="raffle">
    <fieldset>
        <div>
          <label for="cause">Cause</label>
          <c:out value="${raffle.cause}"/>
        </div>
        <div>
          <label for="startDate">Start Date</label>
          <fmt:formatDate value="${raffle.startDate}" type="date" pattern="EEEEE, MMMMM dd, yyyy"/>
        </div>
        <div>
          <input name="submit" type="submit" value="edit"/>
        </div>
    </fieldset>
</form:form>