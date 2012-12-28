import java.awt.ModalEventFilter.ApplicationModalEventFilter;

import org.apache.catalina.core.ApplicationContext;
import org.codehaus.groovy.grails.commons.ApplicationAttributes

import article.Article
import domain.routing.*;

class BootStrap {

	def init = { servletContext ->
		println 'article bootstrap'
		Host defaultHost = new Host(protocol : 'http', domain: 'localhost', port: '8080', domainUrlPart: '/Article')
		defaultHost.save(failOnError:true)
		
		def moduleControls = [
			ModuleControl articleMC = new ModuleControl(
				className: article.control.ArticleModuleControl.class.getName(),
				slug: 'article'
				),
			ModuleControl pageMC = new ModuleControl(
				className: routing.control.RoutingModuleControl.class.getName(),
				slug: 'routing'
				),
			]
		moduleControls*.save();
		def pageTypes = [
			PageType homepagePageType = new PageType(
				slug : 'homepage',
				description: 'Domovská stránka',
				singleton: true,
				templateName: '/article/front/homepage',
				moduleControls: moduleControls,
				registeredCalls : []
				),
			PageType articleListPageType = new PageType(
				slug : 'article_list',
				description: 'Article list',
				singleton: true,
				templateName: '/article/front/articleList',
				moduleControls: moduleControls,
				registeredCalls : [
					new RegisteredCall(
						moduleControl: articleMC,
						methodName: 'getFrontArticleList'
						),
					]
				),
			PageType articleDetailPageType = new PageType(
				slug : 'article_detail',
				description: 'Detail of plugin',
				singleton: false,
				templateName: '/article/front/articleDetail',
				moduleControls: moduleControls,
				registeredCalls : [
					new RegisteredCall(
						moduleControl: articleMC,
						methodName: 'getFrontArticle'
						),
					]
				),
			PageType adminHomepagePageType = new PageType(
				slug : 'admin_homepage',
				description: 'Admin article page',
				singleton: true,
				templateName: '/article/admin/homepage',
				moduleControls: moduleControls,
				registeredCalls : [
					new RegisteredCall(
						moduleControl: articleMC,
						methodName: 'loadArticlesForAdmin'
						),
					]
				),
			PageType adminArticleListPageType = new PageType(
				slug : 'admin_article_list',
				description: 'Admin article page',
				singleton: true,
				templateName: '/article/admin/list',
				moduleControls: moduleControls,
				registeredCalls : [
					new RegisteredCall(
						moduleControl: articleMC,
						methodName: 'loadArticlesForAdmin'
						),
					]
				),
			PageType adminArticleDetailPage = new PageType(
				slug : 'admin_article_detail',
				description: 'Admin article page',
				singleton: true,
				templateName: '/article/admin/detail',
				moduleControls: moduleControls,
				registeredCalls : [
					new RegisteredCall(
						moduleControl: articleMC,
						methodName: 'loadArticleForEdit'
						),
					]
				),
			PageType adminArticleCreatingPage = new PageType(
				slug : 'admin_article_creating',
				description: 'Admin article page',
				singleton: true,
				templateName: '/article/admin/detail',
				moduleControls: moduleControls,
				registeredCalls : [],
			),
			PageType adminArticleSavePage = new PageType(
				slug : 'admin_article_save',
				description: 'Admin article save page',
				singleton: true,
				templateName: '/article/admin/detail',
				moduleControls: moduleControls,
				registeredCalls : [
						new RegisteredCall(
							moduleControl: articleMC,
							methodName: 'saveArticle',
						),
					],
			),
		]
		pageTypes*.save();
		def pages = [
			Page homepage = new Page(
				host: defaultHost,
				urlPart: '/',
				urlType: UrlTypeEnum.FROM_PARENT,
				requestType: RequestTypeEnum.REGULAR,
				httpMethod: HttpMethodEnum.GET,
				pageType: homepagePageType
				),
			
			Page articleList = new Page(
				parent: homepage,
				urlPart: '/article-list',
				pageType: articleListPageType
				),
//			Page contentPlugin = new Page(
//				parent: plugins,
//				urlPart: '/content',
//				pageType: articlePageType
//				),
//			Page routing = new Page(
//				parent: plugins,
//				urlPart: '/routing',
//				pageType: articlePageType
//				),
			
			Page adminHomepage = new Page(
				host: defaultHost,
				urlPart: '/admin',
				urlType: UrlTypeEnum.FROM_PARENT,
				requestType: RequestTypeEnum.REGULAR,
				httpMethod: HttpMethodEnum.GET,
				pageType: adminHomepagePageType
				),
			Page adminArticleList = new Page(
				parent: adminHomepage,
				urlPart: '/articles',
				pageType: adminArticleListPageType
				),
			Page adminArticleCreate = new Page(
				parent: adminHomepage,
				urlPart: '/article/create',
				pageType: adminArticleCreatingPage
				),
			Page adminArticleSave = new Page(
				parent: adminHomepage,
				urlPart: '/article/save',
				pageType: adminArticleSavePage
				),
//			Page adminArticleDetail1 = new Page(
//				parent: adminHomepage,
//				urlPart: '/article/detail/1',
//				pageType: adminArticleDetailPage
//				),
//			Page adminArticleDetail2 = new Page(
//				parent: adminHomepage,
//				urlPart: '/article/detail/2',
//				pageType: adminArticleDetailPage
//				),			
		]
		pages*.save();
		def ctx = servletContext.getAttribute(ApplicationAttributes.APPLICATION_CONTEXT)
		def articleService = ctx.articleService
		articleService.createArticle([name: 'Content', body: 'Content is one of module for content handling'],
			articleList, articleDetailPageType, adminHomepage, adminArticleDetailPage)
		
//		def articles = [
//			Article contentArticle = new Article(pages : ['admin': adminArticleDetail1, 'front' : contentPlugin], name: 'Content', body: 'Content is one of module for content handling'),
//			Article routingArticle = new Article(pages : ['admin': adminArticleDetail2, 'front' : routing], name: 'Routing', body: 'Routing is much different than in other frameworks'),
//		]
//		articles*.save();
	}
}
