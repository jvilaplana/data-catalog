package org.irblleida.dc

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class DocEnumValueController {

    DocEnumValueService docEnumValueService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond docEnumValueService.list(params), model:[docEnumValueCount: docEnumValueService.count()]
    }

    def show(Long id) {
        respond docEnumValueService.get(id)
    }

    def create() {
        respond new DocEnumValue(params)
    }

    def save(DocEnumValue docEnumValue) {
        if (docEnumValue == null) {
            notFound()
            return
        }

        try {
            docEnumValueService.save(docEnumValue)
        } catch (ValidationException e) {
            respond docEnumValue.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'docEnumValue.label', default: 'DocEnumValue'), docEnumValue.id])
                redirect docEnumValue
            }
            '*' { respond docEnumValue, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond docEnumValueService.get(id)
    }

    def update(DocEnumValue docEnumValue) {
        if (docEnumValue == null) {
            notFound()
            return
        }

        try {
            docEnumValueService.save(docEnumValue)
        } catch (ValidationException e) {
            respond docEnumValue.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'docEnumValue.label', default: 'DocEnumValue'), docEnumValue.id])
                redirect docEnumValue
            }
            '*'{ respond docEnumValue, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        docEnumValueService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'docEnumValue.label', default: 'DocEnumValue'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'docEnumValue.label', default: 'DocEnumValue'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
