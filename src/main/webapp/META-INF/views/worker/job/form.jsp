<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="worker.job.form.label.referenceNumber" path="referenceNumber"/>
	<acme:form-textbox code="worker.job.form.label.title" path="title"/>
	<acme:form-moment code="worker.job.form.label.deadline" path="deadline"/>
	<acme:form-money code="worker.job.form.label.salary" path="salary"/>
	<acme:form-textbox code="worker.job.form.label.link" path="link"/>
	<acme:form-textarea code="worker.job.form.label.description" path="description"/>
	
	<acme:form-return code="worker.job.form.button.application.create" action="/worker/application/create?jobId=${id}"/>
	
	<acme:form-return code="worker.job.form.button.return"/>
</acme:form>