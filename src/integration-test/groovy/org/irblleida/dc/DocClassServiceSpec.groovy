package org.irblleida.dc

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class DocClassServiceSpec extends Specification {

    DocClassService docClassService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DocClass(...).save(flush: true, failOnError: true)
        //new DocClass(...).save(flush: true, failOnError: true)
        //DocClass docClass = new DocClass(...).save(flush: true, failOnError: true)
        //new DocClass(...).save(flush: true, failOnError: true)
        //new DocClass(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //docClass.id
    }

    void "test get"() {
        setupData()

        expect:
        docClassService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DocClass> docClassList = docClassService.list(max: 2, offset: 2)

        then:
        docClassList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        docClassService.count() == 5
    }

    void "test delete"() {
        Long docClassId = setupData()

        expect:
        docClassService.count() == 5

        when:
        docClassService.delete(docClassId)
        sessionFactory.currentSession.flush()

        then:
        docClassService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DocClass docClass = new DocClass()
        docClassService.save(docClass)

        then:
        docClass.id != null
    }
}
