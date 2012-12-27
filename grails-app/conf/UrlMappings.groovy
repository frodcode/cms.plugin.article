class UrlMappings {

	static mappings = {
		"/**"{
			controller = "Front"
			action = "route"
			constraints {
				// apply constraints here
			}
		}
		"500"(view:'/error')
	}
}
	