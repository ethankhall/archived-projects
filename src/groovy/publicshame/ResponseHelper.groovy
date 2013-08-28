package publicshame

import grails.converters.JSON

class ResponseHelper {
    public static JSON getResponseForError(message, code, response) {
        response.status = code
        return message as JSON
    }
}
