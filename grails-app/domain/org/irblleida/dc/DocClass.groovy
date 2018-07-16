package org.irblleida.dc

class DocClass {

    String name
    String description

    static hasMany = [
            variables: DocVariable
    ]

    static embedded = ['variables']

    static constraints = {
        name nullable: false, blank: false, unique: true
        description nullable: true
    }
}
