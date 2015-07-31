# Introduction #

The Gap Data codebase serves a couple purposes.  It is an [appengine](http://code.google.com/appengine/docs/java/) application framework, and it includes an [appengine](http://code.google.com/appengine/docs/java/) persistence model with sourcecode generation for application programming.

Mixing these two purposes in one package is a convenience to application development that implies two phases to its use.  In one phase, the package is built (by running 'ant') for executable binaries needed for the second phase.  In the second phase, the compiled software is able to generate sourcecode into the same build environment for persistent data object classes for application development.