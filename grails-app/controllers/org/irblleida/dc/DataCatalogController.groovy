package org.irblleida.dc

class DataCatalogController {

    def index() {
        render view: 'index', model: [
                docClassList: DocClass.findAll([sort: 'name', order: 'desc']),
                docEnumList: DocEnum.findAll([sort: 'name', order: 'desc'])
        ]
    }
}
