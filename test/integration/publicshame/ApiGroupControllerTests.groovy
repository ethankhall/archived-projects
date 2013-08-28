package publicshame

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test

@TestFor(ApiGroupController)
@Mock([Team, SinEntry])
class ApiGroupControllerTests {

    ApiGroupController controller
    def jsonBuilder

    @Before
    void setUp() {
        controller = new ApiGroupController()
        jsonBuilder = new JsonBuilder()
    }

    @Test
    void testAddingGroupController() {
        createGroupReturnId(controller)
    }

    @Test
    void testGettingTwoGroupControllers() {
        def idsCreated = [createGroupReturnId(controller), createGroupReturnId(controller)]
        controller.getAllGroups()
        def jsonObject = new JsonSlurper().parseText(controller.response.contentAsString)

        assert (jsonObject as ArrayList).size() == 2
        for(entry in jsonObject){
            assert entry.key in idsCreated
        }
    }

    public static String createGroupReturnId(ApiGroupController controller) {
        controller.request.contentType = 'type/json'
        controller.request.content = "{\"name\":\"${System.currentTimeMillis()}\"}" as byte[]
        controller.postGroup()

        def jsonObj = new JsonSlurper().parseText((String)controller.response.contentAsString)
        controller.response.reset()

        assert jsonObj.key != null
        return jsonObj.key
    }
}

