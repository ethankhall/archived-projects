package publicshame

class Team {

    String name
    String lookup
    String passphrase = ""

    static constraints = {
        name(blank: false)
        lookup(blank: false, unique: true)
    }

    static hasMany = [entries: SinEntry]
}
