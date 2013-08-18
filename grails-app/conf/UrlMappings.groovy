class UrlMappings {

	static mappings = {

        "/group"(controller: "apiGroup", parseRequest:true) {
            action = [POST: "postGroup", GET: "getAllGroups"]
        }

        "/group/${groupId}"(controller: "apiSinEntry") {
            action = [POST: "createEntry", GET: "getAllEntries"]
        }

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
