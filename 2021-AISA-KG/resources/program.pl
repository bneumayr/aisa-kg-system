:- use_module(library(semweb/rdf11)).
:- use_module(library(semweb/turtle)). 
:- set_prolog_flag(toplevel_print_anon, false).


test(TimePeriod,Graph) :- 
	gml_TimePeriod(Graph, TimePeriod, _,_).


run(Graph,TurnNr) :- 
  rdf_assert(Graph,'http://example.org/hasNr',TurnNr,Graph),
  forall( test(X,G), rdf_assert(X,'http://ex.org/in', G, Graph) ),
  forall( rdf(_,_,_,G), rdf_assert(G,'http://example.org/existsIn',Graph,Graph) ) .

