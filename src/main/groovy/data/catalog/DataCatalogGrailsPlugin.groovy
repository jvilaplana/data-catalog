package data.catalog

import grails.core.GrailsApplication
import grails.plugins.*
import org.irblleida.dc.DocClass
import org.irblleida.dc.DocEnum
import org.irblleida.dc.DocEnumValue
import org.irblleida.dc.DocVariable

class DataCatalogGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.3.6 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Data Catalog" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/data-catalog"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() { {->
            // TODO Implement runtime spring config (optional)
        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }

    private static def DEFAULT_FIELDS = ['$staticClassInfo', '__$stMC', 'metaClass', '$defaultDatabindingWhiteList',
                                         'version', 'log', 'instanceControllersDomainBindingApi', 'instanceConvertersApi',
                                         'org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties',
                                         'org_grails_datastore_gorm_GormValidateable__errors', 'auditable',
                                         'org_grails_datastore_gorm_GormValidateable__skipValidate', '$staticClassInfo$',
                                         '$callSiteArray']

    private static def DATA_CATALOG_DOMAIN_CLASSES = ['DocClass', 'DocVariable', 'DocEnum', 'DocEnumValue']

    @Override
    void onStartup(Map<String, Object> event) {
        println("Configuring Data Catalog ...")
        for (domainClass in grailsApplication.domainClasses) {
            def domainClassName = domainClass.name

            // Don't save the domain classes that belongs to DataCatalog plugin
            if(domainClassName in DATA_CATALOG_DOMAIN_CLASSES)
                continue

            // Save DocClass info and get object
            def docClass = saveDocClass(domainClassName)

            // Save DocVariable (fields) for the particular Domain Class
            saveDomainClassFields(domainClass, docClass)
        }
        println("... finished configuring Data Catalog")
    }

    private static DocClass saveDocClass(domainClassName){
        DocClass docClass = DocClass.findByName(domainClassName)
        if(!docClass)
            docClass = new DocClass(name: domainClassName).save(flush: true)

        return docClass
    }

    private static void saveDomainClassFields(domainClass, docClass){
        for(def field in domainClass.clazz.declaredFields){
            def fieldName = field.toString().split('\\.')[-1]

            // Default Grails domain fields are not saved
            if(fieldName in DEFAULT_FIELDS)
                continue

            def docVariable = DocVariable.findByNameAndDomain(fieldName, docClass)
            if(!docVariable){
                def fieldType = field.type.toString().split(' |\\.')[-1]
                docVariable = new DocVariable(name: fieldName, type: fieldType, domain: docClass).save(flush: true)
                docClass.addToVariables(docVariable)
                docClass.save(flush: true)
                checkFieldTypeIsEnum(field.type, docClass)
            }
        }
    }

    private static void checkFieldTypeIsEnum(fieldType, docClass){
        if(fieldType.isEnum()){
            def enumClass = fieldType.toString().split('\\.')[-1]

            def docEnum = DocEnum.findByName(enumClass)

            if(!docEnum){
                docEnum = new DocEnum(name: enumClass).save(flush: true)

                for(def enumValue in fieldType.values()){
                    def docEnumValue = DocEnumValue.findByNameAndDocEnum(enumValue, docEnum)

                    if(!docEnumValue){
                        docEnumValue = new DocEnumValue(name: enumValue, docEnum: docEnum).save(flush: true)
                        docEnum.addToValues(docEnumValue)
                    }
                }
            }
            docEnum.addToContexts(docClass)
            docEnum.save(flush: true)
        }
    }
}
