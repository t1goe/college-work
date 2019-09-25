#lang racket
(define flatten_sublists (lambda (L) (cond
                                       ((null? L)'());Base case
                                       ((list? (car L))(append '(sublist_starts) (append (car L) (append '(sublist_ends)(flatten_sublists (cdr L))))));First element of the lsit is a list
                                       (#t (cons (car L) (flatten_sublists (cdr L))));First element of the list is atom
                                       )
                           )
  )