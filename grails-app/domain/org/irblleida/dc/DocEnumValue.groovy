package org.irblleida.dc

class DocEnumValue {

    String name
    String description

    static belongsTo = [docEnum: DocEnum]

    static constraints = {
        name nullable: false, blank: false
        description nullable: true
    }
}
