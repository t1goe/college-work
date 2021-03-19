#lang racket
(define powerset (lambda(L)(cond
                               ((null? L) '(()))
                               ((list? L) (append (powerset (cdr L)) (consToAll (car L) (powerset (cdr L)))))
                               )
                    )
  )

(define consToAll (lambda(A L)(cond
                               ((null? L) '())
                               ((list? L)(cons (cons A (car L)) (consToAll A (cdr L))))
                               )
                    )
  )