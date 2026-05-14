<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Detail Fixture</title>
    <script src="/static/jquery.js"></script>
    <script src="/static/axios.js"></script>
</head>
<body>
<form id="searchForm" action="/fixture/api/v1/orders/search" method="post">
    <input name="tenantId" value="tenant-a"/>
    <input name="userId" value="1001"/>
    <button type="submit">Search</button>
</form>
<script>
    fetch('/fixture/api/v1/orders/10001', {
        method: 'GET',
        headers: {'X-Tenant-Id': 'tenant-a', 'X-Trace-Id': 'trace-from-jsp'}
    });

    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/fixture/api/v1/orders/10001/map');
    xhr.setRequestHeader('X-Tenant-Id', 'tenant-a');
    xhr.send();

    $.ajax({
        url: '/fixture/api/v1/orders/dynamic?channel=web',
        method: 'POST',
        headers: {'X-Tenant-Id': 'tenant-a'},
        data: JSON.stringify({tenantId: 'tenant-a', orderId: 10001, pageNum: 1, pageSize: 20})
    });

    axios.get('/fixture/api/reactive/orders/10001', {
        headers: {'X-Tenant-Id': 'tenant-a'}
    });
</script>
</body>
</html>
