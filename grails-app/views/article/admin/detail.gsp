<h1>Article detail</h1>
<r:form page="${routing.mc.getSingleton('admin_article_save')}">
	<r:textField name="parentFrontPageId" controls="article" value="${routing.mc.getSingleton('article_list').id}"/><br />
	<r:textField name="frontPageTypeId" controls="article" value="${routing.mc.getSingletonType('article_detail').id}"/><br />
	<r:textField name="parentAdminPageId" controls="article" value="${routing.mc.getSingleton('admin_article_list').id}"/><br />
	<r:textField name="adminPageTypeId" controls="article" value="${routing.mc.getSingletonType('admin_article_detail').id}"/><br />
	<br />
	<br />
	<r:textField name="name" controls="article"/><br />
	<r:textField name="body" controls="article"/><br />
	<g:submitButton name="Save"/>
</r:form>