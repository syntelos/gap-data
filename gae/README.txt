ULSF Space Net

 This package is a Google App Engine service for orbital visualization
 data.

Frontend

 The primary application is a frontend server dedicated to the various
 user interfaces defined in "src/spacenet/data/*Servlet.java".

 The Frontend is passive to the data management functions of the
 Client and Backend domains.

Client

 The client package manages server data in the Catalog class.  This is
 run periodically on another host where bandwidth and cpu time are
 relatively free.

 The client program is run once in twenty four hours.

Backend

 A backend server produces Catalog Derivatives.  It is a dynamic
 instance that is driven by GAE Cron.

 Every twleve hours, GAE Cron calls "/spacenet/io/load" which is bound
 to the backend servlet.

Annotations

 A set of source code annotations are defined in "src/spacenet/ano".
 These include "Frontend", "Backend" and "Client".  These tags have
 been placed for documentation purposes.

 Not all classes are marked with their intended application domain.
 In many cases, the Frontend is unmarked -- it is primary.  In other
 cases, code is shared among these domains.  Code designed as shared
 may be marked with multiple tags.

 The "Client" tag is critical in the case of the catalog format
 parsers, where the Client classes produce objects as data binding
 operators.  This usage is inconsistent with Frontend or Backend (Gap
 Data) applications.  In this case, the parsers return an object with
 ambiguous storage state.  The normal storage status protocol has been
 discarded, violating Gap Data handling requirements for coherent data
 processing.

 The annotation tagging is consistent with application to an automated
 prover (unambiguous), while presently the annotations are discarded
 by the compiler (RuntimePolicy SOURCE).

Concurrency

 The present concurrency device is the Gap Data BigTable Lock.  Gap
 Data classes work with transparent GD-internal concurrency mechanisms
 that employ the Lock as well as a Marking Statistic device.
