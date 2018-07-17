<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <asset:stylesheet src="bootstrap.css"/>
    <asset:stylesheet src="bootstrap-select.css"/>
    <asset:stylesheet src="jquery.timepicker.css"/>
    <asset:stylesheet src="menu.css"/>
</head>
<body>
<%@ page import="org.irblleida.dc.DocClass" %>
<%@ page import="org.irblleida.dc.DocEnum" %>
<%@ page import="org.irblleida.dc.DocEnumValue" %>

<div class="container-fluid">
    <div class="row">
        <div class="sidebar">
            <form class="form-inline my-2 my-lg-0 form-group">
                <input id="menu-search" class="form-control mr-sm-2" type="search" placeholder="${g.message(code: 'menu.search.label')}" aria-label="${g.message(code: 'menu.search.label')}">
            </form>
            <h5 class="mt-3"><g:message code="domain.label"/></h5>
            <ul id="classes-list" class="nav nav-sidebar">
                <g:each var="docClass" in="${docClassList}">
                    <li><a href="#" onclick="moveTo('${docClass.name.uncapitalize()}')" class="left-menu ${docClass.name.uncapitalize()}">${docClass.name}</a></li>
                </g:each>
            </ul>
            <h5 class="mt-3"><g:message code="enum.label"/></h5>
            <ul id="enums-list" class="nav nav-sidebar">
                <g:each var="docEnum" in="${docEnumList}">
                    <li><a href="#" onclick="moveTo('${docEnum.name.uncapitalize()}')" class="left-menu ${docEnum.name.uncapitalize()}">${docEnum.name}</a></li>
                </g:each>
            </ul>
        </div>

        <div class="col-sm-12 main mt-4">
            <h2 class="text-center"><g:message code="plugin.title"/></h2>
            <div class="container">

                <g:if test="${flash.message}">
                    <div class="alert alert-success" role="alert">
                        <div class="message" role="status">${flash.message}</div>
                    </div>
                </g:if>
                <g:hasErrors bean="${this.docClass}">
                    <div class="alert alert-warning" role="alert">
                        <g:eachError bean="${this.docClass}" var="error">
                            <p <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></p>
                        </g:eachError>
                    </div>
                </g:hasErrors>

                <div class="card mt-5">
                    <div class="card-header">
                        <span><g:message code="plugin.title"/> (<g:message code="domain.label"/>)</span>
                    </div>
                    <div class="card-body">
                        <div class="row justify-content-md-center">
                            <g:each var="docClass" in="${docClassList}">
                                <div class="col-md-12">
                                    <div id="${docClass.name.uncapitalize()}" class="card mt-4">
                                        <div class="card-header">
                                            <span>${docClass.name}</span>
                                            <g:link controller="docClass" action="edit" id="${docClass?.id}" class="btn btn-warning float-right">
                                                <i class="far fa-edit"></i>
                                                <g:message code="default.button.edit.label" />
                                            </g:link>
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
                                                <g:each var="docVariable" in="${docClass.variables}">
                                                    <tr>
                                                        <td>${docVariable.name}</td>
                                                        <td><g:message code="${docClass.name.uncapitalize()}.${docVariable.name}.label" default=""/></td>
                                                        <td>${docVariable.description}</td>
                                                        <td>
                                                            <g:set var="type" value="${DocClass.findByName(docVariable.type)}"/>
                                                            <g:if test="${type}">
                                                                <a href="#" onclick="moveTo('${docVariable.type.uncapitalize()}')">${docVariable.type}</a>
                                                            </g:if><g:else>
                                                            <g:set var="type" value="${DocEnum.findByName(docVariable.type)}"/>
                                                            <g:if test="${type}">
                                                                <a href="#" onclick="moveTo('${docVariable.type.uncapitalize()}')">${docVariable.type}</a>
                                                            </g:if>
                                                            <g:else>
                                                                ${docVariable.type}
                                                            </g:else>
                                                        </g:else>
                                                        </td>
                                                        <td>${docVariable.code}</td>
                                                        <td><g:message code="default.boolean.${docVariable.required}"/></td>
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
                                    <div id="${docEnum.name.uncapitalize()}" class="card mt-4">
                                        <div class="card-header">
                                            <span>${docEnum.name}</span>
                                            <g:link controller="docEnum" action="edit" id="${docEnum?.id}" class="btn btn-warning float-right">
                                                <i class="far fa-edit"></i>
                                                <g:message code="default.button.edit.label" />
                                            </g:link>
                                        </div>
                                        <div class="card-body">
                                            ${docEnum.description}
                                            <br/>
                                            <g:message code="enum.context.label"/>:
                                            <g:each var="context" in="${docEnum.contexts}">
                                                <a href="#" onclick="moveTo('${context.name.uncapitalize()}')">${context.name}</a>
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
                                                <g:each var="docEnumValue" in="${docEnum.values}">
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
        </div>
    </div>
</div>

<asset:javascript src="jquery-3.3.1.min.js"/>
<asset:javascript src="bootstrap.js"/>
<asset:javascript src="fontawesome-all.js"/>
<asset:javascript src="jquery.are-you-sure.js"/>
<g:javascript>
    function moveTo(id) {
        $("html, body").animate({scrollTop: $('#' + id).offset().top}, 1500);
    }

    $('#menu-search').on("keyup", function () {
        var value = $(this).val().toLowerCase();

        $('.left-menu').hide();
        $("a[class*='" + value + "']").show();

        if (value === '') $('.left-menu').show();
    });
</g:javascript>
</body>
</html>