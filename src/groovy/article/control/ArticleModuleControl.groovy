package article.control

import article.Article;
import domain.routing.Page

class ArticleModuleControl {	
	
	def adminUrlResolver;
	
	public def getFrontArticleList(def page, def moduleRequest) {
		def articles = Article.findAll();
		return [articles:articles];
	}
	
	public def getFrontArticle(def page, def moduleRequest) {
		def article = Article.where{
					pages {
						id == page.id
					}
				}.find();
		return ['article' : article]
	}
	
	public def loadArticleForEdit(def page, def moduleRequest) {
		def article = Article.where{
			pages {
				id == page.id
			}
		}.find();
		return ['article' : article]
	}
	
	public def loadArticlesForAdmin(def page, def moduleRequest) {
		return [articles: Article.findAll()]
	}
	
	public def createArticle(def page, def moduleRequest) {
		def newArticle = new Article(moduleRequest)
		def articlePage = new Page(moduleRequest['page']);
		
		def parentAdminPage = Page.find(moduleRequest['parentAdminPageId']);
		def adminPage = new Page(urlPart: adminUrlResolver.getUrlPart(newArticle), parent: parentAdminPage, pageType: adminPageType)
		
		newArticle.addToPages(articlePage);
		newArticle.addToPages(adminPage);
		newArticle.save()
	}

}
