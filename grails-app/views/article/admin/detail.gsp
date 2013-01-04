<h1>Article detail</h1>
<g:set var="articleDomain" value="${article?.vars?.article}"/>
<r:form page="${ articleDomain ? routing.mc.getSingleton(article.mc.adminDoUpdateSlug) : routing.mc.getSingleton(article.mc.adminDoCreateSlug)}">	
	<r:textField name="parentFrontPageId" controls="article" value="${routing.mc.getSingleton(article.mc.frontListSlug).id}"/><br />
	<r:textField name="frontPageTypeId" controls="article" value="${routing.mc.getSingletonType(article.mc.frontDetailSlug).id}"/><br />
	<r:textField name="parentAdminPageId" controls="article" value="${routing.mc.getSingleton(article.mc.adminListSlug).id}"/><br />
	<r:textField name="adminPageTypeId" controls="article" value="${routing.mc.getSingletonType(article.mc.adminUpdateSlug).id}"/><br />
	<br />
	<br />
	ID:<r:textField name="id" controls="article" value="${articleDomain?.id }"/><br />
	Name:<r:textField name="name" controls="article" value="${articleDomain?.name }"/><br />
	Body:<r:textField name="body" controls="article" value="${articleDomain?.body }"/><br />
	<g:submitButton name="Save"/><br/>
	<r:link page="${routing.mc.getSingleton(article.mc.adminListSlug)}">Back to article list</r:link>
</r:form>