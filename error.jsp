<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1>Something went wrong</h1>
<p class="muted">Please check that MySQL is running and <code>db.properties</code> is correct, then try again.</p>
<a class="btn" href="${ctx}/products">Back to products</a>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
