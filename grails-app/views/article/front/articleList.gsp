<h1>Plugin list</h1>
<ul>
<g:each in="${ article.vars.articles}" var="a">
	<li><r:link page="${a.pages['front'] }">${a.name }</r:link></li>
</g:each>
</ul>