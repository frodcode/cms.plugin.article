package article

import domain.routing.Page;

class Article {
	
	String name
	
	String body
	
	Map pages
	
	static hasMany = [pages: Page]

    static constraints = {
    }
	
}
