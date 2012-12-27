package article

import domain.routing.Page
import domain.routing.PageType
import routing.RoutingService

class ArticleService {
	
	def routingService
	
	def getAdminUrlPart = {Article article  ->
		return "/article/${article.id}"
	}
	
	def createFrontPageClosure = { Article article, Page parentPage, String pageTypeSlug, RoutingService routingService ->	
		def frontPage = new Page(
			parent: parentPage,
			pageType: PageType.findBySlug(pageTypeSlug)
			);
		frontPage.setUrlPartFromText(article.name)
		frontPage.save()
		article.pages += ['front':frontPage]
	};

	def createAdminPageClosure = { Article article, Page parentPage, String pageTypeSlug, RoutingService routingService ->
		def adminPage = new Page(
			parent: parentPage,
			pageType: PageType.findBySlug(pageTypeSlug),
			urlPart: getAdminUrlPart(article)
			);
		adminPage.save();
		article.pages += ['admin':adminPage]
	}
	
    def createArticle(def moduleRequest, Page parentFrontPage, String frontPageTypeSlug, Page parentAdminPage, String adminPageTypeSlug) {
		def newArticle = new Article(moduleRequest)
		newArticle.save()
		newArticle.pages = [:]
		this.createFrontPageClosure(newArticle, parentFrontPage, frontPageTypeSlug, this.routingService)
		this.createAdminPageClosure(newArticle, parentAdminPage, adminPageTypeSlug, this.routingService)		
	}
}
