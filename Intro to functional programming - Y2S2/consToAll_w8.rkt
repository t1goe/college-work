#lang racket
(define consToAll (lambda(A L)(cond
                               ((null? L) '())
                               ((list? L)(cons (cons A (car L)) (consToAll A (cdr L))))
                               )
                    )
  )