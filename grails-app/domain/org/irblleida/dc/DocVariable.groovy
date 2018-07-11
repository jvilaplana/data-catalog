package org.irblleida.dc

class DocVariable {

    String name
    String description
    String type
    String code
    Boolean required
    String defaultUnits

    static belongsTo = [domain: DocClass]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true
        type nullable: false
        code nullable: true
        required nullable: true
        defaultUnits nullable: true
    }
}
