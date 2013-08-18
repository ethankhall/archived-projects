package publicshame

class Group {

    String name
    String lookup
    String passphrase = ""

    static constraints = {
        name(blank: false)
        lookup(blank: false, unique: true)
    }

    static mapping = {
        table 'group_holder'
    }

    static hasMany = [entries: SinEntry]
}
