package article.control

import article.Article;
import article.ArticleService;
import domain.routing.Page
import domain.routing.PageType;
import routing.RoutingService

class ArticleModuleControl {	
	
	ArticleService articleService;
	
	RoutingService routingService;
	
	def onArticleCreate = { controlResponse ->
		Page redirectPage = routingService.getSingleton('admin_article_creating');
		controlResponse.addFlash('message', 'Article has been added')
		controlResponse.addRedirect(redirectPage)
	}
	
	public def getFrontArticleList(def page, def moduleRequest, def moduleResponse) {
		def articles = Article.findAll();
		return [articles:articles];
	}
	
	public def getFrontArticle(def page, def moduleRequest, def moduleResponse) {
		def article = Article.where{
					pages {
						id == page.id
					}
				}.find();
		return ['article' : article]
	}
	
	public def loadArticleForEdit(def page, def moduleRequest, def moduleResponse) {
		def article = Article.where{
			pages {
				id == page.id
			}
		}.find();
		return ['article' : article]
	}
	
	public def loadArticlesForAdmin(def page, def moduleRequest, def moduleResponse) {
		return [articles: Article.findAll()]
	}
	
	public def saveArticle(def page, def moduleRequest, def moduleResponse) {
		//def moduleRequest, Page parentFrontPage, PageType frontPageType, Page parentAdminPage, PageType adminPageType
		articleService.createArticle(moduleRequest, 
			Page.findById(moduleRequest.parentFrontPageId.toLong()), 
			PageType.findById(moduleRequest.frontPageTypeId), 
			Page.findById(moduleRequest.parentAdminPageId), 
			PageType.findById(moduleRequest.adminPageTypeId))
		moduleResponse.addCall(this.onArticleCreate)
		return [:]
	}

}
