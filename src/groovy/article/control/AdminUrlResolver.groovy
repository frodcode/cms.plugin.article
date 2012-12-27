package article.control

class AdminUrlResolver {
	
	def pattern
	
	public String getUrlPart = {article.Article article  ->
		return "/article/${article.id}"
	}
//		return pattern.toString().replace('<id>', article.id.toString())

}
