package io.ehdev.easyinvoice.lineitem

abstract class LineItem {

    def description = ""
    def category = ""

    def taxEnabled = true
    def id = UUID.randomUUID() as String
}
