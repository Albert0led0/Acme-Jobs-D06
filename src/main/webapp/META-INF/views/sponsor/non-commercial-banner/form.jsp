<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
    <acme:form-url code="sponsor.non-commercial-banner.form.label.picture-url" path="pictureURL"/>
	<acme:form-textbox code="sponsor.non-commercial-banner.form.label.slogan" path="slogan"/>	
	<acme:form-url code="sponsor.non-commercial-banner.form.label.target-url" path="targetURL"/>
	<acme:form-textbox code="sponsor.non-commercial-banner.form.label.jingle" path="jingle"/>
	
	<acme:form-submit test="${command == 'show'}" 
		code="sponsor.non-commercial-banner.form.button.update"
		action="/sponsor/non-commercial-banner/update" />
		
	<acme:form-submit test="${command == 'show'}" 
		code="sponsor.non-commercial-banner.form.button.delete"
		action="/sponsor/non-commercial-banner/delete" />
		
	<acme:form-submit test="${command == 'create'}" 
		code="sponsor.non-commercial-banner.form.button.create"
		action="/sponsor/non-commercial-banner/create" />
	
	<acme:form-submit test="${command == 'update'}" 
		code="sponsor.non-commercial-banner.form.button.update"
		action="/sponsor/non-commercial-banner/update" />
		
	<acme:form-submit test="${command == 'delete'}" 
		code="sponsor.non-commercial-banner.form.button.delete"
		action="/sponsor/non-commercial-banner/delete" />
	
  	<acme:form-return code="sponsor.non-commercial-banner.form.button.return"/>
</acme:form>
