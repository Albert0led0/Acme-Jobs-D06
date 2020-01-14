<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message var="reference" code="employer.job.form.ref-placeholder"/>
<acme:form>
	<acme:form-textbox code="employer.job.form.label.reference" path="referenceNumber" placeholder="${reference}"/>
	<acme:form-textbox code="employer.job.form.label.title" path="title"/>
	<acme:form-moment code="employer.job.form.label.deadline" path="deadline"/>
	<acme:form-money code="employer.job.form.label.salary" path="salary"/>
	<acme:form-textbox code="employer.job.form.label.link" path="link"/> 
	<jstl:if test="${command != 'create'}">
		<acme:form-textbox readonly="true" code="employer.job.form.label.status" path="status" />
		<jstl:if test="${status == 'draft'}">
			<acme:form-checkbox code="employer.job.form.label.draft" path="draft"/>
		</jstl:if>
	</jstl:if>
	<acme:form-textarea code="employer.job.form.label.description" path="description"/>

	<acme:form-submit test="${command == 'show'}"
		code="employer.job.form.button.update" action="update"/>
	<acme:form-submit test="${command == 'show'}"
		code="employer.job.form.button.delete" action="delete"/>
	<acme:form-submit test="${command == 'create'}"
		code="employer.job.form.button.create" action="create"/>
	<acme:form-submit test="${command == 'update'}"
		code="employer.job.form.button.update" action="update"/>
	<acme:form-submit test="${command == 'delete'}"
		code="employer.job.form.button.delete" action="delete"/>

  	<acme:form-return code="employer.job.form.button.return"/>
</acme:form>
