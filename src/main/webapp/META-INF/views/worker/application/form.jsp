<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form>
	<acme:form-textbox code="worker.application.form.label.referenceNumber" path="referenceNumber"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-moment code="worker.application.form.label.moment" path="moment" readonly="true"/>
		<acme:form-textbox code="worker.application.form.label.status" path="status" readonly="true" />
	</jstl:if>
	<acme:form-textbox code="worker.application.form.label.statement" path="statement"/>
	<acme:form-textbox code="worker.application.form.label.skills" path="skills"/>
	<acme:form-textbox code="worker.application.form.label.qualifications" path="qualifications"/>
	<jstl:if test="${command == 'show'}">
		<acme:form-textbox code="worker.application.form.label.job" path="jobRef" />
	</jstl:if>
	<acme:form-submit test="${command == 'create' }"
		code="worker.application.form.button.create"
		action="/worker/application/create?jobId=${id}"/>

	<acme:form-return code="worker.application.form.button.return"/>
</acme:form>