<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1>Your Cart</h1>
<c:choose>
<c:when test="${empty sessionScope.cart or sessionScope.cart.empty}">
    <p class="muted">Your cart is empty. <a href="${ctx}/products">Continue shopping</a></p>
</c:when>
<c:otherwise>
    <table>
        <tr><th>Product</th><th>Price</th><th>Quantity</th><th>Subtotal</th><th></th></tr>
        <c:forEach var="item" items="${sessionScope.cart.items}">
            <tr>
                <td><c:out value="${item.product.name}"/></td>
                <td>&#8377; <fmt:formatNumber value="${item.product.price}" minFractionDigits="2"/></td>
                <td>
                    <form action="${ctx}/cart" method="post" class="inline">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <input type="number" name="quantity" value="${item.quantity}" min="0" max="${item.product.stock}" class="qty">
                        <button class="btn small">Update</button>
                    </form>
                </td>
                <td>&#8377; <fmt:formatNumber value="${item.subtotal}" minFractionDigits="2"/></td>
                <td>
                    <form action="${ctx}/cart" method="post" class="inline">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <button class="btn small danger">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <tr class="total"><td colspan="3">Total</td>
            <td colspan="2">&#8377; <fmt:formatNumber value="${sessionScope.cart.total}" minFractionDigits="2"/></td></tr>
    </table>
    <form action="${ctx}/checkout" method="post" class="right">
        <button class="btn">Place Order</button>
    </form>
</c:otherwise>
</c:choose>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
