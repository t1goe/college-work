#lang racket
(define removeFirst(lambda (A L)(cond
                                  ((null? L) (L))
                                  ((equal? A (car L)) (cdr L))
                                  (#t (cons (car L) (removeFirst A (cdr L))))
)))