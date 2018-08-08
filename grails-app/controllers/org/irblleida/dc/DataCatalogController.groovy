package org.irblleida.dc

class DataCatalogController {

    def index() {
        render view: 'index', model: [
                docClassList: DocClass.findAll([sort: 'name', order: 'asc']),
                docEnumList: DocEnum.findAll([sort: 'name', order: 'asc'])
        ]
    }

    def publicCatalog() {
        render view: 'publicCatalog', model: [
                docClassList: DocClass.findAll([sort: 'name', order: 'asc']),
                docEnumList: DocEnum.findAll([sort: 'name', order: 'asc'])
        ]
    }
}
