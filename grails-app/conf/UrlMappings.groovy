class UrlMappings {

	static mappings = {

        "/api/group"(controller: "apiGroup", parseRequest:true) {
            action = [POST: "postGroup", GET: "getAllGroups"]
        }

        "/api/group/${teamId}"(controller: "apiSinEntry") {
            action = [POST: "createEntry", GET: "getAllEntries"]
        }

        "/api/post/${sinId}"(controller: "apiSinEntry") {
            action = [ DELETE: "deleteEntry", GET: "showEntry"]
        }

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
