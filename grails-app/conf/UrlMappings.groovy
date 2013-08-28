class UrlMappings {

	static mappings = {

        "/api/team"(controller: "apiGroup", parseRequest:true) {
            action = [POST: "postGroup", GET: "getAllGroups"]
        }

        "/api/team/${teamId}"(controller: "apiSinEntry") {
            action = [POST: "createEntry", GET: "getAllEntries"]
        }

        "/api/post/${sinId}"(controller: "apiSinEntry") {
            action = [ DELETE: "deleteEntry", GET: "showEntry"]
        }

        "/seed"(controller: "apiQa") {
            action: [ GET: "seed"]
        }

        "/api/team/update/${teamId}"(controller: "apiTimeBasedGroup") {
            action: [GET: "getSinceLastTime" ]
        }

		"500"(view:'/error')
	}
}
