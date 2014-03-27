<%@ include file="/WEB-INF/jsp/include.jsp" %>

<h1>Add/Edit Raffle</h1>

<c:url var="url" value="save"/>
<form:form action="${url}" commandName="raffle">
    <fieldset>
        <div>
          <label for="cause">Cause</label>
          <form:input path="cause"/>
        </div>
        <div>
          <label for="startDate">Start Date</label>
          <form:input path="startDate"/>
        </div>
        <div>
          <input name="submit" type="submit" value="save"/>
        </div>
    </fieldset>
</form:form>