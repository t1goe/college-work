#lang racket
(define split(lambda (L) (cond
                           ((null? L)('() '()))
                           (#t (cons (half L) (list (half (cdr L)))))
                           ) 
               )
  )

(define half(lambda (L) (cond
                          ((null? L) '())
                          ((null? (cdr L)) (car L))
                          (#t (cons (car L) (half (cdr (cdr L)))))
                          )
              )
  )