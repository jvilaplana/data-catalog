package org.irblleida.dc

import grails.gorm.services.Service

@Service(DocEnum)
interface DocEnumService {

    DocEnum get(Serializable id)

    List<DocEnum> list(Map args)

    Long count()

    void delete(Serializable id)

    DocEnum save(DocEnum docEnum)

}