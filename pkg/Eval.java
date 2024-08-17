package pkg;

public class Eval {
    public static Node eval(Node e, Node a) throws Exception {

        System.out.println("eval==========================\n");
        System.out.println("e:");
        e.echo();
        System.out.println("a:");
        a.echo();
        System.out.println("==============================");
        try {
            if (e.isAtom()) {
                return assoc(e, a);
            }
            if (e.car().isAtom()) {
                if (e.car().value().equals("quote")) {
                    try {
                        return e.cdr().car();
                    } catch (Exception error) {
                        throw new Exception("case: quote\n" + error);
                    }
                }
                if (e.car().value().equals("atom")) {
                    try {
                        if (eval(e.cdr().car(), a).isAtom()) {
                            return new Atom("T");
                        } else {
                            return new Atom();
                        }
                    } catch (Exception error) {
                        throw new Exception("case: atom\n" + error);
                    }
                }
                if (e.car().value().equals("eq")) {
                    try {
                        String l = eval(e.cdr().car(), a).value();
                        String r = eval(e.cdr().cdr().car(), a).value();
                        if (l.equals(r)) {
                            return new Atom("T");
                        } else {
                            return new Atom();
                        }
                    } catch (Exception error) {
                        throw new Exception("case: eq\n" + error);
                    }
                }
                if (e.car().value().equals("cond")) {
                    try {
                        return eval_cond(e.cdr(), a);
                    } catch (Exception error) {
                        throw new Exception("case: cond\n" + error);
                    }
                }
                if (e.car().value().equals("car")) {
                    try {
                        return eval(e.cdr().car(), a).car();
                    } catch (Exception error) {
                        throw new Exception("case: car\n" + error);
                    }
                }
                if (e.car().value().equals("cdr")) {
                    try {
                        return eval(e.cdr().car(), a).cdr();
                    } catch (Exception error) {
                        throw new Exception("case: cdr\n" + error);
                    }
                }
                if (e.car().value().equals("cons")) {
                    try {
                        Node l = eval(e.cdr().car(), a);
                        Node r = eval(e.cdr().cdr().car(), a);
                        return cons(l, r);
                    } catch (Exception error) {
                        throw new Exception("case: cons\n" + error);
                    }
                }

                if (e.car().value().equals("append")) {
                    return append(e.cdr().car(), e.cdr().cdr().car());
                }

                if (e.car().value().equals("plus")) {
                    return plus(eval(e.cdr().car(), a), eval(e.cdr().cdr().car(), a));
                }

                if (e.car().value().equals("minus")) {
                    return minus(eval(e.cdr().car(), a), eval(e.cdr().cdr().car(), a));
                }

                if (e.car().value().equals("rand")) {
                    return rand(eval(e.cdr().car(), a));
                }

                // 原始オペレータでない場合
                // labelによって置き換えられる、label式かlambda式を表す変数
                // それ以外はthrow
                Node ff = assoc(e.car(), a);
                if (ff == e.car()) {
                    throw new Exception("case: non-defined operator\n" + e.car().tos(0));
                }
                return eval(cons(ff, e.cdr()), a);
            }
            if (e.car().car().isAtom()) {
                if (e.car().car().value().equals("lambda")) {
                    try {
                        Node e2 = e.car().cdr().cdr().car();
                        Node cadar = e.car().cdr().car();
                        Node resolved_e = eval_list(e.cdr(), a);
                        Node a2 = append(pair(cadar, resolved_e), a);
                        return eval(e2, a2);
                    } catch (Exception error) {
                        throw new Exception("case: lambda\n" + error);
                    }
                }
                if (e.car().car().value().equals("label")) {
                    try {
                        Node e2 = cons(e.car().cdr().cdr().car(), e.cdr());
                        Node ff = cons(e.car().cdr().car(), cons(e.car(), new Atom()));
                        Node a2 = cons(ff, a);
                        return eval(e2, a2);
                    } catch (Exception error) {
                        throw new Exception("case: label\n" + error);
                    }
                }
            }

            // cons(eval(e.car(), a), e.cdr()).echo();
            System.out.println("eval(cons(eval(e.car(), a), e.cdr()), a)");
            System.out.println("e.car():");
            e.car().echo();
            System.out.println("e.cdr():");
            e.cdr().echo();
            return eval(cons(eval(e.car(), a), e.cdr()), a);
            // throw new Exception("case: non-executable\n");
        } catch (Exception error) {
            throw new Exception("at Eval.eval\n" + "e:\n" + e.tos(0) + "\na:\n" + a.tos(0) + "\n" + error);
        }
    }

    static Node plus(Node e, Node a) throws Exception {
        try {
            if (e.isNum() && a.isNum()) {
                int eint = Integer.parseInt(e.value());
                int aint = Integer.parseInt(a.value());
                return new Atom(Integer.toString(eint + aint));
            } else {
                throw new Exception("case: argument is not a number\n");
            }
        } catch (Exception error) {
            throw new Exception("at Eval.plus\n" + e.tos(0) + "\n" + a.tos(0) + "\n" + error);
        }
    }

    static Node minus(Node e, Node a) throws Exception {
        try {
            if (e.isNum() && a.isNum()) {
                int eint = Integer.parseInt(e.value());
                int aint = Integer.parseInt(a.value());
                return new Atom(Integer.toString(eint - aint));
            } else {
                throw new Exception("case: argument is not a number\n");
            }
        } catch (Exception error) {
            throw new Exception("at Eval.minus\n" + e.tos(0) + "\n" + a.tos(0) + "\n" + error);
        }
    }

    static Node rand(Node x) throws Exception {
        try {
            if (!x.isNum()) {
                throw new Exception("case: argument is not a number");
            }
            int seed = Integer.parseInt(x.value());
            MTRandom mt = new MTRandom(seed);
            return new Atom(Integer.toString(mt.nextInt()));
        } catch (Exception error) {
            throw new Exception("at Eval.rand\n" + x.tos(0) + "\n" + error);
        }
    }

    static Node assoc(Node e, Node a) throws Exception {
        try {
            if (a.isNIL()) {
                return e;
            }
            if (e.value().equals(a.car().car().value())) {
                return a.car().cdr().car();
            } else {
                return assoc(e, a.cdr());
            }
        } catch (Exception error) {
            throw new Exception("at Eval.assoc()\n" + e.tos(0) + "\n" + a.tos(0) + "\n" + error);
        }
    }

    static Node eval_cond(Node e, Node a) throws Exception {
        try {
            if (e.isAtom()) {
                throw new Exception("case: T does not exist\n");
            }
            if (eval(e.car().car(), a).value().equals("T")) {
                return eval(e.car().cdr().car(), a);
            } else {
                return eval_cond(e.cdr(), a);
            }
        } catch (Exception error) {
            throw new Exception("at Eval.eval_cond()\n" + e.tos(0) + "\n" + error);
        }
    }

    static Pair cons(Node l, Node r) {
        Pair p = new Pair(l, r);
        System.out.println("cons===================");
        p.echo();
        System.out.println("=======================");
        return p;
    }

    static Node pair(Node x, Node y) throws Exception {
        if (x.isNIL() && y.isNIL()) {
            return new Atom();
        }
        if (!x.isAtom() && !y.isAtom()) {
            // x と y を一段階落として Pair にする
            Pair p = cons(x.car(), cons(y.car(), new Atom()));
            return cons(p, pair(x.cdr(), y.cdr()));
        }
        return null;
    }

    static Node eval_list(Node e, Node a) throws Exception {
        try {
            if (e.isNIL()) {
                return new Atom();
            } else {
                return cons(eval(e.car(), a), eval_list(e.cdr(), a));
            }
        } catch (Exception error) {
            throw new Exception("at Eval.eval_list\n" + error);
        }
    }

    static Node append(Node x, Node y) throws Exception {
        if (x.isNIL()) {
            return y;
        } else {
            return cons(x.car(), append(x.cdr(), y));
        }
    }

}
