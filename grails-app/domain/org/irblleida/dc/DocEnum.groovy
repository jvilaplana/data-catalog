package org.irblleida.dc

class DocEnum {

    String name
    String description

    static hasMany = [
            contexts: DocClass
    ]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true
    }
}
