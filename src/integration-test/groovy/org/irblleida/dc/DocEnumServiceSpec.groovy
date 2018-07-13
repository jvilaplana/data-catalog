package org.irblleida.dc

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class DocEnumServiceSpec extends Specification {

    DocEnumService docEnumService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DocEnum(...).save(flush: true, failOnError: true)
        //new DocEnum(...).save(flush: true, failOnError: true)
        //DocEnum docEnum = new DocEnum(...).save(flush: true, failOnError: true)
        //new DocEnum(...).save(flush: true, failOnError: true)
        //new DocEnum(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //docEnum.id
    }

    void "test get"() {
        setupData()

        expect:
        docEnumService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DocEnum> docEnumList = docEnumService.list(max: 2, offset: 2)

        then:
        docEnumList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        docEnumService.count() == 5
    }

    void "test delete"() {
        Long docEnumId = setupData()

        expect:
        docEnumService.count() == 5

        when:
        docEnumService.delete(docEnumId)
        sessionFactory.currentSession.flush()

        then:
        docEnumService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DocEnum docEnum = new DocEnum()
        docEnumService.save(docEnum)

        then:
        docEnum.id != null
    }
}
