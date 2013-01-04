package article.control

import routing.RoutingService
import article.Article
import article.ArticleService
import domain.routing.Page
import domain.routing.PageType

class ArticleModuleControl {

	ArticleService articleService;

	RoutingService routingService;

	def frontListSlug = 'article_list'
	def frontDetailSlug = 'article_detail'

	def adminCreateSlug = 'article_admin_create'
	def adminListSlug = 'article_admin_list'
	def adminUpdateSlug = 'article_admin_update'
	def adminDoCreateSlug = 'article_admin_do_create'
	def adminDoUpdateSlug = 'article_admin_do_update'
	def adminDoDeleteSlug = 'article_admin_do_delete'

	def onArticleCreate = { moduleResponse, moduleRequest, page ->
		Page redirectPage = routingService.getSingleton(adminCreateSlug);
		moduleResponse.addFlash('message', 'Article has been added')
		moduleResponse.addRedirect(redirectPage)
	}

	def onArticleUpdate = { moduleResponse, moduleRequest, page ->
		moduleResponse.addFlash('message', 'Article has been added')
		moduleResponse.addRedirect(Article.get(moduleRequest.id.toLong()).pages['admin'])
	}

	def onArticleDelete = { moduleResponse, moduleRequest, page ->
		moduleResponse.addFlash('message', 'Article has been deleted')
		moduleResponse.addRedirect(routingService.getSingleton(adminListSlug))
	}

	public def getFrontArticleList(def page, def moduleRequest, def moduleResponse) {
		def articles = Article.findAll();
		return [articles:articles];
	}

	public def getFrontArticle(def page, def moduleRequest, def moduleResponse) {
		def article = Article.where{
			pages { id == page.id }
		}.find();
		return ['article' : article]
	}

	public def loadArticleForEdit(def page, def moduleRequest, def moduleResponse) {
		def article = Article.where{
			pages { id == page.id }
		}.find();
		return ['article' : article]
	}

	public def loadArticlesForAdmin(def page, def moduleRequest, def moduleResponse) {
		return [articles: Article.findAll()]
	}

	public def updateArticle(def page, def moduleRequest, def moduleResponse) {
		def article = Article.get(moduleRequest.id.toLong());
		article.properties = moduleRequest
		article.save()
		moduleResponse.addCall(this.onArticleUpdate)
		return [:]
	}

	public def createArticle(def page, def moduleRequest, def moduleResponse) {
		//def moduleRequest, Page parentFrontPage, PageType frontPageType, Page parentAdminPage, PageType adminPageType
		articleService.createArticle(moduleRequest,
				Page.findById(moduleRequest.parentFrontPageId.toLong()),
				PageType.findById(moduleRequest.frontPageTypeId),
				Page.findById(moduleRequest.parentAdminPageId),
				PageType.findById(moduleRequest.adminPageTypeId))
		moduleResponse.addCall(this.onArticleCreate)
		return [:]
	}

	public def deleteArticle(def page, def moduleRequest, def moduleResponse) {
		def article = Article.get(moduleRequest.id.toLong());
		article.delete()
		moduleResponse.addCall(this.onArticleDelete)
		return [:]
	}
}
