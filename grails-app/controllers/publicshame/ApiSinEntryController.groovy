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
        if(entry.save(failOnError: true)) {
            def resultMap = [
                    count: SinEntry.countByGroup(group)
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
            jsonResponse.add([
                    sinner: it.sinner,
                    sin: it.sin,
                    misc: it.misc
            ])
        }
        def resultMap = [
                count: jsonResponse.size(),
                sins: jsonResponse
        ]
        render resultMap as JSON
    }
}
