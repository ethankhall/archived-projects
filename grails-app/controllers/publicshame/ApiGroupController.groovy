package publicshame
import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils

class ApiGroupController {

    def getAllGroups(){
        def mapHash = []
        Team.findAll().each {
            mapHash.add([
                    id: it.lookup,
                    name: it.name,
                    count: SinEntry.countByGroup(it)
            ])
        }

        log.error(mapHash as JSON)

        render mapHash as JSON
    }

    def postGroup() {
        log.debug(request)
        def group = new Team(request.JSON)
        createRandomString(group)

        if(group.save()) {
            def resp = [
                    id: group.lookup,
                    name: group.name
            ]
            render resp as JSON
        } else {
            render group.errors
        }
    }

    private void createRandomString(Team group) {
        def randomString = RandomStringUtils.random(5, true, true)
        while ( 0 != Team.findAllWhere(lookup: randomString).size() ) {
            randomString = RandomStringUtils.random(5, true, true)
        }
        group.lookup = randomString
    }
}
