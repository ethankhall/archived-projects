package io.ehdev.testify.dbbuilder

import groovy.util.logging.Slf4j

@Slf4j
class TestCaseContainer {
    Map<String, TestCasePrimaryKeyResults> testCases = [:]

    def addNewPrimaryKey(String currentTestName, String tableName, Integer rowResult){
        if(null != rowResult) {
            def testResults = getTestCaseResultsForTestName(currentTestName)
            testResults.addPrimaryKeyToTable(tableName, rowResult)
        } else {
            log.debug("A null value was passed into $tableName under test case $currentTestName")
        }
    }

    public TestCasePrimaryKeyResults getTestCaseResultsForTestName(String currentTestName) {
        if (null == testCases[currentTestName]) {
            testCases[currentTestName] = new TestCasePrimaryKeyResults()
        }
        testCases[currentTestName]
    }

    public List<Integer> getResultsForTableName(String tableName) {
        List<Integer> primaryKeys = []
        testCases.each { key, value ->
            primaryKeys.addAll(value.getPrimaryKeysForTable(tableName))
        }
        return primaryKeys
    }
}
