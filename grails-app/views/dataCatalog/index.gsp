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


<div class="container">
    <div class="card mt-5">
        <div class="card-header">
            <span><g:message code="plugin.title"/> (<g:message code="domain.label"/>)</span>
        </div>
        <div class="card-body">
            <div class="row justify-content-md-center">
                <g:each var="docClass" in="${docClassList}">
                    <div class="col-md-11">
                        <div class="card mt-4">
                            <div class="card-header">
                                <span>${docClass.name}</span>
                            </div>
                            <div class="card-body">
                                ${docClass.description}
                                <br/>
                                <g:message code="variables.label"/>:
                                <table class="table mt-2">
                                    <thead>
                                    <tr>
                                        <th><g:message code="variable.name.label"/></th>
                                        <th><g:message code="variable.detailedName.label"/></th>
                                        <th><g:message code="variable.description.label"/></th>
                                        <th><g:message code="variable.type.label"/></th>
                                        <th><g:message code="variable.code.label"/></th>
                                        <th><g:message code="variable.required.label"/></th>
                                        <th><g:message code="variable.defaultUnits.label"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <g:each var="docVariable" in="${DocVariable.findAllByDomain(docClass)}">
                                        <tr>
                                            <td>${docVariable.name}</td>
                                            <td><g:message code="${docClass.name.uncapitalize()}.${docVariable.name}.label" default=""/></td>
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

    <div class="card mt-5">
        <div class="card-header">
            <span><g:message code="plugin.title"/> (<g:message code="enum.label"/>)</span>
        </div>
        <div class="card-body">
            <div class="row justify-content-md-center">
                <g:each var="docEnum" in="${docEnumList}">
                    <div class="col-md-11">
                        <div class="card mt-4">
                            <div class="card-header">
                                <span>${docEnum.name}</span>
                            </div>
                            <div class="card-body">
                                ${docEnum.description}
                                <br/>
                                <g:message code="enum.context.label"/>:
                                <g:each var="context" in="${docEnum.contexts}">
                                    <g:link controller="docClass" action="show" id="${context.id.toString()}">
                                        ${context.name}
                                    </g:link>
                                </g:each>
                                <br/>
                                <g:message code="values.label"/>:
                                <table class="table mt-2">
                                    <thead>
                                    <tr>
                                        <th><g:message code="enum.value.name.label"/></th>
                                        <th><g:message code="enum.value.description.label"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <g:each var="docEnumValue" in="${DocEnumValue.findAllByDocEnum(docEnum)}">
                                        <tr>
                                            <td>${docEnumValue.name}</td>
                                            <td>${docEnumValue.description}</td>
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