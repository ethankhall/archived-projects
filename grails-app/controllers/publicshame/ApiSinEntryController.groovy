package publicshame

import grails.converters.JSON

class ApiSinEntryController {

    def createEntry() {
        def entry = new SinEntry(request.JSON)
        def group = Group.findWhere([lookup: params.groupId as String])

        if(!group.passphrase?.isEmpty() && request.JSON.passphrase != group.passphrase) {
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
        def jsonResponse = []
        SinEntry.findAllWhere([ group: Group.findWhere([ lookup: params.groupId ]) ]).each {
            def map = [
                    sinner: it.sinner,
                    sin: it.sin
            ]
            if(!it.misc.isEmpty())
                map.put("misc", it.misc)

            jsonResponse.add(map)
        }
        def resultMap = [
                count: jsonResponse.size(),
                sins: jsonResponse
        ]
        render resultMap as JSON
    }
}
