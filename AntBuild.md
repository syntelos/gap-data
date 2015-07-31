The [apache ant](http://ant.apache.org/) tool uses [java](http://java.sun.com/) to package, test and debug [a codebase](Application.md).

> # IDE #

Ant is the point of integration with [NetBeans](http://www.netbeans.org/), [Eclipse](http://www.eclipse.org/) and other IDEs.

> # Ant Commands #

Run

```
 ant -p
```

for a full list of options.

> ## Compile & Package ##

To compile and package run

```
 ant 
```

> ## Upload ##

To upload to appengine, run
```
 ant update
```

  * However, first time per session use AppCfg from the command line.

> ## Debug ##

To debug suspended, run ant debug for jpda on port 9999
```
 ant debug
```

> ## Test ##

To run with debugging port open, run
```
 ant run
```

Ant will run a local test server with jpda debugging available to [debuggers](http://code.google.com/p/jswat/) and IDEs.


> ## Versioning ##

> To see the current version, run
  * `ant version`.

> To increment your version number, run one of three routines
  * `ant build`, or
  * `ant minor`,  or
  * `ant major`.
