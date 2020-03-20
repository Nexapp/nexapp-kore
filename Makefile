.PHONY: build lint test coverage

build:
	mvn compile -DskipTests

lint:
	mvn antrun:run@detekt

test:
	mvn compile test

coverage:
	mvn jacoco:report-aggregate
	cat maven-reporting/target/site/jacoco-aggregate/index.html
