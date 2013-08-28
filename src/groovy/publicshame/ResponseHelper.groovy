package publicshame

import grails.converters.JSON

class ResponseHelper {
    public static JSON getResponseForError(message, code, response) {
        response.status = code
        return message as JSON
    }

    public static JSON getResponseForTeamNotFound(response) {
        return getResponseForError([error: "Team not found"], 404, response)
    }

    public static JSON getResponseForPassphraseNotCorrect(response) {
        return getResponseForError([error: "Passphrase invalid"], 403, response)
    }

    public static JSON getResponseForPostNotFount(response) {
        return getResponseForError([error: "Post not found"], 404, response)
    }
}
