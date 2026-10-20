<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1>My Orders</h1>
<c:if test="${empty orders}"><p class="muted">You have not placed any orders yet.</p></c:if>
<c:forEach var="o" items="${orders}">
    <div class="card wide">
        <div class="order-head">
            <strong>Order #<c:out value="${o.id}"/></strong>
            <span class="tag"><c:out value="${o.status}"/></span>
            <span class="muted"><fmt:formatDate value="${o.createdAt}" pattern="dd MMM yyyy, HH:mm"/></span>
        </div>
        <ul>
            <c:forEach var="i" items="${o.items}">
                <li><c:out value="${i.productName}"/> &times; <c:out value="${i.quantity}"/>
                    &mdash; &#8377; <fmt:formatNumber value="${i.price}" minFractionDigits="2"/></li>
            </c:forEach>
        </ul>
        <p class="price">Total: &#8377; <fmt:formatNumber value="${o.total}" minFractionDigits="2"/></p>
    </div>
</c:forEach>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
