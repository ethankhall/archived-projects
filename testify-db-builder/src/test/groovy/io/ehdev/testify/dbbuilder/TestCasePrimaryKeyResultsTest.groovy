package io.ehdev.testify.dbbuilder
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class TestCasePrimaryKeyResultsTest {

    def testCase
    def id

    @BeforeMethod
    public void setup() {
        id = 0
        testCase = new TestCasePrimaryKeyResults()
    }

    @Test
    public void testTableToIdMapping() throws Exception {
        createTableIds("table1")
        createTableIds("table2")
        assertThat(testCase.getPrimaryKeysForTable("table1")).hasSize(3)
        assertThat(testCase.getPrimaryKeysForTable("table2")).hasSize(3)
    }

    @Test
    public void testValuesInMap() throws Exception {
        createTableIds("table1")
        assertThat(testCase.getPrimaryKeysForTable('table1')).containsOnly(1, 2, 3)
    }


    private void createTableIds(String tableName) {
        testCase.addPrimaryKeyToTable(tableName, ++id)
        testCase.addPrimaryKeyToTable(tableName, ++id)
        testCase.addPrimaryKeyToTable(tableName, ++id)
        testCase.addPrimaryKeyToTable(tableName, id)
    }
}
