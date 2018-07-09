package org.irblleida.dc

class DocClass {

    String name

    static constraints = {
        name nullable: false, blank: false, unique: true
    }
}
