This is the example code used in my various presentations on improving application design by using dependency injection, aspect-oriented programming and object-oriented design.

It contains multiple versions of the same sample application - a really simple "banking" application - that is incrementally transformed from tangled mess of procedural code to a nice object-oriented POJO design that leverages aspects and dependency injection.

There are three sets of maven projects.

The first group contains the example code for my rich domain model presentation. This code illustrates compares and contrasts a procedural design with one that uses a rich domain model. There are two projects:
  * procedural-banking - the procedural version
  * domain-model-banking - the domain model version
Both projects uses Spring and Hibernate. There are a couple of different versions of this presentation. The Spring One version from June 2007 is available as both [slides and a video](http://chrisrichardson.net/springOne2007.html). The Spring Experience version from December 2007 has the [slides and an audio recording](http://www.thespringexperience.com/speaker/presentation_list.jsp?speakerId=985&showId=147).

The second set of maven projects contain the code for my presentation on simplifying code with dependency injection, aspect-oriented programming (Spring AOP) and Hibernate. There are the following projects:
  * v0-non-pojo-banking - uses programmatic transaction management/security/etc and singletons and statics for inter-component references
  * v1-non-pojo-banking-with-di - uses dependency injection for inter-component references
  * v2-non-pojo-banking-with-di-aop - uses Spring AOP to handle transactions/security/etc
  * v3-non-pojo-banking-with-di-aop-spring-txn - uses Spring's builtin transaction management aspect
  * v4-non-pojo-banking-with-di-aop-spring-txn-jdbc - uses JDBC
  * v5-non-pojo-banking-with-di-aop-spring-txn-hibernate - use Hibernates

These projects progressively illustrate how using dependency injection, AOP and Hibernate simplify the code. You can find the slides for February 2008 version of the presentation [here](http://www.chrisrichardson.net/sdforumjavasig0208.html). An earlier version of this presentation was also given at the [Colorado Software Summit 2007](http://www.softwaresummit.com/2007/speakers/richardson.htm).

Finally, there is also the spring-mvc-jpa-banking project. This version uses Spring 2.5 @MVC and JPA.

