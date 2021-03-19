#lang racket
(define removeMinKey (lambda (F L) (cond
                          ((null? L) '())
                          ((equal? (car L) (minKey F L)) (cdr L))
                          (#t (cons (car L)(removeMinKey F (cdr L))))
                          )
                 )
  ) 


(define minKey (lambda (F L) (cond
                          ((null? (cdr L)) (car L))
                          ((> (F (car L)) (F (minKey F (cdr L)))) (minKey F (cdr L)))
                          (#t  (car L))
                          )
                 )
  ) 