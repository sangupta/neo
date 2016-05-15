# NEO

`neo` is a project scaffolding tool that can create ready-to-use projects based on templates.

## How does it work?

* A template is defined by presence of a `neo.json` file
* The file contains user parameters to be read, and processing actions that need to be performed
* Based on the template, all user input parameters are read
* Everything in the `data` folder (relative to `neo.json` file) is copied to project folder
* All files that have an extension of `txt`, `md`, `json`, `xml` are processed via Velocity for property embedding
* Once the `data` folder is copied, process execution starts
* All actions are performed sequentially as present in the template file

## Available commands

* addProperty
* copyDir
* copyFile
* copyTemplate
* log
* mkdir
* print
* rmdir
* writeFile

## TODO commands

* copyTemplate
* renameFile
* zip
* unzip
* wget


## Road Map

* Allow to read templates from github/bitbucket and all
* Support for ZIP based templates
* Support for HTTP based template urls
* Auto-update check for tool
