package publicshame

import grails.test.mixin.TestFor
import groovy.json.JsonSlurper
import org.junit.After
import org.junit.Before
import org.junit.Test

@TestFor(ApiSinEntryController)
class ApiSinEntryControllerTests {

    ApiSinEntryController sinController

    @Before
    void setUp() {
        sinController = new ApiSinEntryController()
    }

    @After
    void tearDown() {
        Group.findAll().each {
            it.delete()
        }
    }

    @Test
    void testAddSin() {
        def group = createGroup("123", "1")

        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"sinner\" : \"Ethan\"}" as byte[]
        sinController.params.groupId = group.lookup
        sinController.createEntry()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject != null
        assert jsonObject.count == 1
    }

    private Group createGroup(name, lookup) {
        def group = new Group()
        group.name = name
        group.lookup = lookup
        group.save()
        return group
    }
}
