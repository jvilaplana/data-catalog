package org.irblleida.dc

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class DocEnumController {

    DocEnumService docEnumService

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
    }

    def edit(Long id) {
        respond docEnumService.get(id)
    }

    def update(DocEnum docEnum) {
        if (docEnum == null) {
            notFound()
            return
        }

        for(DocEnumValue docEnumValue in docEnum.values){
            if(params[docEnumValue.name + '-description']) docEnumValue.description = params[docEnumValue.name + '-description']
        }

        try {
            docEnumService.save(docEnum)
        } catch (ValidationException e) {
            respond docEnum.errors, view:'edit', id: docEnum.id.toString()
            return
        }

        flash.message = message(code: 'default.saved.success')
        redirect controller: 'dataCatalog', action: 'index'
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        docEnumService.delete(id)
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
