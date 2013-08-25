class UrlMappings {

	static mappings = {

        "/api/group"(controller: "apiGroup", parseRequest:true) {
            action = [POST: "postGroup", GET: "getAllGroups"]
        }

        "/api/group/${groupId}"(controller: "apiSinEntry") {
            action = [POST: "createEntry", GET: "getAllEntries"]
        }

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
