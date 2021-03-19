#lang racket
(require racket/trace)

(define repeat(lambda (L) (cond
                           ((null? L) '())
                           (#t(cons (makeList (car L) (car (cdr L))) (repeat (cdr (cdr L)))))
                           )
                )
  )

;;Takes number and element and returns a list of those two items
(define makeList(lambda (N E)(cond
                               ((null? N) '())
                               ((null? E) '())
                               ((= 0 N) '())
                               (#t (cons E (makeList (- 1 N) E)))
                               )
                  )
  )

(trace repeat)