# NEO

`neo` is a project scaffolding tool that can create ready-to-use projects based on templates.

## Work

* All user input parameters are read
* Everything in the `data` folder is copied to project folder
* All files that have an extension of `txt`, `md`, `json`, `xml` are processed via Velocity for property embedding
* Ignore files via the ignore tag that you don't want to run via Velocity

## Available commands

* copydir
* copyfile
* log
* mkdir
* print
* rmdir

## Road Map

* Allow to read templates from github/bitbucket and all
* Support for ZIP based templates
* Support for HTTP based template urls
* Auto-update check for tool
