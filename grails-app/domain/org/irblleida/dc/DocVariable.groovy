package org.irblleida.dc

class DocVariable {

    String name
    String description
    String type
    String classType
    String code
    Boolean required
    String defaultUnits

    DocClass domain

    static belongsTo = [
            domain: DocClass
    ]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true
        type nullable: false
        classType nullable: false
        code nullable: true
        required nullable: true
        defaultUnits nullable: true
    }
}
