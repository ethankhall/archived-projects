package publicshame

import grails.converters.JSON

class ApiTimeBasedGroupController {

    def getSinceLastTime() {
        String teamId = params.teamId
        def teamFound = Team.findByLookup(teamId)
        if(!teamFound) {
            render ResponseHelper.getResponseForTeamNotFound(response)
        } else if( !params.timestamp ){
            render ResponseHelper.getResponseForError([error: "Missing Timestamp"], 400, response)
        } else if( !((String)params.timestamp).isNumber() ){
            render ResponseHelper.getResponseForError([error: "Timestamp Invalid"], 400, response)
        } else {
            render getNewPostsSinceTimestamp(teamFound, ((String)params.timestamp).toInteger())
        }
    }

    JSON getNewPostsSinceTimestamp(Team team, Integer timestamp) {
        def responseObj = [ sins: SinEntry.findAllByTeamAndCreatedDateGreaterThan(team, new Date(timestamp)) ]
        responseObj << [ refreshLink: RequestHelper.getBaseURL(request) ]
        return responseObj as JSON
    }
}
