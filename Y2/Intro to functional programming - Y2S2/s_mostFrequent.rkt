#lang racket
(define mostFrequent(lambda (L) (cond
                                  ((> (count (car L) L) (count (mostFrequent (cdr L)) L))(car L))
                                  (#t(mostFrequent (cdr L)))
                                  )
                      )
  )

(define count(lambda (A L) (cond
                              ((null? L) 0)
                              ((null? A) 0)
                              ((equal? A (car L))(+ 1 (count (cdr L) A)))
                              (#t(count (cdr L) A))
                              )
                  )
  )