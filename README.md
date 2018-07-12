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

By doing this, you will be able to update your data catalog using the method `udate()` from service `dataCatalogService`:

```
...
def dataCatalogService
...
dataCatalogService.udate()
```

Or navigating to `URL/dataCatalog` path.


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
