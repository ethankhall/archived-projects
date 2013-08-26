package publicshame

import grails.converters.JSON

class ApiSinEntryController {

    /**
     * Create a new sin. If the team doesn't exists return a 404
     */
    def createEntry() {
        def entry = new SinEntry(request.JSON)
        def group = Team.findWhere([lookup: params.teamId as String])
        if(!group) {
            response.sendError(404)
        } else if(!group.passphrase?.isEmpty() && request.JSON.passphrase != group.passphrase) {
            response.sendError(403)
        } else if(null == entry.sinner) {
            final def responseMessage = [ error : "Invalid input", message: "Need to name a sinner."]
            response.sendError(400)
            render responseMessage as JSON
        } else {
            entry.team = group
            def sinCount = SinEntry.countByTeam(group)

            render saveNewSin(entry, sinCount)
        }
    }

    def saveNewSin(SinEntry entry, int sinCount) {
        if (entry.save(failOnError: true)) {
            def resultMap = [
                    count: sinCount + 1
            ]
            return resultMap as JSON
        } else {
            log.error(entry.errors)
            return entry.errors
        }
    }

    /**
     * Get all the sin entries for this team.
     *
     * IF the team doesn't exists return 404
     *
     */
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

    def deleteEntry() {
        def sinUsed = SinEntry.findWhere([lookup: params.sinId])
        if(!sinUsed){
            def errorMessage = [
                    error: "Post not found"
            ] as JSON
            response.sendError(404, errorMessage as String)
            return
        }

        sinUsed.delete()

        def response = [
                "status" : "OK"
        ]

        render response as JSON
    }

    def showEntry() {
        def sinUsed = SinEntry.findWhere([lookup: params.sinId])
        if(!sinUsed){
            def errorMessage = [
                    error: "Post not found"
            ] as JSON
            response.sendError(404, errorMessage as String)
        } else {
            render createEntryMapWithRefreshLink(sinUsed) as JSON
        }
    }

    /**
     * This method will take a team and create a list of all sins
     *
     * @param teamUsed
     * @return list of all sins assigned no this team
     *
     */
    def generateSinnerList(Team teamUsed) {
        def sinnerList = []
        SinEntry.findAllWhere([team: teamUsed]).each {
            sinnerList << createEntryMap(it)
        }

        return sinnerList
    }

    def createEntryMapWithRefreshLink(SinEntry sinUsed) {
        def result = createEntryMap(sinUsed)
        result << [refreshLink: request.getRequestURL()]
        result
    }

    def createEntryMap(SinEntry sinUsed) {
        def resultMap = [
                sinner: sinUsed.sinner,
                sin: sinUsed.sin,
                id: sinUsed.id,
        ]

        if (sinUsed.misc)
            resultMap <<  ["misc", sinUsed.misc]
        resultMap
    }
}
