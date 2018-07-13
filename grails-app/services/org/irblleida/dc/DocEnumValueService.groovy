package org.irblleida.dc

import grails.gorm.services.Service

@Service(DocEnumValue)
interface DocEnumValueService {

    DocEnumValue get(Serializable id)

    List<DocEnumValue> list(Map args)

    Long count()

    void delete(Serializable id)

    DocEnumValue save(DocEnumValue docEnumValue)

}