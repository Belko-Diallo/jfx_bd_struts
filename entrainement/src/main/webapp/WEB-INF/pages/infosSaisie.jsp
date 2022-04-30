<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: abou
  Date: 28/01/2021
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="accueil.pageAccueil"></s:text></title>
</head>
<body>
<p><s:property value="pseudo"></s:property></p>
<p><s:property value="motSecret"></s:property></p>
<s:a action="accueil"><s:text name="connexion.retour"></s:text></s:a>

</body>
</html>
