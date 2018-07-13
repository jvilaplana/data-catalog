package org.irblleida.dc

import grails.gorm.services.Service

@Service(DocClass)
interface DocClassService {

    DocClass get(Serializable id)

    List<DocClass> list(Map args)

    Long count()

    void delete(Serializable id)

    DocClass save(DocClass docClass)

}