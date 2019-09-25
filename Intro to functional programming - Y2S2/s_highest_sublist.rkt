#lang racket
(define highest_sublist(lambda (L) (cond
                                     ((null? L)'())
                                     (#t(helper L '()))
                                     )
                         )
  )

(define helper(lambda (L H) (cond
                              ((null? L) H)
                              ((> (apply + (car L)) (apply + H))(helper (cdr L) (car L)))
                              ((< (apply + (car L)) (apply + H))(helper (cdr L) H))
                              )
                          )
  )

(define highest_sublist2(lambda (L) (cond
                                     ((null? L)'())
                                     ((> (apply + (car L)) (apply + (highest_sublist2 (cdr L))))(car L))
                                     ((< (apply + (car L)) (apply + (highest_sublist2 (cdr L))))(highest_sublist2 (cdr L)))
                                     )
                         )
  );;Improved version

