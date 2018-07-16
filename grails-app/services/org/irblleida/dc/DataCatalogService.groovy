package org.irblleida.dc

import grails.gorm.transactions.Transactional

@Transactional
class DataCatalogService {
    def grailsApplication

    private static def DEFAULT_FIELDS = ['$staticClassInfo', '__$stMC', 'metaClass', '$defaultDatabindingWhiteList',
                                         'version', 'log', 'instanceControllersDomainBindingApi', 'instanceConvertersApi',
                                         'org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties',
                                         'org_grails_datastore_gorm_GormValidateable__errors', 'auditable',
                                         'org_grails_datastore_gorm_GormValidateable__skipValidate', '$staticClassInfo$',
                                         '$callSiteArray', 'belongsTo', 'transients', 'constraints', 'hasMany', 'mapping',
                                         'embedded']

    private static def DATA_CATALOG_DOMAIN_CLASSES = ['DocClass', 'DocVariable', 'DocEnum', 'DocEnumValue']

    def update() {
        println("Configuring Data Catalog ...")
        for (domainClass in grailsApplication.domainClasses) {
            def domainClassName = domainClass.name
            // Don't save the domain classes that belongs to DataCatalog plugin
            if(domainClassName in DATA_CATALOG_DOMAIN_CLASSES)
                continue

            // Save DocClass info and get object
            saveCatalog(domainClass)
        }
        println("... finished configuring Data Catalog\n")
    }

    private static void saveCatalog(domainClass){
        def domainClassName = domainClass.name
        DocClass docClass = DocClass.findByName(domainClassName)

        if(!docClass)
            docClass = new DocClass(name: domainClassName).save(flush: true)

        // Udate/Save DocVariable (fields) for the particular Domain Class
        updateDomainClassFields(domainClass, docClass)

    }

    private static void updateDomainClassFields(domainClass, DocClass docClass){
        // Check new variables
        def declaredVariables = []
        for(def field in domainClass.clazz.declaredFields){
            def fieldName = field.toString().split('\\.')[-1]

            // Default Grails domain fields are not saved
            if(fieldName in DEFAULT_FIELDS)
                continue

            declaredVariables.add(fieldName)

            // Field already saved
            if(DocVariable.findByDomainAndName(docClass, fieldName) != null){
                checkIfEnumHasChanged(field.type, docClass)
                continue
            }

            def fieldType = field.type.toString().split('\\.')[-1]
            def variable = new DocVariable(name: fieldName, type: fieldType, classType: field.type.toString(), domain: docClass).save(flush: true)
            docClass.addToVariables(variable)
            docClass.save(flush: true)
            checkFieldTypeIsEnum(field.type, docClass)

        }
        // Check removed variables
        def removedVariables = DocVariable.findAllByDomainAndNameNotInList(docClass, declaredVariables)

        for(def variable in removedVariables){
            def variableClassType = variable.classType
            def variableType = variable.type

            docClass.removeFromVariables(variable)
            docClass.save(flush: true)
            variable.delete(flush: true)

            try{
                Class c = Class.forName(variableClassType)
                if(c.isEnum())
                    reviewEnum(variableType)
            }catch (ClassNotFoundException e){
                def docEnum = DocEnum.findByName(variableType)
                if(docEnum) docEnum.delete(flush: true)
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
                        def value = new DocEnumValue(name: enumValue, docEnum: docEnum).save(flush: true)
                        docEnum.addToValues(value)
                        docEnum.save(flush: true)
                    }
                }
            }
            if(!docEnum.contexts || !docEnum.contexts.contains(docClass)){
                docEnum.addToContexts(docClass)
                docEnum.save(flush: true)
            }
        }
    }

    private static void reviewEnum(enumClass){
        def enumName = enumClass.toString().split('\\.')[-1]
        def docEnum = DocEnum.findByName(enumName)

        // Review contexts
        for(DocClass context in docEnum.contexts){
            def variablesList = DocVariable.findAllByDomainAndType(context, enumClass)
            if(!variablesList) docEnum.removeFromContexts(context)
        }
        docEnum.save(flush: true)

        if(!docEnum.contexts)
            docEnum.delete(flush: true)

    }

    private static void checkIfEnumHasChanged(fieldType, docClass){
        if(fieldType.isEnum()){
            def enumClass = fieldType.toString().split('\\.')[-1]

            def docEnum = DocEnum.findByName(enumClass)
            if(docEnum){
                def valueList = []
                for(def enumValue in fieldType.values()){
                    def docEnumValue = DocEnumValue.findByNameAndDocEnum(enumValue, docEnum)

                    if(!docEnumValue){
                        def value = new DocEnumValue(name: enumValue, docEnum: docEnum).save(flush: true)
                        docEnum.addToValues(value)
                        docEnum.save(flush: true)
                    }
                    valueList.add(enumValue)
                }

                DocEnumValue.findAllByDocEnumAndNameNotInList(docEnum, valueList).each { it ->
                    docEnum.removeFromValues(it)
                    docEnum.save(flush: true)
                    it.delete(flush: true)
                }
            }
            if(!docEnum.contexts || !docEnum.contexts.contains(docClass)){
                docEnum.addToContexts(docClass)
                docEnum.save(flush: true)
            }
        }
    }
}
