<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/list.css">
    <title>维护图书</title>
</head>
<body>
<div class="w">
    <header>
        <a href="${pageContext.request.contextPath }/cargoods/addorders" >
            <input type="button" οnclick="javascrtpt:window.location.href='${pageContext.request.contextPath}/cargoods/addorders'" value="下单" class="btn">
        </a>
        <a href="${pageContext.request.contextPath }/cargoods/clearCart" >
            <input type="button" οnclick="javascrtpt:window.location.href='${pageContext.request.contextPath}/cargoods/clearCart'" value="清空购物车" class="btn">
        </a>
        <%--<a href="${pageContext.request.contextPath }/cargoods/deleteAllCookie" >--%>
            <%--<input type="button" οnclick="javascrtpt:window.location.href='${pageContext.request.contextPath}/cargoods/deleteAllCookie'" value="清空购物车" class="btn">--%>
        <%--</a>--%>
    </header>
    <div class="list">
        <div class="list-bd">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr>
                    <th width="18%">名称</th>
                    <th width="5%">价格</th>
                    <th width="10%">生产商</th>
                    <th width="10%">数量</th>
                    <th width="10%">操作</th>
                </tr>
                <c:forEach items="${list}" var="item">
                    <tr>
                        <th width="18%">${item.name}</th>
                        <th width="5%">${item.price}</th>
                        <th width="10%">${item.produce}</th>
                        <th width="10%">${item.num}</th>
                        <th width="10%"> <a href="${pageContext.request.contextPath}/cargoods/deleteGoodsInCart?id=${item.id}"> 删除</a></th>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>
</div>
</body>
</html>