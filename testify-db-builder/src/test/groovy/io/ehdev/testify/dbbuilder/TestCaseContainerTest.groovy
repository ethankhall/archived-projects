package io.ehdev.testify.dbbuilder

import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class TestCaseContainerTest {

    TestCaseContainer container

    @BeforeMethod
    public void setup() {
        container = new TestCaseContainer()
    }

    @Test
    public void testFIFOReturnOrder() throws Exception {
        container.addNewPrimaryKey("", "table1", 1)
        container.addNewPrimaryKey("", "table1", 2)
        container.addNewPrimaryKey("", "table1", 3)
        container.addNewPrimaryKey("", "table1", 4)
        assertThat(container.getResultsForTableName("table1")).containsSequence(1, 2, 3, 4)
    }

    @Test
    public void testFIFOWithDifferentTables() throws Exception {
        container.addNewPrimaryKey("", "table1", 4)
        container.addNewPrimaryKey("", "table3", 3)
        container.addNewPrimaryKey("", "table3", 2)
        container.addNewPrimaryKey("", "table1", 1)
        assertThat(container.getResultsForTableName("table1")).containsExactly(4, 1)
        assertThat(container.getResultsForTableName("table3")).containsExactly(3, 2)
    }

    @Test
    public void testInsertNull() throws Exception {
        container.addNewPrimaryKey("", "table1", null)
        assertThat(container.getResultsForTableName("table1")).isEmpty()
    }

}
