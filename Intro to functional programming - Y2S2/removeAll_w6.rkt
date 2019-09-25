#lang racket
(define removeAll(lambda (A L)(cond
                                  ((null? L) L)
                                  ((list? (car L))(cons (removeAll A (car L)) (removeAll A (cdr L))))
                                  ((equal? A (car L)) (removeAll A (cdr L)))
                                  (#t (cons (car L) (removeAll A (cdr L))))
)))