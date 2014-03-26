<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. --%>
<!-- c:redirect url="/hello.htm"/ -->

<p>
  <a href="<c:url value="/drawing/events"/>">Events</a><br>
</p>