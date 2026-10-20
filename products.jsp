<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<h1>Products</h1>
<form class="search" action="${ctx}/products" method="get">
    <input type="text" name="q" value="<c:out value='${q}'/>" placeholder="Search by name or category">
    <button class="btn">Search</button>
</form>

<c:if test="${empty products}"><p class="muted">No products found.</p></c:if>
<div class="grid">
    <c:forEach var="p" items="${products}">
        <div class="card">
            <span class="tag"><c:out value="${p.category}"/></span>
            <h3><c:out value="${p.name}"/></h3>
            <p class="muted"><c:out value="${p.description}"/></p>
            <p class="price">&#8377; <fmt:formatNumber value="${p.price}" minFractionDigits="2"/></p>
            <c:choose>
                <c:when test="${p.stock > 0}">
                    <form action="${ctx}/cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="${p.id}">
                        <button class="btn">Add to Cart</button>
                    </form>
                </c:when>
                <c:otherwise><span class="out">Out of stock</span></c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
