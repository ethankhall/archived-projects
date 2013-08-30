package publicshame

import grails.converters.JSON

class ApiTimeBasedGroupController {

    def getSinceLastTime() {
        String teamId = params.teamId
        def teamFound = Team.findByLookup(teamId)
        if(!teamFound) {
            render ResponseHelper.getResponseForTeamNotFound(response)
        } else if( !params.id ){
            render ResponseHelper.getResponseForError([error: "Missing ID"], 400, response)
        } else if( !((String)params.id).isNumber() ){
            render ResponseHelper.getResponseForError([error: "Invalid ID"], 400, response)
        } else {
            render getNewPostsSinceLastPost(teamFound, ((String)params.id).toInteger())
        }
    }

    JSON getNewPostsSinceLastPost(Team team, Integer lastId) {
        def sinList = []
        def reuseId = lastId
        SinEntry.findAllByTeamAndIdGreaterThan(team, lastId).each {
            sinList << SinEntryHelper.createEntryMap(it)
        }

        if(sinList.size() != 0) {
            reuseId = sinList[sinList.size() - 1].id
        }

        def responseObj = [ sins: sinList]
        responseObj << [ refreshLink: RequestHelper.getBaseURL(request) + "?id=${reuseId}" ]
        return responseObj as JSON
    }
}
