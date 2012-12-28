package article

import domain.routing.Page
import domain.routing.PageType
import routing.RoutingService

class ArticleService {
	
	def routingService
	
	def getAdminUrlPart = {Article article  ->
		return "/article/${article.id}"
	}
	
	def createFrontPageClosure = { Article article, Page parentPage, PageType pageType, RoutingService routingService ->	
		def frontPage = new Page(
			parent: parentPage,
			pageType: pageType,
			);
		frontPage.setUrlPartFromText(article.name)
		frontPage.save()
		article.pages += ['front':frontPage]
	};

	def createAdminPageClosure = { Article article, Page parentPage, PageType pageType, RoutingService routingService ->
		def adminPage = new Page(
			parent: parentPage,
			pageType: pageType,
			urlPart: getAdminUrlPart(article)
			);
		adminPage.save();
		article.pages += ['admin':adminPage]
	}
	
    def createArticle(def moduleRequest, Page parentFrontPage, PageType frontPageType, Page parentAdminPage, PageType adminPageType) {
		def newArticle = new Article(moduleRequest)
		newArticle.save()
		newArticle.pages = [:]
		this.createFrontPageClosure(newArticle, parentFrontPage, frontPageType, this.routingService)
		this.createAdminPageClosure(newArticle, parentAdminPage, adminPageType, this.routingService)		
	}
}
