import domain.routing.Page;
import domain.routing.PageType;
import domain.routing.*;

class ArticleGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Article Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/article"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
       'article.control.ArticleModuleControl'(article.control.ArticleModuleControl) {
		   articleService = ref('articleService')
		   routingService = ref('routingService')
	   }
	   articleService(article.ArticleService) {
		   routingService = ref('routingService')
	   }
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
	
	static def loadFixtures(def ctx, def defaultHost, def routingModuleControl, def frontParent, def adminParent) {
		def articleMCInstance = ctx.'article.control.ArticleModuleControl'
		
		def moduleControls = [
			articleMC : new ModuleControl(
				className: article.control.ArticleModuleControl.class.getName(),
				slug: 'article'
				),
		]
		moduleControls*.value*.save()
		moduleControls += [routingModuleControl:routingModuleControl]
		
		def pageTypes = [
			articleListPageType : new PageType(
				slug : articleMCInstance.frontListSlug,
				description: 'Article list',
				singleton: true,
				templateName: '/article/front/articleList',
				moduleControls: moduleControls.values(),
				registeredCalls : [
					new RegisteredCall(
						moduleControl: moduleControls.articleMC,
						methodName: 'getFrontArticleList'
						),
					]
				),
			articleDetailPageType : new PageType(
				slug : articleMCInstance.frontDetailSlug,
				description: 'Detail of plugin',
				singleton: false,
				templateName: '/article/front/articleDetail',
				moduleControls: moduleControls.values(),
				registeredCalls : [
					new RegisteredCall(
						moduleControl: moduleControls.articleMC,
						methodName: 'getFrontArticle'
						),
					]
				),
			
			adminArticleListPageType : new PageType(
				slug : articleMCInstance.adminListSlug,
				description: 'Admin article page',
				singleton: true,
				templateName: '/article/admin/list',
				moduleControls: moduleControls.values(),
				registeredCalls : [
					new RegisteredCall(
						moduleControl: moduleControls.articleMC,
						methodName: 'loadArticlesForAdmin'
						),
					]
				),
			adminArticleDetailPageType : new PageType(
				slug : articleMCInstance.adminUpdateSlug,
				description: 'Admin article page',
				singleton: true,
				templateName: '/article/admin/detail',
				moduleControls: moduleControls.values(),
				registeredCalls : [
					new RegisteredCall(
						moduleControl: moduleControls.articleMC,
						methodName: 'loadArticleForEdit'
						),
					]
				),
			adminArticleCreatePageType : new PageType(
				slug : articleMCInstance.adminCreateSlug,
				description: 'Admin article page',
				singleton: true,
				templateName: '/article/admin/detail',
				moduleControls: moduleControls.values(),
				registeredCalls : [],
			),
			adminArticleDoCreatePageType : new PageType(
				slug : articleMCInstance.adminDoCreateSlug,
				description: 'Admin article create page after submit',
				singleton: true,
				templateName: '/article/admin/detail',
				moduleControls: moduleControls.values(),
				registeredCalls : [
						new RegisteredCall(
							moduleControl: moduleControls.articleMC,
							methodName: 'createArticle',
						),
					],
			),
			adminArticleDoUpdatePageType : new PageType(
				slug : articleMCInstance.adminDoUpdateSlug,
				description: 'Admin article update page after submit',
				singleton: true,
				templateName: '/article/admin/detail',
				moduleControls: moduleControls.values(),
				registeredCalls : [
						new RegisteredCall(
							moduleControl: moduleControls.articleMC,
							methodName: 'updateArticle',
						),
					],
			),
			adminArticleDoDeletePageType : new PageType(
				slug : articleMCInstance.adminDoDeleteSlug,
				description: 'Admin article delete page',
				singleton: true,
				templateName: '/article/admin/list',
				moduleControls: moduleControls.values(),
				registeredCalls : [
						new RegisteredCall(
							moduleControl: moduleControls.articleMC,
							methodName: 'deleteArticle',
						),
					],
			),
		]
		pageTypes*.value*.save()
		
		def pages = [			
			articleList : new Page(
				parent: frontParent,
				urlPart: '/article-list',
				pageType: pageTypes.articleListPageType
				),
			adminArticleList : new Page(
				parent: adminParent,
				urlPart: '/articles',
				pageType: pageTypes.adminArticleListPageType
				),
			adminArticleCreate : new Page(
				parent: adminParent,
				urlPart: '/article/create',
				pageType: pageTypes.adminArticleCreatePageType
				),
			adminArticleDoCreate : new Page(
				parent: adminParent,
				urlPart: '/article/do-create',
				pageType: pageTypes.adminArticleDoCreatePageType
				),
			adminArticleDoUpdate : new Page(
				parent: adminParent,
				urlPart: '/article/do-update',
				pageType: pageTypes.adminArticleDoUpdatePageType
				),
			adminArticleDoDelete : new Page(
				parent: adminParent,
				urlPart: '/article/do-delete',
				pageType: pageTypes.adminArticleDoDeletePageType
				),
		]
		
		pages*.value*.save()
		
		return [pageTypes: pageTypes, pages : pages, moduleControls : moduleControls]
	}
	
	static def loadExampleData(def ctx, Page parentFrontPage, PageType frontPageType, Page parentAdminPage, PageType adminPageType) {			
		def articleService = ctx.articleService
		articleService.createArticle([name: 'Content', body: 'Content is one of module for content handling'],
			parentFrontPage, frontPageType, parentAdminPage, adminPageType)
	}
}
