:- use_module(library(semweb/rdf11)).
:- use_module(library(semweb/turtle)). 
:- set_prolog_flag(toplevel_print_anon, false).

run(Graph,TurnNr) :- 
  rdf_assert(Graph,'http://example.org/hasNr',TurnNr,Graph).

