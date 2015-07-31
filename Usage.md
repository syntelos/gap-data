> ## Prerequisites ##
  * Java JDK 1.6 or later
  * Apache Ant 1.6.5 or later

> ## Procedure ##

> | At the moment, constructing a gap-data application is a manual process.  One can take a copy of [cpi](http://cpi.googlecode.com/) and delete the application specific components.|
|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

> Edit "project.application" to point it to your own appengine name.

> Run "ant" to build, and then
```
 ./appengine-java-sdk/X.Y.Z/bin/appcfg -e email@dom update war
```
> to install.

> After appcfg has been successfully employed once, then "ant update" can repeat that process if necessary.

> See [Application](Application.md), [AntBuild](AntBuild.md), and [AppCfg](AppCfg.md).