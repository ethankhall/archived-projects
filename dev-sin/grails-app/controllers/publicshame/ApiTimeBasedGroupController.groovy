/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Ethan Hall
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 *  copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ******************************************************************************/

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
            reuseId = reuseId < it.id ? it.id : reuseId
        }

        def responseObj = [ sins: sinList]
        responseObj << [ refreshLink: RequestHelper.getBaseURL(request) + "?id=${reuseId}" ]
        return responseObj as JSON
    }
}
