.PHONY: help build deploy-staging deploy-release lint lint/check lint/format test coverage

help:
	@echo "Nexapp Kore"
	@echo "> test"
	@echo "> lint"
	@echo "> coverage	- Generates Jacoco coverage report"

build:
	mvn compile -DskipTests

deploy-staging:
	GPG_TTY=$(tty) mvn -DperformRelease=true clean deploy -DskipTests

deploy-release:
	GPG_TTY=$(tty) mvn -DperformRelease=true clean deploy -P release -DskipTests

lint: lint/format
lint/check:
	mvn install ktlint:check -DskipTests
lint/format:
	mvn install ktlint:format -DskipTests

test:
	mvn install test

coverage:
	mvn jacoco:report-aggregate
