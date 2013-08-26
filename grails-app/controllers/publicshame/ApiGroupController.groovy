package publicshame
import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils

class ApiGroupController {

    def getAllGroups(){
        def mapHash = []
        Team.findAll().each {
            mapHash.add([
                    key: it.lookup,
                    name: it.name,
                    count: SinEntry.countByTeam(it)
            ])
        }

        render mapHash as JSON
    }

    def postGroup() {
        def group = new Team(request.JSON)
        group.lookup = createRandomString()

        if(group.save()) {
            def resp = [
                    key: group.lookup,
                    name: group.name
            ]
            render resp as JSON
        } else {
            render group.errors
        }
    }

    def createRandomString() {
        def randomString = RandomStringUtils.random(5, true, true)
        while ( 0 != Team.findAllWhere(lookup: randomString).size() ) {
            randomString = RandomStringUtils.random(5, true, true)
        }

        return randomString

    }
}
