[![CircleCI](https://circleci.com/gh/jfspps/ChemInvent.svg?style=svg)](https://circleci.com/gh/jfspps/ChemInvent)


# ChemInvent

This project demonstrates Spring MVC REST Docs, using a chemical reagent inventory list as the model.

When packaging the project, look under the /target/generated-snippets directory for the generated snippets. The index, index.html is in /target/generated-docs and can be opened in a browser.

The styling and layout of the index is handled by [request-fields.snippet](/src/test/resources/org/springframework/restdocs/templates/request-fields.snippet).

One can configure the Maven pom.xml to integrate the compiled index.html into the JAR and have it available when the JAR is executed. The integration of index.html is optional.