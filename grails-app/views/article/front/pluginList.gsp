<h1>Plugin list</h1>
<ul>
<g:each in="${ article.vars.articles}" var="a">
	<li><a href="${a.pages['front'].url }">${a.name }</a></li>
</g:each>
</ul>