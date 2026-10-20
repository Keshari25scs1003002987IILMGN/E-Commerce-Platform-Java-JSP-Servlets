<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="form-box">
    <h1>Create Account</h1>
    <c:if test="${not empty error}"><div class="error"><c:out value="${error}"/></div></c:if>
    <form action="${ctx}/register" method="post">
        <label>Name</label>
        <input type="text" name="name" value="<c:out value='${name}'/>" required>
        <label>Email</label>
        <input type="email" name="email" value="<c:out value='${email}'/>" required>
        <label>Password (min 6 characters)</label>
        <input type="password" name="password" minlength="6" required>
        <button class="btn full">Register</button>
    </form>
    <p class="muted">Already registered? <a href="${ctx}/login">Login</a></p>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
