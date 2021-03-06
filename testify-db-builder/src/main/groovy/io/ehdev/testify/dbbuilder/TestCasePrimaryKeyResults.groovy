package io.ehdev.testify.dbbuilder

class TestCasePrimaryKeyResults {
    Map<String, Set<Integer>> tableToPrimaryKeyMap = [:]

    def addPrimaryKeyToTable(String tableName, Integer primaryKey) {
        Set<Integer> keys = getPrimaryKeysForTable(tableName)
        keys.add(primaryKey)
        tableToPrimaryKeyMap[tableName] = keys
    }

    Set<Integer> getPrimaryKeysForTable(String tableName) {
        if(null == tableToPrimaryKeyMap[tableName]) {
            tableToPrimaryKeyMap[tableName] = new LinkedHashSet<Integer>()
        }
        tableToPrimaryKeyMap[tableName]
    }
}
