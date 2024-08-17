# lisp by java

課題用に作ったけど、レポートのほうが間に合わず提出できず  
南無

いつか、ログとれるように作り直す

# 高階関数

(((lambda (x) x) atom) a)

のように、オペレータを返す関数は作れる

(((lambda (x) (lambda (y)(cons x y)) ) a ) b)

は、

(lambda (y) (cons a y) b)

になっていると考えられ、 non-defined operator エラーを返す