# Grails Data Catalog
A Grails 3 plugin to automatically generate a data catalog.


## Overview
The data-catalog offers an automated way to generate a data catalog of all the domain classes. It also provides default views and report generation


## Basic usage
Add the Bintray repository to your `build.gradle` file:

```
buildscript {
  repositories {
      ...
      maven { url "http://dl.bintray.com/jvilaplana/plugins" }
  }
}
```

Add the dependency to your project's `build.gradle` file:

```
dependencies {
  ...
  compile 'grails.bootstrap.forms:data-catalog:0.0.1'
}
```

It automatically handles the application start. If you want to disable it, just add the property `dataCatalog.autoUpdate` in your `application.groovy` file and set it to `false`:

```
dataCatalog.autoUpdate = false
```

By doing this, you will be able to update your data catalog using the method `update()` from service `dataCatalogService`:

```
...
def dataCatalogService
...
dataCatalogService.update()
```

### Default views

This plugin offers you some default views for the Data Catalog visualization. You will find it on `URL/dataCatalog/index`.
All the views use `Bootstrap v4.1.1`, `Font Awesome Free 5.1.0 ` and `jQuery v1.9.0` Plugins

It **does not offers any security mechanism**.

You can override any view if you want to customize the security or the style by creating the `DataCatalog` controller and views.

If you are using `SpringSecurity` Plugin and you want to customize the security, then you can extend the `DataCatalog` class.
Example:
```
@Secured(['ROLE_ADMIN'])
class DataCatalogController extends org.irblleida.dc.DataCatalogController{

    def index() {
        super.index()
    }

}
```

If you want to customize the views, the default paths are:

 - `dataCatalog/index`: index with all the information
 - `docClass/[show|edit|index|create]`: DocClass domain default views.
 - `docVariable/[show|edit|index|create]`: DocVariable domain default views.
 - `docEnum/[show|edit|index|create]`: DocEnum domain default views.
 - `docEnumValue/[show|edit|index|create]`: DocEnumValue domain default views.


### Documentation

This plugin defines four different domain classes:

#### DocClass

| Attribute | Type | Description |
| --------- | ----------- | ----------- |
| `name` | `String` | Name of the Domain class |
| `description` | `String` | Description of the domain class | 
| `variables` | `DocVariable` | Set of variables of the domain class |

#### DocVariable

| Attribute | Type | Description |
| --------- | ----------- | ----------- |
| `name` | `String` | Name of the variable |
| `description` | `String` | Description of the variable |
| `type` | `Class` | Object type of the variable |
| `code` | `String` | Code that defines the variable |
| `required` | `Boolean` | Set to `true` if it is not `nullable` |
| `defaultUnits` | `String` | Default measure units, if any |

#### DocEnum

| Attribute | Type | Description |
| --------- | ----------- | ----------- |
| `name` | `String` | Name of the Enum class |
| `description` | `String` | Description of the Enum class | 
| `values` | `DocEnumValue` | Set of possible Enum values |
| `contexts` | `DocClass` | Set `DocClass` where this Enum appears |

#### DocEnumValue

| Attribute | Type | Description |
| --------- | ----------- | ----------- |
| `name` | `String` | Name of the Enum value |
| `description` | `String` | Description of the Enum value | 


## Authors

 - [Pau Balaguer](https://github.com/pbalaguer19)
 - [Jordi Vilaplana](https://github.com/jvilaplana)