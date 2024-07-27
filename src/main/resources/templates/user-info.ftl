<!DOCTYPE html>
<html>
<head>
    <title>Пися</title>
</head>
<body>
<h1>Попа</h1><hr>
<h3>Подробная информация о пользователе ${user.name}</h3>
<b>Номер телефона: </b> <#if user.numberPhone??>${user.numberPhone}<#else>Не указано</#if><br>
<b>Email: </b> ${user.email}<br>
<h4>Товары пользователя ${user.name}</h4>
<#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
    </div>
    <#else>
    <h3>Товаров нет</h3>
</#list>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>


