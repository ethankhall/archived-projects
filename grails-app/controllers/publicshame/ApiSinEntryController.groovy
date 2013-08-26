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
            def teamNotFound = [
                    error: "Team not found"
            ]
            response.status = 404
            render teamNotFound as JSON
        } else if(!group.passphrase?.isEmpty() && request.JSON.passphrase != group.passphrase) {
            def jsonResponse = [
                    error: "Passphrase invalid"
            ] as JSON
            response.status = 403
            render jsonResponse
        } else if(null == entry.sinner) {
            final def responseMessage = [ error : "Invalid input", message: "Need to name a sinner."]
            response.sendError(400)
            render responseMessage as JSON
        } else {
            entry.team = group
            def sinCount = SinEntry.countByTeam(group)

            if (entry.save(failOnError: true)) {
                def resultMap = [
                        count: sinCount + 1,
                        refreshLink: "http://" + request.serverName + "/api/team/" + params.teamId
                ]
                render resultMap as JSON
            } else {
                log.error(entry.errors)
                render entry.errors
            }
        }
    }

    def saveNewSin(SinEntry entry, int sinCount) {
        if (entry.save(failOnError: true)) {
            def resultMap = [
                    count: sinCount + 1
            ]
            return resultMap
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
                hasPassphrase: !teamUsed.passphrase.isEmpty(),
                refreshLink: "http://" + request.serverName + "/api/team/" + teamUsed.lookup
        ]
        render resultMap as JSON
    }

    def deleteEntry() {
        if( params.sinId.isNumber() && SinEntry.findWhere([id: params.sinId as Long]) ) {
            SinEntry.findWhere([id: params.sinId as Long]).delete()
            def response = [
                    "status" : "OK"
                ]

            render response as JSON
        } else {
            sendPostNotFound()
        }


    }

    def showEntry() {
        if( params.sinId.isNumber()) {
            def sinUsed = SinEntry.findWhere([id: params.sinId as Long])
            if(!sinUsed){
                sendPostNotFound()
            } else {
                render createEntryMap(sinUsed) as JSON
            }
        } else {
            sendPostNotFound()
        }
    }

    private void sendPostNotFound() {
        def errorMessage = [
                error: "Post not found"
        ] as JSON
        response.status = 404
        render errorMessage
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
        createEntryMap(sinUsed) << [refreshLink: request.getRequestURL()]
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
