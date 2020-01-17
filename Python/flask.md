# Flask

## Quickstart
- Flash class: WSGI application (Web Server Gateway Interface)
- route() decorator: bind a function to a URL
  - variable rules: \<converter:variable_name>
  - unique URLs / Redirection Behavior: trailing slash
- URL binding: url_for()
- HTTP methods: method argument of route()
- static files: CSS and JavaScript, filename argument of route()
- rendering templates: render_template()


## Jinja
- a modern and designer-friendly templating language for Python



# SQLAlchemy
- the python SQL toolkit and object relational mapper
- consider database to be a relational algebra engine, not just a collection of tables
- rows can be selected from not only tables but also joins and other select statements
- ORP (object-relational mapper): an optional component that provides data mapper pattern
  - map classes to database in open ended, multiple ways
  - decouple object model and database schema
  - 