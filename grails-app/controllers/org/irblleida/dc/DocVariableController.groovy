package org.irblleida.dc

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class DocVariableController {

    DocVariableService docVariableService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond docVariableService.list(params), model:[docVariableCount: docVariableService.count()]
    }

    def show(Long id) {
        respond docVariableService.get(id)
    }

    def create() {
        respond new DocVariable(params)
    }

    def save(DocVariable docVariable) {
        if (docVariable == null) {
            notFound()
            return
        }

        try {
            docVariableService.save(docVariable)
        } catch (ValidationException e) {
            respond docVariable.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'docVariable.label', default: 'DocVariable'), docVariable.id])
                redirect docVariable
            }
            '*' { respond docVariable, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond docVariableService.get(id)
    }

    def update(DocVariable docVariable) {
        if (docVariable == null) {
            notFound()
            return
        }

        try {
            docVariableService.save(docVariable)
        } catch (ValidationException e) {
            respond docVariable.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'docVariable.label', default: 'DocVariable'), docVariable.id])
                redirect docVariable
            }
            '*'{ respond docVariable, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        docVariableService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'docVariable.label', default: 'DocVariable'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'docVariable.label', default: 'DocVariable'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
