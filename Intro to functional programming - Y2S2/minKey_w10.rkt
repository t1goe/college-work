#lang racket
(define minKey (lambda (F L) (cond
                          ((null? (cdr L)) (car L))
                          ((> (F (car L)) (F (minKey F (cdr L)))) (minKey F (cdr L)))
                          (#t  (car L))
                          )
                 )
  ) 