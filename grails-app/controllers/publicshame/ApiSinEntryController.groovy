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
            render ResponseHelper.getResponseForTeamNotFound(response)
        } else if(!group.passphrase?.isEmpty() && request.JSON.passphrase != group.passphrase) {
            render ResponseHelper.getResponseForPassphraseNotCorrect(response)
        } else if(null == entry.sinner) {
            render ResponseHelper.getResponseForError(
                    [ error : "Invalid input", message: "Need to name a sinner."], 403, response)
        } else {
            entry.team = group
            def sinCount = SinEntry.countByTeam(group)

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
            render ResponseHelper.getResponseForTeamNotFound(response)
        } else {
            def resultMap = [
                    name: teamUsed.name,
                    hasPassphrase: !teamUsed.passphrase.isEmpty(),
            ]

            resultMap << getSinnerListForRequest(teamUsed, getStartPosition())
            render resultMap as JSON
        }
    }

    private int getStartPosition() {
        if(null == params.start)
            return 0
        else
            return ((String)params.start).toInteger()
    }

    def getSinnerListForRequest(Team teamUsed, int startLocation) {
        def sinnerList = SinEntryHelper.generateSinnerList(teamUsed, startLocation)

        def returnValue = [
                totalCount: SinEntry.countByTeam(teamUsed),
                size: sinnerList.size(),
                sins: sinnerList,
                refreshLink: getBaseURL() + "?start=${startLocation}"
        ]

        if(sinnerList.size() == SinEntryHelper.MAX_STEP) {
            returnValue <<
                    [ next: getBaseURL() + "?start=${startLocation + SinEntryHelper.MAX_STEP}" ]
        }

        if(startLocation != 0) {
            returnValue <<
                    [ prev: getBaseURL() + "?start=${Math.max(startLocation - SinEntryHelper.MAX_STEP, 0)}" ]
        }

        returnValue
    }

    def deleteEntry() {
        if( params.sinId.isNumber() && SinEntry.findWhere([id: params.sinId as Long]) ) {
            SinEntry.findWhere([id: params.sinId as Long]).delete()
            def response = [ "status" : "OK" ]

            render response as JSON
        } else {
            render ResponseHelper.getResponseForPostNotFount(response)
        }
    }

    def showEntry() {
        if( params.sinId.isNumber()) {
            def sinUsed = SinEntry.findWhere([id: params.sinId as Long])
            if(!sinUsed){
                render ResponseHelper.getResponseForPostNotFount(response)
            } else {
                render SinEntryHelper.createEntryMap(sinUsed) as JSON
            }
        } else {
            render ResponseHelper.getResponseForPostNotFount(response)
        }
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
