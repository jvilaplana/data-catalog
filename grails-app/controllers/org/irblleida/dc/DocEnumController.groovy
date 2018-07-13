package org.irblleida.dc

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class DocEnumController {

    DocEnumService docEnumService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond docEnumService.list(params), model:[docEnumCount: docEnumService.count()]
    }

    def show(Long id) {
        respond docEnumService.get(id)
    }

    def create() {
        respond new DocEnum(params)
    }

    def save(DocEnum docEnum) {
        if (docEnum == null) {
            notFound()
            return
        }

        try {
            docEnumService.save(docEnum)
        } catch (ValidationException e) {
            respond docEnum.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'docEnum.label', default: 'DocEnum'), docEnum.id])
                redirect docEnum
            }
            '*' { respond docEnum, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond docEnumService.get(id)
    }

    def update(DocEnum docEnum) {
        if (docEnum == null) {
            notFound()
            return
        }

        try {
            docEnumService.save(docEnum)
        } catch (ValidationException e) {
            respond docEnum.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'docEnum.label', default: 'DocEnum'), docEnum.id])
                redirect docEnum
            }
            '*'{ respond docEnum, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        docEnumService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'docEnum.label', default: 'DocEnum'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'docEnum.label', default: 'DocEnum'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
