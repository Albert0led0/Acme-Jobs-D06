<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="worker.job.list.referenceNumber" path="referenceNumber" width="20%"/>
	<acme:list-column code="worker.job.list.title" path="title" width="20%"/>
	<acme:list-column code="worker.job.list.deadline" path="deadline" width="20%"/>
</acme:list>