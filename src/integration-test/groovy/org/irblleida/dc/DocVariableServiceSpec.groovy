package org.irblleida.dc

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class DocVariableServiceSpec extends Specification {

    DocVariableService docVariableService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DocVariable(...).save(flush: true, failOnError: true)
        //new DocVariable(...).save(flush: true, failOnError: true)
        //DocVariable docVariable = new DocVariable(...).save(flush: true, failOnError: true)
        //new DocVariable(...).save(flush: true, failOnError: true)
        //new DocVariable(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //docVariable.id
    }

    void "test get"() {
        setupData()

        expect:
        docVariableService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DocVariable> docVariableList = docVariableService.list(max: 2, offset: 2)

        then:
        docVariableList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        docVariableService.count() == 5
    }

    void "test delete"() {
        Long docVariableId = setupData()

        expect:
        docVariableService.count() == 5

        when:
        docVariableService.delete(docVariableId)
        sessionFactory.currentSession.flush()

        then:
        docVariableService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DocVariable docVariable = new DocVariable()
        docVariableService.save(docVariable)

        then:
        docVariable.id != null
    }
}
