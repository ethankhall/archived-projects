package publicshame

import grails.converters.JSON

class ApiSinEntryController {

    def createEntry() {
        def entry = new SinEntry(request.JSON)
        def group = Group.findWhere([lookup: params.groupId as String])
        entry.group = group
        if(entry.save()) {
            def resp =
                [
                        count: SinEntry.countByGroup(group)
                ]
            render resp as JSON
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
        render jsonResponse as JSON
    }
}
