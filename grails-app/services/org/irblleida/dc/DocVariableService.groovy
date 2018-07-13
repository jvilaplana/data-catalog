package org.irblleida.dc

import grails.gorm.services.Service

@Service(DocVariable)
interface DocVariableService {

    DocVariable get(Serializable id)

    List<DocVariable> list(Map args)

    Long count()

    void delete(Serializable id)

    DocVariable save(DocVariable docVariable)

}