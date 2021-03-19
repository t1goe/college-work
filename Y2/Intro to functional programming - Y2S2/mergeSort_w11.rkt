#lang racket
(define mergeSort (lambda (F L) (cond
                                  ((null? (cdr L)) L)
                                  (#t (merge F (mergeSort F (sublist L)) (mergeSort F (sublist (cdr L)))))
                                  )
                    )
  )

(define sublist (lambda (L) (cond
                          ((null? L) L)
                          ((null? (cdr L)) L)
                          (#t (cons (car L) (sublist (cdr (cdr L)))))
                          )
                  )
)

  

(define merge (lambda (F L R) (cond
                          ((null? L) R)
                          ((null? R) L)
                          ((> (F (car R)) (F (car L)))(cons (car L) (merge F (cdr L) R)))
                          ((<= (F (car R)) (F (car L)))(cons (car R) (merge F (cdr R) L)))
                          )
                 )
  )