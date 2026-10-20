<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="form-box">
    <h1>Login</h1>
    <c:if test="${not empty error}"><div class="error"><c:out value="${error}"/></div></c:if>
    <form action="${ctx}/login" method="post">
        <label>Email</label>
        <input type="email" name="email" value="<c:out value='${email}'/>" required>
        <label>Password</label>
        <input type="password" name="password" required>
        <button class="btn full">Login</button>
    </form>
    <p class="muted">New here? <a href="${ctx}/register">Create an account</a></p>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
