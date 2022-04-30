<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: abou
  Date: 28/01/2021
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="connexion.titre"></s:text></title>
</head>
<body>
<s:form action="saisie">
    <s:textfield name="pseudo" key="connexion.pseudo"></s:textfield>
    <s:password name="motSecret" key="connexion.motSecret"></s:password>
    <s:submit key="connexion.go"></s:submit>
</s:form>
<s:a action="accueil"><s:text name="connexion.retour"></s:text></s:a>

</body>
</html>
