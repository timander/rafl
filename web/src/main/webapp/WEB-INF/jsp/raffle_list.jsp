<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head><title><fmt:message key="title"/></title></head>
  <body>
    <h1>Showing Drawings</h1>
    <h3>drawing</h3>
    <table>
      <tr>
        <th>Name</th>
        <th>Date</th>
      </tr>
      <c:forEach items="${raffles}" var="raffle">
        <tr>
          <td><c:out value="${raffle.cause}"/></td>
          <td><i><fmt:formatDate value="${raffle.startDate}" type="date" pattern="EEEEE, MMMMM dd, yyyy"/></i></td>
        </tr>
      </c:forEach>
    </table>
    <br>
    <a href="<c:url value="/drawing/raffle/new"/>">Add Raffle</a>
    <br>
  </body>
</html>