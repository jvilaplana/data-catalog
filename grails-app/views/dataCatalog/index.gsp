<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="bootstrap-select.css"/>
    <asset:stylesheet src="jquery.timepicker.css"/>
</head>
<body>
<%@ page import="org.irblleida.dc.DocVariable" %>
<%@ page import="org.irblleida.dc.DocClass" %>
<%@ page import="org.irblleida.dc.DocEnum" %>
<%@ page import="org.irblleida.dc.DocEnumValue" %>


<div class="container mt-5">
    <div class="card">
        <div class="card-header">
            <span><g:message code="plugin.title"/> (<g:message code="domain.label"/>)</span>
        </div>
        <div class="card-body">
            <div class="row justify-content-md-center">
            <g:each var="docClass" in="${docClassList}">
                <div class="col-md-10">
                    <div class="card mt-4">
                        <div class="card-header">
                            <span>${docClass.name}</span>
                        </div>
                        <div class="card-body">
                            ${docClass.description}
                            <g:message code="variables.label"/>:
                            <table class="table mt-2">
                                <thead>
                                <tr>
                                    <th><g:message code="variable.name.label"/></th>
                                    <th><g:message code="variable.description.label"/></th>
                                    <th><g:message code="variable.type.label"/></th>
                                    <th><g:message code="variable.code.label"/></th>
                                    <th><g:message code="variable.required.label"/></th>
                                    <th><g:message code="variable.defaultUnits.label"/></th>
                                </tr>
                                </thead>
                                <tbody id="tbody">
                                <g:each var="docVariable" in="${DocVariable.findAllByDomain(docClass)}">
                                    <tr>
                                        <td>${docVariable.name}</td>
                                        <td>${docVariable.description}</td>
                                        <td>
                                            <g:set var="type" value="${DocClass.findByName(docVariable.type)}"/>
                                            <g:if test="${type}">
                                                <g:link controller="docClass" action="show" id="${type.id.toString()}">
                                                    ${docVariable.type}
                                                </g:link>
                                            </g:if><g:else>
                                                <g:set var="type" value="${DocEnum.findByName(docVariable.type)}"/>
                                                <g:if test="${type}">
                                                    <g:link controller="docEnum" action="show" id="${type.id.toString()}">
                                                        ${docVariable.type}
                                                    </g:link>
                                                </g:if>
                                                <g:else>
                                                    ${docVariable.type}
                                                </g:else>
                                            </g:else>
                                        </td>
                                        <td>${docVariable.code}</td>
                                        <td><g:message code="boolean.${docVariable.required}.label"/></td>
                                        <td>${docVariable.defaultUnits}</td>
                                    </tr>
                                </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </g:each>
            </div>
        </div>
    </div>
</div>

<asset:javascript src="jquery-3.3.1.min.js"/>
<asset:javascript src="bootstrap.js"/>
<asset:javascript src="fontawesome-all.js"/>
<asset:javascript src="jquery.are-you-sure.js"/>
</body>
</html>