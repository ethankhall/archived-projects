package publicshame

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test

@TestFor(ApiSinEntryController)
@Mock([SinEntry, Team])
class ApiSinEntryControllerTests {

    ApiSinEntryController sinController

    @Before
    void setUp() {
        sinController = new ApiSinEntryController()
    }

    @Test
    void testAddSin() {
        def group = createGroup("123", "1")

        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"sinner\" : \"Ethan\"}" as byte[]
        sinController.params.teamId = group.lookup
        sinController.createEntry()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject != null
        assert jsonObject.count == 1
    }

    @Test
    void testGetCount() {
        def group = createGroup("1234", "12")
        createSin("Ethan", "", group)
        createSin("Ethan1", "", group)

        sinController.request.contentType = 'type/json'
        sinController.params.teamId = group.lookup
        sinController.getAllEntries()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject.count == 2
    }

    @Test
    void testPasswordProtectedBadPassword() {
        def group = createGroup("1", "12345", "someone")
        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"sinner\" : \"Ethan\", \"passphrase\" : \"somethingwrong\"}" as byte[]
        sinController.params.teamId = group.lookup
        sinController.createEntry()
        assert sinController.response.getStatus() == 403
    }

    @Test
    void testPasswordProtectedGoodPassword() {
        def group = createGroup("1", "12345", "someone")
        sinController.request.contentType = 'type/json'
        sinController.request.content = "{\"sinner\" : \"Ethan\", \"passphrase\" : \"someone\"}" as byte[]
        sinController.params.teamId = group.lookup
        sinController.createEntry()
        assert sinController.response.getStatus() == 200
    }

    @Test
    void testThatNameIsPassedBackProperly() {
        def group = createGroup("1", "12345", "someone")
        sinController.request.contentType = 'type/json'
        sinController.params.teamId = group.lookup
        sinController.getAllEntries()
        def jsonObject = new JsonSlurper().parseText(sinController.response.contentAsString)
        assert jsonObject.name == group.name
    }

    @Test
    void testThatInvalidTeamFailsWith404() {
        sinController.request.contentType = 'type/json'
        sinController.params.teamId = '1'
        sinController.getAllEntries()
        assert sinController.response.getStatus() ==  404
    }

    private void createSin(name, sinName, group){
        def sin = new SinEntry()
        sin.sinner = name
        sin.sin = sinName
        sin.group = group
        sin.save(failOnError: true)
    }

    private Team createGroup(name, lookup, password = "") {
        def group = new Team()
        group.name = name
        group.lookup = lookup
        group.passphrase = password
        group.save(failOnError: true)
        return group
    }
}
