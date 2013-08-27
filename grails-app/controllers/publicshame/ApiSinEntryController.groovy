package publicshame

import grails.converters.JSON

import java.security.MessageDigest

class ApiSinEntryController {

    private static final int MAX_STEP = 15

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
            processImage(entry)

            if (entry.save(failOnError: true)) {
                def resultMap = [
                        count: sinCount + 1,
                        refreshLink: getBaseURL()
                ]
                render resultMap as JSON
            } else {
                log.error(entry.errors)
                render entry.errors
            }
        }
    }

    def processImage(SinEntry sinEntry) {
        def f = request.getFile('image')
        if (!f.empty) {
            def messageDigest = MessageDigest.getInstance("SHA1")
            messageDigest.update(f)
            def filename = new BigInteger(1, messageDigest.digest()).toString(16).padLeft( 40, '0' )
            def newFile = new File("${filename}.png")
            f.transferTo(newFile)
            sinEntry.pathToFile = newFile.getPath()
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
        getBaseURL()
        def teamUsed = Team.findWhere([lookup: params.teamId])
        if(!teamUsed){
            response.sendError(404)
            return;
        }
        def resultMap = [
                name: teamUsed.name,
                hasPassphrase: !teamUsed.passphrase.isEmpty(),
        ]

        resultMap << getSinnerListForRequest(teamUsed, getStartPosition())
        render resultMap as JSON
    }

    private int getStartPosition() {
        if(null == params.start)
            return 0
        else
            return ((String)params.start).toInteger()
    }

    def getSinnerListForRequest(Team teamUsed, int startLocation) {
        def sinnerList = generateSinnerList(teamUsed, startLocation)

        def returnValue = [
                totalCount: SinEntry.countByTeam(teamUsed),
                size: sinnerList.size(),
                sins: sinnerList,
                refreshLink: getBaseURL() + "?start=${startLocation}"
        ]

        if(sinnerList.size() == MAX_STEP) {
            returnValue <<
                    [ next: getBaseURL() + "?start=${startLocation + MAX_STEP}" ]
        }

        if(startLocation != 0) {
            returnValue <<
                    [ prev: getBaseURL() + "?start=${Math.max(startLocation - MAX_STEP, 0)}" ]
        }

        returnValue
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
    def generateSinnerList(Team teamUsed, int startPosition) {
        def sinnerList = []
        SinEntry.findAllByTeam(teamUsed,  [max: MAX_STEP, offset: startPosition]).each {
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

    def getBaseURL() {
        def responseUrl = "http://" + request.getServerName()
        if(80 != request.getServerPort()){
            responseUrl += ":${request.getServerPort()}"
        }
        responseUrl += "${request.forwardURI}"
        return responseUrl
    }
}
