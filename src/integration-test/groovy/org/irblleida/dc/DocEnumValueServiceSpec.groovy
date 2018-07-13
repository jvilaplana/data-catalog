package org.irblleida.dc

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class DocEnumValueServiceSpec extends Specification {

    DocEnumValueService docEnumValueService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DocEnumValue(...).save(flush: true, failOnError: true)
        //new DocEnumValue(...).save(flush: true, failOnError: true)
        //DocEnumValue docEnumValue = new DocEnumValue(...).save(flush: true, failOnError: true)
        //new DocEnumValue(...).save(flush: true, failOnError: true)
        //new DocEnumValue(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //docEnumValue.id
    }

    void "test get"() {
        setupData()

        expect:
        docEnumValueService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DocEnumValue> docEnumValueList = docEnumValueService.list(max: 2, offset: 2)

        then:
        docEnumValueList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        docEnumValueService.count() == 5
    }

    void "test delete"() {
        Long docEnumValueId = setupData()

        expect:
        docEnumValueService.count() == 5

        when:
        docEnumValueService.delete(docEnumValueId)
        sessionFactory.currentSession.flush()

        then:
        docEnumValueService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DocEnumValue docEnumValue = new DocEnumValue()
        docEnumValueService.save(docEnumValue)

        then:
        docEnumValue.id != null
    }
}
