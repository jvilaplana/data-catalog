package org.irblleida.dc

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class DocClassController {

    DocClassService docClassService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond docClassService.list(params), model:[docClassCount: docClassService.count()]
    }

    def show(Long id) {
        respond docClassService.get(id)
    }

    def create() {
        respond new DocClass(params)
    }

    def save(DocClass docClass) {
        if (docClass == null) {
            notFound()
            return
        }

        try {
            docClassService.save(docClass)
        } catch (ValidationException e) {
            respond docClass.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'docClass.label', default: 'DocClass'), docClass.id])
                redirect docClass
            }
            '*' { respond docClass, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond docClassService.get(id)
    }

    def update(DocClass docClass) {
        if (docClass == null) {
            notFound()
            return
        }

        try {
            docClassService.save(docClass)
        } catch (ValidationException e) {
            respond docClass.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'docClass.label', default: 'DocClass'), docClass.id])
                redirect docClass
            }
            '*'{ respond docClass, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        docClassService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'docClass.label', default: 'DocClass'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'docClass.label', default: 'DocClass'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
