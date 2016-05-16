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

* addProperty - adds a property to current context
* copyDir - copy directory from base folder to project folder
* copyFile - copy file from base folder to project folder, no variable injection takes place
* copyTemplate - copy file from base folder to project folder, variable injection as in a template happens
* log - log the following line to logs
* mkdir - create a new directory in the project folder
* moveFile - move a file within the project folder
* moveDir - move a directory within the project folder
* print - print a line to console
* rmdir - remove a directory from the project folder
* rmFile - remove a file from the project folder
* writeFile - write a new file in the project folder, without variable injection

## TODO commands

* zip
* unzip
* wget


## Road Map

* Allow to read templates from github/bitbucket and all
* Support for ZIP based templates
* Support for HTTP based template urls
* Auto-update check for tool
* Allow download/install of templates to local cache
* Allow clean up of local cache
