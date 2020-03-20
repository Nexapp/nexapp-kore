.PHONY: help build lint test coverage

help:
	@echo "Nexapp Kore"
	@echo "> test"
	@echo "> lint"
	@echo "> coverage	- Generates Jacoco coverage report"

build:
	mvn compile -DskipTests

lint:
	mvn compile test-compile antrun:run@detekt

test:
	mvn compile test

coverage:
	mvn jacoco:report-aggregate
	cat maven-reporting/target/site/jacoco-aggregate/index.html
