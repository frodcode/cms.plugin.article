<h1>List of articles</h1>
<r:link page="${routing.mc.getSingleton(article.mc.adminCreateSlug)}">Create new</r:link>
<ul>
	<g:each in="${ article.vars.articles}" var="a">
		<li><r:link page="${a.pages['admin']}">${a.name }</r:link>  <r:link page="${routing.mc.getSingleton(article.mc.adminDoDeleteSlug)}" params="${[article : [id : a.id ]]}">delete</r:link></li>
	</g:each>
</ul>