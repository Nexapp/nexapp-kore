.PHONY: help build deploy-staging deploy-release lint test coverage

help:
	@echo "Nexapp Kore"
	@echo "> test"
	@echo "> lint"
	@echo "> coverage	- Generates Jacoco coverage report"

build:
	mvn compile -DskipTests

deploy-staging:
	GPG_TTY=$(tty) mvn clean deploy -DskipTests

deploy-release:
	GPG_TTY=$(tty) mvn clean deploy -P release -DskipTests

lint:
	mvn compile test-compile antrun:run@detekt

test:
	mvn compile test

coverage:
	mvn jacoco:report-aggregate
