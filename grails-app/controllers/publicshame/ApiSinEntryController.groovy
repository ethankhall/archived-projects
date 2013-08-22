package publicshame

import grails.converters.JSON

class ApiSinEntryController {

    def createEntry() {
        def entry = new SinEntry(request.JSON)
        def group = Team.findWhere([lookup: params.teamId as String])
        if(!group) {
            response.sendError(404)
            return
        } else if(!group.passphrase?.isEmpty() && request.JSON.passphrase != group.passphrase) {
            response.sendError(403)
            return
        }

        entry.group = group
        def sinCount = SinEntry.countByGroup(group)

        if(entry.save(failOnError: true)) {
            def resultMap = [
                    count: sinCount + 1
            ]
            render resultMap as JSON
        } else {
            log.error(entry.errors)
            render entry.errors
        }
    }

    def getAllEntries() {
        def teamUsed = Team.findWhere([lookup: params.teamId])
        if(!teamUsed){
            response.sendError(404)
            return;
        }

        def sinnerList = generateSinnerList(teamUsed)
        def resultMap = [
                name: teamUsed.name,
                count: sinnerList.size(),
                sins: sinnerList,
                refreshLink: request.getRequestURL(),
        ]
        render resultMap as JSON
    }

    def generateSinnerList(Team teamUsed) {
        def sinnerList = []
        SinEntry.findAllWhere([group: teamUsed]).each {
            def map = [
                    sinner: it.sinner,
                    sin: it.sin
            ]
            if (!it.misc.isEmpty())
                map.put("misc", it.misc)

            sinnerList.add(map)
        }

        return sinnerList
    }
}
